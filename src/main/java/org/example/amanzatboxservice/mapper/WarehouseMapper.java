package org.example.amanzatboxservice.mapper;

import org.example.amanzatboxservice.dto.WarehouseRequest;
import org.example.amanzatboxservice.dto.WarehouseResponse;
import org.example.amanzatboxservice.model.Warehouse;
import org.example.amanzatboxservice.model.Geo;
import org.example.amanzatboxservice.enums.CityEnum;
import org.example.amanzatboxservice.enums.WarehouseStatus;

public class WarehouseMapper {

    public static WarehouseResponse toResponse(Warehouse warehouse) {
        WarehouseResponse response = new WarehouseResponse();
        response.setId(warehouse.getId());
        response.setAddress(warehouse.getAddress());
        response.setCity(warehouse.getCity());
        response.setStatus(warehouse.getStatus());
        response.setWorkSchedule(warehouse.getWorkSchedule());

        if (warehouse.getGeoPoint() != null) {
            response.setLatitude(warehouse.getGeoPoint().getLatitude());
            response.setLongitude(warehouse.getGeoPoint().getLongitude());
        }

        return response;
    }

    public static Warehouse toEntity(WarehouseRequest warehouseRequest) {
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress(warehouseRequest.getAddress());
        warehouse.setCity(CityEnum.valueOf(warehouseRequest.getCity().toUpperCase()));
        warehouse.setStatus(WarehouseStatus.valueOf(warehouseRequest.getStatus().toUpperCase()));

        Geo geo = new Geo(warehouseRequest.getLatitude(), warehouseRequest.getLongitude());
        warehouse.setGeoPoint(geo);

        return warehouse;
    }
}
