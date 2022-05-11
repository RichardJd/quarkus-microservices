package br.com.rj.systems.ifood.cadastro.dto;

import br.com.rj.systems.ifood.cadastro.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {

    @Mapping(target = "name", source = "fantasyName")
    Restaurant toRestaurant(AddRestaurantDto dto);

    @Mapping(target = "name", source = "fantasyName")
    void toRestaurant(UpdateRestaurantDto dto, @MappingTarget Restaurant restaurant);

    @Mapping(target = "fantasyName", source = "name")
    @Mapping(target = "createdDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    RestaurantDto toRestaurantDTO(Restaurant restaurant);
}
