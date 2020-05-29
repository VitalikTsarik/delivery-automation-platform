package com.delivery.dto;

import com.delivery.entity.Package;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class PackageDto {
    private long id;

    @NotBlank
    private String name;
    @Min(1)
    private long weight;
    @Min(1)
    private long length;
    @Min(1)
    private long width;
    @Min(1)
    private long height;
    @Min(1)
    private long cost;
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
