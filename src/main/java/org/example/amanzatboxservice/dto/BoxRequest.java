package org.example.amanzatboxservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BoxRequest {

    @NotNull(message = "Address cannot be null")
    private String address;

    @NotNull(message = "City cannot be null")
    private String city;

    @NotNull(message = "dimensionsId cannot be null")
    @Positive(message = "Price must be positive")
    private UUID dimensionsId;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Status cannot be null")
    private String status;

    @NotNull(message = "Type cannot be null")
    private String type;
}
