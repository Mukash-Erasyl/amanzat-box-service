package org.example.amanzatboxservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.amanzatboxservice.anotation.RoleCheck;
import org.example.amanzatboxservice.dto.WarehouseRequest;
import org.example.amanzatboxservice.dto.WarehouseResponse;
import org.example.amanzatboxservice.mapper.WarehouseMapper;
import org.example.amanzatboxservice.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAll(){
        return ResponseEntity.ok(warehouseService.getAll().stream()
                .map(WarehouseMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(WarehouseMapper
                .toResponse(warehouseService.getById(id)));
    }

    @RoleCheck(role = "ADMIN")
    @PostMapping
    public ResponseEntity<WarehouseResponse> create(@RequestBody WarehouseRequest warehouseRequest){
        return ResponseEntity.ok(WarehouseMapper.toResponse(warehouseService.create(warehouseRequest)));
    }

    @RoleCheck(role = "ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponse> update(@PathVariable UUID id, @RequestBody WarehouseRequest warehouseRequest){
        return ResponseEntity.ok(WarehouseMapper.toResponse(warehouseService.update(id, warehouseRequest)));
    }

    @RoleCheck(role = "ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        warehouseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
