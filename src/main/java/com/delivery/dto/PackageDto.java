package com.delivery.dto;

import com.delivery.entity.Package;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class PackageDto {
    private long id;

    @NotBlank
    private String name;
    @NotBlank
    private Long weight;
    @NotBlank
    private Long length;
    @NotBlank
    private Long width;
    @NotBlank
    private Long height;
    @NotBlank
    private Long cost;
    @NotBlank
    private String initialLocation;
    @NotBlank
    private String targetLocation;

    public static PackageDto build(Package cargo) {
        return new PackageDto(
                cargo.getId(),
                cargo.getName(),
                cargo.getWeight(),
                cargo.getLength(),
                cargo.getWidth(),
                cargo.getHeight(),
                cargo.getCost(),
                cargo.getInitialLocation().getName(),
                cargo.getTargetLocation().getName()
        );
    }
}
