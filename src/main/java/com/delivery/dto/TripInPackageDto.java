package com.delivery.dto;

import com.delivery.entity.CityInTrip;
import com.delivery.entity.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TripInPackageDto {
    private Long id;
    private Trip.State state;
    private List<CityInTrip> routeList;
    private String car;
    private CityInTrip currentLocation;

    public static TripInPackageDto build(Trip trip) {
        if (trip == null) {
            return null;
        }

        return new TripInPackageDto(
                trip.getId(),
                trip.getState(),
                trip.getRouteList(),
                trip.getCar(),
                trip.getCurrentLocation()
        );
    }
}
