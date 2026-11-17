package com.example.cache_hashmap.application.port.out;

public record CacheEstatisticas(
        int tamanhoAtual,
        int limite,
        long hits,
        long misses,
        long evictions,
        double hitRatio
) {}
