package com.example.cache_hashmap.infraestructure.adapter.in.controller;

import com.example.cache_hashmap.application.port.in.ConteudoUseCase;
import com.example.cache_hashmap.application.service.PayloadBuscaConteudo;
import com.example.cache_hashmap.domain.model.Conteudo;
import com.example.cache_hashmap.infraestructure.adapter.in.dto.ConteudoRequest;
import com.example.cache_hashmap.infraestructure.adapter.in.dto.ConteudoResponse;
import com.example.cache_hashmap.infraestructure.adapter.in.mapper.ConteudoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conteudos")
@RequiredArgsConstructor
public class ConteudoController {

    private final ConteudoUseCase conteudoUseCase;
    private final ConteudoMapper mapper;


    @PostMapping
    public ResponseEntity<ConteudoResponse> cadastrar(@RequestBody ConteudoRequest request) {
        Conteudo conteudo = mapper.toDomain(request);
        Conteudo cadastrado = conteudoUseCase.cadastrar(conteudo);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(cadastrado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoResponse> buscarPorId(@PathVariable Long id) {
        PayloadBuscaConteudo payload = conteudoUseCase.buscarPorId(id);
        String cacheStatus = payload.cacheHit() ? "HIT" : "MISS";
        return ResponseEntity.ok()
                .header("X-Header", cacheStatus)
                .body(mapper.toResponse(payload.conteudo()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConteudoResponse> atualizar(@PathVariable Long id, @RequestBody ConteudoRequest request) {
        Conteudo conteudo = mapper.toDomain(request);
        Conteudo atualizado = conteudoUseCase.atualizar(id, conteudo);
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        conteudoUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}