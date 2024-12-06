package org.example.amanzatboxservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;
import org.example.amanzatboxservice.model.BoxDimensions;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BoxResponse {
    private UUID id;
    private BoxDimensions boxDimensions;
    private BigDecimal price;
    private BoxStatus status;
    private BoxType type;
    private double volume;
}
