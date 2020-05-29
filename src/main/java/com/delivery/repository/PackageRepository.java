package com.delivery.repository;

import com.delivery.entity.Package;
import com.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {
    @Query("select p" +
            " from Package p" +
            " where p.owner=:owner" +
            " order by p.id")
    List<Package> findAllPackages(@Param("owner") User owner);

    Package findByIdAndOwner(long id, User owner);
}
