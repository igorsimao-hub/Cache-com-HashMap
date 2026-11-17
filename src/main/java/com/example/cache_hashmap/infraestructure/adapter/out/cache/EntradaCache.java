package com.example.cache_hashmap.infraestructure.adapter.out.cache;

import com.example.cache_hashmap.domain.model.Conteudo;

import java.time.LocalDateTime;

public class EntradaCache {

    private final Conteudo conteudo;
    private final LocalDateTime armazenadoEm;
    private LocalDateTime ultimoAcesso;

    public EntradaCache(Conteudo conteudo) {
        this.conteudo = conteudo;
        this.armazenadoEm = LocalDateTime.now();
        this.ultimoAcesso = LocalDateTime.now();
    }

    public Conteudo getConteudo() {
        return conteudo;
    }

    public LocalDateTime getArmazenadoEm() {
        return armazenadoEm;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void registrarAcesso() {
        this.ultimoAcesso = LocalDateTime.now();
    }
}
