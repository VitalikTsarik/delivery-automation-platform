package com.delivery.dto;

import com.delivery.entity.CityInTrip;
import lombok.Data;

import java.util.List;

@Data
public class TripToUpdateDto {
    private Long id;
    private List<CityInTrip> routeList;
    private List<FreePackageDto> packageList;
    private String car;
    private CityInTrip currentLocation;

//    public static TripDto build(Trip trip) {
//        return new TripDto(
//                trip.getId(),
//                trip.getState(),
//                trip.getRouteList(),
//                trip.getPackageList().stream()
//                        .map(FreePackageDto::build).collect(Collectors.toList()),
//                trip.getCar(),
//                trip.getCurrentLocation(),
//                UserDto.build(trip.getTransporter())
//        );
//    }
}
