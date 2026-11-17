package com.example.cache_hashmap.infraestructure.adapter.out.persistence;

import com.example.cache_hashmap.domain.model.Conteudo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConteudoAdapter implements ConteudoRepositoryPort {

    private final ConteudoJpaRepository repository;
    private final ConteudoRepositoryMapper mapper;

    @Override
    public Conteudo salvar(Conteudo conteudo) {
        ConteudoEntity entity = mapper.toEntity(conteudo);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Conteudo> buscarPorId(Long id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro durante a simulação de lentidão", e);
        }

        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
