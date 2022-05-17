package br.com.rj.systems.ifood.cadastro.dto;

import br.com.rj.systems.ifood.cadastro.model.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", uses = RestaurantMapper.class)
public interface PlateMapper {

    PlateDto toDto(Plate plate);

    Plate toPlate(AddPlateDto dto);

    void toPlate(UpdatePlateDto dto, @MappingTarget Plate plate);
}
