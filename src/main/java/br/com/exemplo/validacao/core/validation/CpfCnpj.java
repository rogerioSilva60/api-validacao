package br.com.exemplo.validacao.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CpfCnpjValidator.class })
public @interface CpfCnpj {

  String message() default "CpfCnpj deve estar no formato 000.000.000-00 ou 00.000.000/0000-00";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
