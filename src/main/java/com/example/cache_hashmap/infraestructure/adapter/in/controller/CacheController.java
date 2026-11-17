package com.example.cache_hashmap.infraestructure.adapter.in.controller;

import com.example.cache_hashmap.application.port.out.CacheEstatisticas;
import com.example.cache_hashmap.application.port.out.ConteudoCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final ConteudoCachePort conteudoCachePort;

    @GetMapping("/estatisticas")
    public ResponseEntity<CacheEstatisticas> obterEstatisticas() {
        return ResponseEntity.ok(conteudoCachePort.obterEstatisticas());
    }

    @DeleteMapping
    public ResponseEntity<Void> limparCache() {
        conteudoCachePort.limpar();
        return ResponseEntity.noContent().build();
    }
}