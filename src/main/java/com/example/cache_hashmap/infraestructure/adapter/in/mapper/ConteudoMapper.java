package com.example.cache_hashmap.infraestructure.adapter.in.mapper;

import com.example.cache_hashmap.domain.model.Conteudo;
import com.example.cache_hashmap.infraestructure.adapter.in.dto.ConteudoRequest;
import com.example.cache_hashmap.infraestructure.adapter.in.dto.ConteudoResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConteudoMapper {

    public Conteudo toDomain(ConteudoRequest request) {
        return new Conteudo(
                null,
                request.titulo(),
                request.corpo(),
                request.autor(),
                null
        );
    }

    public ConteudoResponse toResponse(Conteudo conteudo) {
        return new ConteudoResponse(
                conteudo.getId(),
                conteudo.getTitulo(),
                conteudo.getCorpo(),
                conteudo.getAutor(),
                conteudo.getAtualizadoEm()
        );
    }
}