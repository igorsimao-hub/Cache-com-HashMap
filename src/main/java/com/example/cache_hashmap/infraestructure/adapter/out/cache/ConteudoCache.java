package com.example.cache_hashmap.infraestructure.adapter.out.cache;

import com.example.cache_hashmap.application.port.out.CacheEstatisticas;
import com.example.cache_hashmap.application.port.out.ConteudoCachePort;
import com.example.cache_hashmap.domain.model.Conteudo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ConteudoCache implements ConteudoCachePort {

    private final Map<Long, EntradaCache> cache = new ConcurrentHashMap<>();

    private final AtomicLong hits = new AtomicLong();
    private final AtomicLong misses = new AtomicLong();
    private final AtomicLong evictions = new AtomicLong();

    @Value("${app.cache.max-itens:5}")
    private int maxItens;

    @Value("${app.cache.ttl-segundos:60}")
    private long ttlSegundos;

    @Override
    public synchronized Optional<Conteudo> buscar(Long id) {
        EntradaCache entrada = cache.get(id);

        // Não existe no cache
        if (entrada == null) {
            misses.incrementAndGet();
            return Optional.empty();
        }

        // Verifica se expirou
        if (LocalDateTime.now().isAfter(entrada.getArmazenadoEm().plusSeconds(ttlSegundos))) {

            cache.remove(id);
            misses.incrementAndGet();
            return Optional.empty();
        }

        // Atualiza o último acesso
        entrada.registrarAcesso();

        hits.incrementAndGet();

        return Optional.of(entrada.getConteudo());
    }

    @Override
    public synchronized void armazenar(Conteudo conteudo) {

        Long id = conteudo.getId();

        // Se já existe, apenas atualiza
        if (cache.containsKey(id)) {
            cache.put(id, new EntradaCache(conteudo));
            return;
        }

        // Se estiver cheio, remove o menos utilizado
        if (cache.size() >= maxItens) {
            removerMenosUtilizado();
        }

        // Armazena o novo conteúdo
        cache.put(id, new EntradaCache(conteudo));
    }

    @Override
    public synchronized void invalidar(Long id) {
        cache.remove(id);
    }

    @Override
    public synchronized void limpar() {
        cache.clear();

        hits.set(0);
        misses.set(0);
        evictions.set(0);
    }

    @Override
    public synchronized CacheEstatisticas obterEstatisticas() {
        long total = hits.get() + misses.get();

        double hitRatio = total == 0 ? 0.0 : (double) hits.get() / total;

        return new CacheEstatisticas(
                cache.size(),
                maxItens,
                hits.get(),
                misses.get(),
                evictions.get(),
                hitRatio
        );
    }


    private void removerMenosUtilizado() {

        Long idMenosUtilizado = cache.entrySet()
                .stream()
                .min(Comparator.comparing(
                        entrada -> entrada.getValue().getUltimoAcesso()
                ))
                .map(Map.Entry::getKey)
                .orElseThrow();

        cache.remove(idMenosUtilizado);

        evictions.incrementAndGet();
    }
}
