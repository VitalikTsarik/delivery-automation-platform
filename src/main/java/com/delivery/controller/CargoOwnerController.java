package com.delivery.controller;

import com.delivery.dto.PackageDto;
import com.delivery.entity.Package;
import com.delivery.entity.User;
import com.delivery.repository.PackageRepository;
import com.delivery.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasAuthority('CARGO_OWNER')")
@RequestMapping("/api")
public class CargoOwnerController {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private CityService cityService;

    @GetMapping("/packages")
    public ResponseEntity<?> getAllPackages(@AuthenticationPrincipal User user) {
        List<Package> packages = packageRepository.findAllPackages(user);

        return ResponseEntity.ok(packages);
    }

    @PostMapping("/package")
    public ResponseEntity<?> createPackage(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PackageDto packageDto
    ) {
        Package newPackage = new Package();
        copyFields(packageDto, newPackage);
        newPackage.setOwner(user);

        packageRepository.save(newPackage);

        return new ResponseEntity<>(PackageDto.build(newPackage), HttpStatus.CREATED);
    }

    @GetMapping("/package/{id}")
    public ResponseEntity<?> getAllPackages(
            @PathVariable long id,
            @AuthenticationPrincipal User user
    ) {
        Package cargo = packageRepository.findByIdAndOwner(id, user);

        return ResponseEntity.ok(PackageDto.build(cargo));
    }

    @PutMapping("/package/{id}")
    public ResponseEntity<?> updatePackage(
            @PathVariable long id,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PackageDto packageDto
    ) {
        Package packageToUpdate = packageRepository.findByIdAndOwner(id, user);

        if (packageToUpdate == null) {
            return new ResponseEntity<>("Package not found", HttpStatus.NOT_FOUND);
        }
        if (packageToUpdate.getCurrentTrip() != null) {
            return new ResponseEntity<>("Package is already in trip", HttpStatus.CONFLICT);
        }

        copyFields(packageDto, packageToUpdate);

        return new ResponseEntity<>(PackageDto.build(packageToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/package/{id}")
    public ResponseEntity<?> deletePackage(
            @PathVariable long id,
            @AuthenticationPrincipal User user
    ) {
        Package packageToDelete = packageRepository.findByIdAndOwner(id, user);

        if (packageToDelete == null) {
            return ResponseEntity.ok().build();
        }
        if (packageToDelete.getCurrentTrip() != null) {
            return new ResponseEntity<>("Package is already in trip", HttpStatus.CONFLICT);
        }

        packageRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private void copyFields(PackageDto from, Package to) {
        to.setName(from.getName());
        to.setLength(from.getLength());
        to.setHeight(from.getHeight());
        to.setWidth(from.getWidth());
        to.setWeight(from.getWeight());
        to.setCost(from.getCost());
        to.setInitialLocation(cityService.getOrAdd(from.getInitialLocation()));
        to.setTargetLocation(cityService.getOrAdd(from.getTargetLocation()));
    }
}
