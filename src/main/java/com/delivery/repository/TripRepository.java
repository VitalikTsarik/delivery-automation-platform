package com.delivery.repository;

import com.delivery.entity.Trip;
import com.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findFirstByIdAndTransporter(long id, User transporter);

    List<Trip> findAllByStateAndTransporter(Trip.State state, User transporter);

    List<Trip> findAllByTransporter(User transporter);
}
