package org.example.amanzatboxservice.repository;

import org.example.amanzatboxservice.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
}
