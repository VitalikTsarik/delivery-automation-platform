package com.delivery.controller;

import com.delivery.dto.FreePackageDto;
import com.delivery.dto.TripDto;
import com.delivery.dto.TripToCreateDto;
import com.delivery.entity.Package;
import com.delivery.entity.Trip;
import com.delivery.entity.User;
import com.delivery.service.PackageService;
import com.delivery.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasAuthority('TRANSPORTER')")
@RequestMapping("/api/transporter")
public class TransporterController {

    @Autowired
    private PackageService packageService;
    @Autowired
    private TripService tripService;

    @GetMapping("/packages/free")
    public ResponseEntity<?> getFreePackages() {
        List<Package> freePackages = packageService.findFreePackages();

        return ResponseEntity.ok(
                freePackages
                        .stream()
                        .map(FreePackageDto::build)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/trip")
    public ResponseEntity<?> createTrip(
            @AuthenticationPrincipal @ApiIgnore User user,
            @RequestBody TripToCreateDto tripDto
    ) {
        Trip newTrip = tripService.createTrip(user, tripDto.getCar(), tripDto.getSelectedPackages());

        return ResponseEntity.ok(TripDto.build(newTrip));
    }
}
