package org.example.amanzatboxservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.amanzatboxservice.dto.WarehouseRequest;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;
import org.example.amanzatboxservice.enums.CityEnum;
import org.example.amanzatboxservice.enums.WarehouseStatus;
import org.example.amanzatboxservice.mapper.WarehouseMapper;
import org.example.amanzatboxservice.model.Box;
import org.example.amanzatboxservice.model.BoxDimensions;
import org.example.amanzatboxservice.model.Geo;
import org.example.amanzatboxservice.model.Warehouse;
import org.example.amanzatboxservice.repository.WarehouseRepository;
import org.example.amanzatboxservice.repository.WorkScheduleRepository;
import org.example.amanzatboxservice.service.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DefaultWarehouseService implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WorkScheduleRepository workScheduleRepository;
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    public Warehouse getById(UUID id) {
        var existingWarehouse = warehouseRepository.findById(id);
        if (existingWarehouse.isEmpty()) {
            throw new IllegalArgumentException("Box with id " + id + " not found");
        }
        return existingWarehouse.get();
    }

    public Warehouse create(WarehouseRequest warehouseRequest) {
        return warehouseRepository.save(WarehouseMapper.toEntity(warehouseRequest));
    }


    public Warehouse update(UUID id, WarehouseRequest warehouseRequest) {

        var existingWarehouse = warehouseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Warehouse with id " + id + " not found"));;

        if (warehouseRequest.getWorkingScheduleId() != null) {
            var warehouseSchedule = workScheduleRepository.findById(warehouseRequest.getWorkingScheduleId())
                    .orElseThrow(() -> new IllegalArgumentException("workSchedule not found for ID: " + warehouseRequest.getWorkingScheduleId()));
            existingWarehouse.setWorkSchedule(warehouseSchedule);
        }

        existingWarehouse.setAddress(warehouseRequest.getAddress());
        existingWarehouse.setCity(CityEnum.valueOf(warehouseRequest.getCity().toUpperCase()));
        existingWarehouse.setStatus(WarehouseStatus.valueOf(warehouseRequest.getStatus()));
        existingWarehouse.setAddress(warehouseRequest.getAddress());
        existingWarehouse.setAddress(warehouseRequest.getAddress());
        existingWarehouse.setAddress(warehouseRequest.getAddress());
        existingWarehouse.setGeoPoint(new Geo(warehouseRequest.getLatitude(), warehouseRequest.getLongitude()));

        return warehouseRepository.save(WarehouseMapper.toEntity(warehouseRequest));
    }

    public void delete(UUID id) {
        warehouseRepository.deleteById(id);
    }

}
