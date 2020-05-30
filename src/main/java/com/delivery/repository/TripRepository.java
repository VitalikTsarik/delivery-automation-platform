package com.delivery.repository;

import com.delivery.entity.Trip;
import com.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findFirstByIdAndTransporter(long id, User transporter);

    List<Trip> findAllByStateAndTransporter(Trip.State state, User transporter);

    List<Trip> findAllByTransporter(User transporter);

    @Query("select trip" +
            " from Trip trip" +
            "   join Package package on trip.id=package.currentTrip.id" +
            " where package.owner=:cargoOwner")
    List<Trip> findTripsForCargoOwner(@Param("cargoOwner") User cargoOwner);
}
