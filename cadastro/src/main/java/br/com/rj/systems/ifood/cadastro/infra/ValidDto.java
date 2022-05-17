package br.com.rj.systems.ifood.cadastro.infra;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidDtoValidator.class})
@Documented
public @interface ValidDto {

    String message() default "{br.com.rj.systems.ifood.cadastro.infra.ValidDto.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
