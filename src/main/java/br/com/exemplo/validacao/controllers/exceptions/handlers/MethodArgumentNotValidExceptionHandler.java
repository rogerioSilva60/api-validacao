package br.com.exemplo.validacao.controllers.exceptions.handlers;


import br.com.exemplo.validacao.controllers.exceptions.StandardError;
import br.com.exemplo.validacao.controllers.exceptions.StandardError.Field;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RequiredArgsConstructor
public class MethodArgumentNotValidExceptionHandler extends
    ExceptionHandler<MethodArgumentNotValidException, WebRequest, StandardError> {

  private static final String MSG_INVALID_FIELDS = "Um ou mais campos são inválidos, preencha corretamente e tente novamente.";
  private final MessageSource messageSource;
  private final HttpStatus status;

  @Override
  public StandardError createResponse(MethodArgumentNotValidException e, WebRequest request) {

    String path = ((ServletWebRequest) request).getRequest().getRequestURI();
    StringJoiner stringJoiner = new StringJoiner(", ");
    List<Field> problemFields = prepareProblemFields(e, stringJoiner);

    return StandardError.createStandartErrorBuilder(status, stringJoiner.toString(), MSG_INVALID_FIELDS, path)
        .fields(problemFields)
        .build();
  }

  private List<Field> prepareProblemFields(MethodArgumentNotValidException e, StringJoiner stringJoiner) {
    List<Field> problemFields = e.getBindingResult().getFieldErrors()
        .stream()
        .map(fieldError -> {
          String messageField = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
          stringJoiner.add(messageField);
          return Field.builder()
              .name(fieldError.getField())
              .userMessage(messageField)
              .build();
        })
        .collect(Collectors.toList());
    return problemFields;
  }

}
