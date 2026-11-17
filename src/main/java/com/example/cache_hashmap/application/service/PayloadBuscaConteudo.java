package com.example.cache_hashmap.application.service;

import com.example.cache_hashmap.domain.model.Conteudo;

public record PayloadBuscaConteudo(
        Conteudo conteudo,
        boolean cacheHit
) {}
