package org.example.amanzatboxservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WarehouseRequest {
    @NotNull(message = "Address cannot be null")
    private String address;
    @NotNull(message = "City cannot be null")
    private String city;
    @NotNull(message = "Status cannot be null")
    private String status;
    @NotNull(message = "working_schedule_id cannot be null")
    private UUID workingScheduleId;
    @NotNull(message = "latitude cannot be null")
    private Double latitude;
    @NotNull(message = "longitude cannot be null")
    private Double longitude;
}
