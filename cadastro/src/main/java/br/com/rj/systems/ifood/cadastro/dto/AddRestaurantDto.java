package br.com.rj.systems.ifood.cadastro.dto;

import br.com.rj.systems.ifood.cadastro.infra.Dto;
import br.com.rj.systems.ifood.cadastro.infra.ValidDto;
import br.com.rj.systems.ifood.cadastro.model.Restaurant;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ValidDto
public class AddRestaurantDto implements Dto {

    @NotBlank
    public String owner;

    @CNPJ
    public String cnpj;

    @Size(min = 3, max = 30)
    public String fantasyName;

    public LocalizationDto localization;

    @Override
    public Boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (Restaurant.find("cnpj", this.cnpj).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
