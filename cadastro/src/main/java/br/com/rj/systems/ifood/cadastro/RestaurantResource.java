package br.com.rj.systems.ifood.cadastro;

import br.com.rj.systems.ifood.cadastro.dto.AddPlateDto;
import br.com.rj.systems.ifood.cadastro.dto.AddRestaurantDto;
import br.com.rj.systems.ifood.cadastro.dto.PlateDto;
import br.com.rj.systems.ifood.cadastro.dto.PlateMapper;
import br.com.rj.systems.ifood.cadastro.dto.RestaurantDto;
import br.com.rj.systems.ifood.cadastro.dto.RestaurantMapper;
import br.com.rj.systems.ifood.cadastro.dto.UpdatePlateDto;
import br.com.rj.systems.ifood.cadastro.dto.UpdateRestaurantDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurant")
public class RestaurantResource {

    @Inject
    RestaurantMapper restaurantMapper;

    @Inject
    PlateMapper plateMapper;

    @GET
    public List<RestaurantDto> findAll() {
        Stream<Restaurant> restaurants = Restaurant.streamAll();
        return restaurants.map(restaurant -> restaurantMapper.toRestaurantDTO(restaurant)).collect(Collectors.toList());
    }

    @POST
    @Transactional
    public Response save(AddRestaurantDto dto) {
        final var restaurant = restaurantMapper.toRestaurant(dto);
        restaurant.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void update(@PathParam("id") Long id, UpdateRestaurantDto dto) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurant restaurant = restaurantOp.get();
        restaurantMapper.toRestaurant(dto, restaurant);
        restaurant.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void remove(@PathParam("id") Long id) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(id);
        restaurantOp.ifPresentOrElse(Restaurant::delete, () -> {
            throw new NotFoundException();
        });
    }

    // Plates

    @GET
    @Path("{idRestaurant}/plates")
    @Tag(name = "plate")
    public List<PlateDto> findAll(@PathParam("idRestaurant") Long idRestaurant) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant don't exist!");
        }
        Stream<Plate> plates = Plate.stream("restaurant", restaurantOp.get());
        return plates.map(plate -> plateMapper.toDto(plate)).collect(Collectors.toList());
    }

    @POST
    @Path("{idRestaurant}/plates")
    @Tag(name = "plate")
    @Transactional
    public Response addPlate(@PathParam("idRestaurant") Long idRestaurant, AddPlateDto dto) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant don't exist!");
        }

//        Plate plate = new Plate();
//        plate.name = dto.name;
//        plate.description = dto.description;
//        plate.price = dto.price;
//        plate.restaurant = restaurantOp.get();

        Plate plate = plateMapper.toPlate(dto);
        plate.restaurant = restaurantOp.get();

        plate.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{idRestaurant}/plates/{id}")
    @Tag(name = "plate")
    @Transactional
    public void update(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id, UpdatePlateDto dto) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant don't exist!");
        }

        Optional<Plate> plateOp = Plate.findByIdOptional(id);
        if (plateOp.isEmpty()) {
            throw new NotFoundException();
        }

        Plate plate = plateOp.get();
        plateMapper.toPlate(dto, plate);
        plate.price = dto.price;
        plate.persist();
    }

    @DELETE
    @Path("{idRestaurant}/plates/{id}")
    @Tag(name = "plate")
    @Transactional
    public void remove(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {
        Optional<Restaurant> restaurantOp = Restaurant.findByIdOptional(idRestaurant);
        if (restaurantOp.isEmpty()) {
            throw new NotFoundException("Restaurant don't exist!");
        }

        Optional<Plate> plateOp = Plate.findByIdOptional(id);
        plateOp.ifPresentOrElse(Plate::delete, () -> {
            throw new NotFoundException();
        });
    }
}