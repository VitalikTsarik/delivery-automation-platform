package com.delivery.dto;

import com.delivery.entity.CityInTrip;
import com.delivery.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TripDto {
    private Long id;
    private Trip.State state;
    private List<CityInTrip> routeList;
    private List<FreePackageDto> packageList;
    private String car;
    private CityInTrip currentLocation;
    private UserDto transporter;

    public static TripDto build(Trip trip) {
        return new TripDto(
                trip.getId(),
                trip.getState(),
                trip.getRouteList(),
                trip.getPackageList().stream()
                        .map(FreePackageDto::build).collect(Collectors.toList()),
                trip.getCar(),
                trip.getCurrentLocation(),
                UserDto.build(trip.getTransporter())
        );
    }
}
