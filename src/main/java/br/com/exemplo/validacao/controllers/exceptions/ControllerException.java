package br.com.exemplo.validacao.controllers.exceptions;


import br.com.exemplo.validacao.controllers.exceptions.handlers.MethodArgumentNotValidExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerException.class);
  private final MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    StandardError problem = new MethodArgumentNotValidExceptionHandler(
        messageSource, HttpStatus.valueOf(status.value()))
        .createResponse(methodArgumentNotValidException, request);

    LOGGER.error(problem.getMessage(), methodArgumentNotValidException);
    return handleExceptionInternal(methodArgumentNotValidException, problem, headers, status, request);
  }

}
