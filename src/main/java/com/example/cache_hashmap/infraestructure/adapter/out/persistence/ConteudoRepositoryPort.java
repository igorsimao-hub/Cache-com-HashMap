package com.example.cache_hashmap.infraestructure.adapter.out.persistence;

import com.example.cache_hashmap.domain.model.Conteudo;

import java.util.Optional;

public interface ConteudoRepositoryPort {

    Conteudo salvar(Conteudo conteudo);

    Optional<Conteudo> buscarPorId(Long id);

    void excluir(Long id);
}
