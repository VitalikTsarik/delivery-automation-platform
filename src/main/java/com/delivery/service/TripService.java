package com.delivery.service;

import com.delivery.entity.Package;
import com.delivery.entity.Trip;
import com.delivery.entity.User;
import com.delivery.repository.PackageRepository;
import com.delivery.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private PackageRepository packageRepository;

    @Transactional
    public Trip createTrip(
            User transporter,
            String car,
            List<Long> selectedPackages
    ) {
        Trip trip = new Trip();
        trip.setTransporter(transporter);
        trip.setCar(car);
        trip.setState(Trip.State.CREATING);

        tripRepository.save(trip);

        int booked = packageRepository.bookPackagesForTrip(trip, selectedPackages);
        List<Package> bookedPackages;
        if (booked > 0) {
            bookedPackages = packageRepository.findAllByCurrentTrip(trip);
        } else {
            bookedPackages = new ArrayList<>();
        }

        trip.setPackageList(bookedPackages);
        tripRepository.save(trip);

        return trip;
    }

}
