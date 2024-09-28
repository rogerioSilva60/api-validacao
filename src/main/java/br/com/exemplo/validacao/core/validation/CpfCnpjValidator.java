package br.com.exemplo.validacao.core.validation;

import br.com.exemplo.validacao.util.DocumentoCnpjUtil;
import br.com.exemplo.validacao.util.DocumentoCpfUtil;
import br.com.exemplo.validacao.util.ValidacaoDocumentoUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

  public static final int TOTAL_CARACTERES_CNPJ = 18;
  public static final int TOTAL_CARACTERES_CPF = 14;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(Objects.nonNull(value)) {
      try {
        if(TOTAL_CARACTERES_CNPJ == value.length()) {
          ValidacaoDocumentoUtil.validar(new DocumentoCnpjUtil(value));
          return true;
        } else if (TOTAL_CARACTERES_CPF == value.length()) {
          ValidacaoDocumentoUtil.validar(new DocumentoCpfUtil(value));
          return true;
        }
      } catch (RuntimeException runtimeException) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(runtimeException.getMessage())
            .addConstraintViolation();
      }
    }
    return false;
  }
}
