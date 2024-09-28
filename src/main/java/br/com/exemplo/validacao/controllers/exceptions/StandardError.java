package br.com.exemplo.validacao.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class StandardError implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
  private Instant  timestamp;
  private Integer status;
  private String error;
  private String message;
  private String path;
  private List<Field> fields;

  @Getter
  @Builder
  public static class Field {

    private String name;
    private String userMessage;

  }

  public static StandardErrorBuilder createStandartErrorBuilder(HttpStatus status, String error,
      String message, String path) {
    return StandardError.builder()
        .timestamp(Instant.now())
        .status(status.value())
        .error(error)
        .message(message)
        .path(path);
  }
}
