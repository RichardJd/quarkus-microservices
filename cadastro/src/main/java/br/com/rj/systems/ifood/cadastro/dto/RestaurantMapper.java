package br.com.rj.systems.ifood.cadastro.dto;

import br.com.rj.systems.ifood.cadastro.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {

    @Mapping(target = "name", source = "fantasyName")
    Restaurant toRestaurant(AddRestaurantDto dto);

    @Mapping(target = "name", source = "fantasyName")
    void toRestaurant(UpdateRestaurantDto dto, @MappingTarget Restaurant restaurant);

    @Mapping(target = "fantasyName", source = "name")
    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "offsetToString")
    RestaurantDto toRestaurantDTO(Restaurant restaurant);

    @Named("offsetToString")
    default String offSetToString(OffsetDateTime createdDate) {
        return createdDate.format(DateTimeFormatter.ISO_INSTANT);
    }
}
