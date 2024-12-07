package org.example.amanzatboxservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.amanzatboxservice.anotation.RoleCheck;
import org.example.amanzatboxservice.dto.BoxRequest;
import org.example.amanzatboxservice.dto.BoxResponse;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.kafka.KafkaProducer;
import org.example.amanzatboxservice.service.BoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/box")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;
    private final KafkaProducer producer;

    @GetMapping
    public ResponseEntity<List<BoxResponse>> getAllBoxes() {
        List<BoxResponse> boxes = boxService.findAll();
        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoxResponse> getBoxById(@PathVariable UUID id) {
        BoxResponse boxResponse = boxService.findById(id);
        return ResponseEntity.ok(boxResponse);
    }

    @RoleCheck(role = "ADMIN")
    @PostMapping
    public ResponseEntity<BoxResponse> createBox(@RequestBody BoxRequest boxRequest) {
        BoxResponse boxResponse = boxService.save(boxRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(boxResponse);
    }

    @RoleCheck(role = "ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<BoxResponse> updateBox(
            @PathVariable UUID id,
            @RequestBody BoxRequest boxRequest) {
        BoxResponse updatedBox = boxService.update(id, boxRequest);
        return ResponseEntity.ok(updatedBox);
    }
    @RoleCheck(role = "ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable UUID id) {
        boxService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BoxResponse>> getBoxesByStatus(@PathVariable String status) {
        List<BoxResponse> boxes = boxService.findByStatus(status);
        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/filter/byVolume")
    public ResponseEntity<List<BoxResponse>> getBoxesByVolume(
            @RequestParam Double minVolume,
            @RequestParam Double maxVolume) {
        List<BoxResponse> boxes = boxService.findByVolumeBetween(minVolume, maxVolume);
        return ResponseEntity.ok(boxes);
    }

    @RoleCheck(role = "ADMIN")
    @PatchMapping("/{id}/status")
    public ResponseEntity<BoxResponse> updateBoxStatus(
            @PathVariable UUID id,
            @RequestParam BoxStatus newStatus) {
        BoxResponse updatedBox = boxService.updateStatus(id, newStatus);
        return ResponseEntity.ok(updatedBox);
    }
}
