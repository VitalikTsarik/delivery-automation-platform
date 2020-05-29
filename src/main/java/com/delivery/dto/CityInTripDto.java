package com.delivery.dto;

import com.delivery.entity.City;
import lombok.Data;

@Data
public class CityInTripDto {
    private City city;
    private long order;
}
