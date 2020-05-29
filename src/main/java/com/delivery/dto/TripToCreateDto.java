package com.delivery.dto;

import lombok.Data;

import java.util.List;

@Data
public class TripToCreateDto {
    String car;
    List<Long> selectedPackages;
}
