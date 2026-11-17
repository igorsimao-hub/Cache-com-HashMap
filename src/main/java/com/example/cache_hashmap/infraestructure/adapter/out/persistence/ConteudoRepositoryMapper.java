package com.example.cache_hashmap.infraestructure.adapter.out.persistence;


import com.example.cache_hashmap.domain.model.Conteudo;
import org.springframework.stereotype.Component;

@Component
public class ConteudoRepositoryMapper {

    public Conteudo toDomain(ConteudoEntity entity) {
        return new Conteudo(
                entity.getId(),
                entity.getTitulo(),
                entity.getCorpo(),
                entity.getAutor(),
                entity.getAtualizadoEm()
        );
    }

    public ConteudoEntity toEntity(Conteudo conteudo) {

        ConteudoEntity entity = new ConteudoEntity();

        entity.setId(conteudo.getId());
        entity.setTitulo(conteudo.getTitulo());
        entity.setCorpo(conteudo.getCorpo());
        entity.setAutor(conteudo.getAutor());
        entity.setAtualizadoEm(conteudo.getAtualizadoEm());

        return entity;
    }
}