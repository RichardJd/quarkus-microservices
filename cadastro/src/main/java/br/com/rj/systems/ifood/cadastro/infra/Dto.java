package br.com.rj.systems.ifood.cadastro.infra;

import javax.validation.ConstraintValidatorContext;

public interface Dto {

    default Boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
