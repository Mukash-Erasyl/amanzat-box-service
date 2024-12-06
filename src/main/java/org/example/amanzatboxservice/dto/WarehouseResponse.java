package org.example.amanzatboxservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.amanzatboxservice.enums.CityEnum;
import org.example.amanzatboxservice.enums.WarehouseStatus;
import org.example.amanzatboxservice.model.WorkSchedule;

import java.util.UUID;


@Getter
@Setter
public class WarehouseResponse {
    private UUID id;
    private String address;
    private CityEnum city;
    private WarehouseStatus status;
    private WorkSchedule workSchedule;
    private Double latitude;
    private Double longitude;
}
