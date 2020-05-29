package com.delivery.service;

import com.delivery.entity.Package;
import com.delivery.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    public List<Package> findFreePackages() {
        return packageRepository.findFreePackages();
    }
}
