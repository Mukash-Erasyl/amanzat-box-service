package org.example.amanzatboxservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BoxRequest {
    @NotNull(message = "volume cannot be null")
    @Min(value = 0, message = "Volume must be a positive number")
    private Double volume;

    @NotNull(message = "Address cannot be null")
    private String address;

    @NotNull(message = "City cannot be null")
    private String city;

    @NotNull(message = "volumeId cannot be null")
    @Positive(message = "Price must be positive")
    private Long volumeId;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Status cannot be null")
    private String status;

    @NotNull(message = "Type cannot be null")
    private String type;
}
