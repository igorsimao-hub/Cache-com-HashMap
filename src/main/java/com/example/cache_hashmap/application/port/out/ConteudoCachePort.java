package com.example.cache_hashmap.application.port.out;

import com.example.cache_hashmap.domain.model.Conteudo;

import java.util.Optional;

public interface ConteudoCachePort {

    Optional<Conteudo> buscar(Long id);

    void armazenar(Conteudo conteudo);

    void invalidar(Long id);

    void limpar();

    CacheEstatisticas obterEstatisticas();
}
