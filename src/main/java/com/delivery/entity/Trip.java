package com.delivery.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER)
    private List<CityInTrip> routeList;

    @OneToMany(mappedBy = "currentTrip", fetch = FetchType.LAZY)
    private List<Package> packageList;

    private String car;

    @OneToOne
    private CityInTrip currentLocation;

    public Long getId() {
        return id;
    }

    public List<CityInTrip> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<CityInTrip> routeList) {
        this.routeList = routeList;
    }

    public List<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public CityInTrip getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CityInTrip currentLocation) {
        this.currentLocation = currentLocation;
    }
}
