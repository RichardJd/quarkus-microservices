package br.com.rj.systems.ifood.cadastro.dto;

import br.com.rj.systems.ifood.cadastro.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface PlateMapper {

    PlateDto toDto(Plate plate);

    Plate toPlate(AddPlateDto dto);

    void toPlate(UpdatePlateDto dto, @MappingTarget Plate plate);
}
