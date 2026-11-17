package com.example.cache_hashmap.application.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
  public RecursoNaoEncontradoException() {
    super("Conteudo não encontrado");
  }
}
