package br.com.exemplo.validacao.controllers.exceptions.handlers;

public abstract class ExceptionHandler<T,E,R> {

  public abstract R createResponse(T t, E e);

}
