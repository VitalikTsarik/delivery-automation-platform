package com.delivery.dto;

import lombok.Data;

import java.util.List;

@Data
public class TripToStartDto {
    private long id;
    private List<String> route;
}
