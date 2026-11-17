package com.example.cache_hashmap.application.port.in;

import com.example.cache_hashmap.application.service.PayloadBuscaConteudo;
import com.example.cache_hashmap.domain.model.Conteudo;

import java.util.Optional;

public interface ConteudoUseCase {

    Conteudo cadastrar(Conteudo conteudo);

    PayloadBuscaConteudo buscarPorId(Long id);

    Conteudo atualizar(Long id, Conteudo conteudo);

    void excluir(Long id);
}
