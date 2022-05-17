package br.com.rj.systems.ifood.cadastro.infra;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDtoValidator implements ConstraintValidator<ValidDto, Dto> {

    @Override
    public void initialize(ValidDto constraintAnnotation) {
    }

    @Override
    public boolean isValid(Dto dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.isValid(constraintValidatorContext);
    }
}
