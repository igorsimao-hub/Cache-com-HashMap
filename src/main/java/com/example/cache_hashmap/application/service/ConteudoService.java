package com.example.cache_hashmap.application.service;

import com.example.cache_hashmap.application.exception.RecursoNaoEncontradoException;
import com.example.cache_hashmap.application.port.in.ConteudoUseCase;
import com.example.cache_hashmap.application.port.out.ConteudoCachePort;
import com.example.cache_hashmap.domain.model.Conteudo;
import com.example.cache_hashmap.infraestructure.adapter.out.persistence.ConteudoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConteudoService implements ConteudoUseCase {

    private final ConteudoRepositoryPort repositoryPort;
    private final ConteudoCachePort conteudoCachePort;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConteudoService.class);

    @Override
    public Conteudo cadastrar(Conteudo conteudo) {

        Conteudo novoConteudo = new Conteudo(
                null,
                conteudo.getTitulo(),
                conteudo.getCorpo(),
                conteudo.getAutor(),
                LocalDateTime.now()
        );

        return repositoryPort.salvar(novoConteudo);
    }

    @Override
    public PayloadBuscaConteudo buscarPorId(Long id) {
        long inicio = System.nanoTime();

        Optional<Conteudo> conteudoCache = conteudoCachePort.buscar(id);

        if (conteudoCache.isPresent()) {
            long tempoMs = (System.nanoTime() - inicio) / 1_000_000;
            LOGGER.info("Cache HIT | ID: {} | Tempo: {} ms", id, tempoMs);
            return new PayloadBuscaConteudo(conteudoCache.get(), true);
        }

        Conteudo conteudo = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException());

        conteudoCachePort.armazenar(conteudo);

        long tempoMs = (System.nanoTime() - inicio) / 1_000_000;
        LOGGER  .info("Cache MISS | ID: {} | Tempo: {} ms", id, tempoMs);
        return new PayloadBuscaConteudo(conteudo, false);
    }

    @Override
    public Conteudo atualizar(Long id, Conteudo conteudo) {

        Conteudo existente = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException());

        conteudo.setId(id);
        conteudo.setAtualizadoEm(LocalDateTime.now());

        Conteudo atualizado = repositoryPort.salvar(conteudo);
        conteudoCachePort.invalidar(id);

        return atualizado;
    }

    @Override
    public void excluir(Long id) {
        repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException());

        repositoryPort.excluir(id);
    }
}
