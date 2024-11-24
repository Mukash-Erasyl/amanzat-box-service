package org.example.amanzatboxservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;
import org.example.amanzatboxservice.model.BoxDimensions;

import java.math.BigDecimal;

@Getter
@Setter
public class BoxResponse {
    private String address;
    private String city;
    private BoxDimensions boxDimensions;
    private BigDecimal price;
    private BoxStatus status;
    private BoxType type;
    private double volume;
}
