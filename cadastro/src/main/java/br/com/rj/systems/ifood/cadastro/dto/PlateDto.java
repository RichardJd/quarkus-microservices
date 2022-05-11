package br.com.rj.systems.ifood.cadastro.dto;

import java.math.BigDecimal;

public class PlateDto {

    public Long id;

    public String name;

    public String description;

    public RestaurantDto restaurant;

    public BigDecimal price;
}
