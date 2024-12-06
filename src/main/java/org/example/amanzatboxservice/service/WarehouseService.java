package org.example.amanzatboxservice.service;

import org.example.amanzatboxservice.dto.WarehouseRequest;
import org.example.amanzatboxservice.model.Warehouse;

import java.util.List;
import java.util.UUID;

public interface WarehouseService {
    List<Warehouse> getAll();
    Warehouse getById(UUID id);
    Warehouse create(WarehouseRequest warehouseRequest);
    Warehouse update(UUID id, WarehouseRequest warehouseRequest);
    void delete(UUID id);
}
