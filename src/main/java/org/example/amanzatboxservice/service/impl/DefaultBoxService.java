package org.example.amanzatboxservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.amanzatboxservice.dto.BoxRequest;
import org.example.amanzatboxservice.dto.BoxResponse;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;
import org.example.amanzatboxservice.mapper.BoxMapper;
import org.example.amanzatboxservice.model.Box;
import org.example.amanzatboxservice.model.BoxDimensions;
import org.example.amanzatboxservice.repository.BoxRepository;
import org.example.amanzatboxservice.repository.BoxDimensionsRepository;
import org.example.amanzatboxservice.service.BoxService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultBoxService implements BoxService {

    private final BoxRepository boxRepository;
    private final BoxDimensionsRepository boxDimensionsRepository;
    private final BoxMapper boxMapper;

    public List<BoxResponse> findAll() {
        return boxRepository.findAll()
                .stream()
                .map(boxMapper::toResponse)
                .toList();
    }

    public BoxResponse findById(UUID id) {
        Optional<Box> existingBox = boxRepository.findById(id);
        if (existingBox.isEmpty()) {
            throw new IllegalArgumentException("Box with id " + id + " not found");
        }
        return boxMapper.toResponse(existingBox.get());
    }

    public BoxResponse save(BoxRequest boxRequest) {

        BoxDimensions boxDimensions = boxDimensionsRepository.findById(boxRequest.getDimensionsId())
                .orElseThrow(() -> new IllegalArgumentException("Dimensions not found for ID: " + boxRequest.getDimensionsId()));

        Box box = boxMapper.toEntity(boxRequest);
        box.setBoxDimensions(boxDimensions);
        Box savedBox = boxRepository.save(box);
        return boxMapper.toResponse(savedBox);
    }

    public BoxResponse update(UUID id, BoxRequest boxRequest) {
        Box existingBox = boxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Box with id " + id + " not found"));

        if (boxRequest.getDimensionsId() != null) {
            BoxDimensions boxDimensions = boxDimensionsRepository.findById(boxRequest.getDimensionsId())
                    .orElseThrow(() -> new IllegalArgumentException("Dimensions not found for ID: " + boxRequest.getDimensionsId()));
            existingBox.setBoxDimensions(boxDimensions);
        }

        existingBox.setPrice(boxRequest.getPrice());
        existingBox.setStatus(BoxStatus.valueOf(boxRequest.getStatus().toUpperCase()));
        existingBox.setType(BoxType.valueOf(boxRequest.getType().toUpperCase()));

        Box savedBox = boxRepository.save(existingBox);
        return boxMapper.toResponse(savedBox);
    }

    public void delete(UUID id) {
        boxRepository.deleteById(id);
    }

    public List<BoxResponse> findByStatus(String status) {
        try {
            BoxStatus boxStatus = BoxStatus.valueOf(status.toUpperCase());

            List<Box> boxes = boxRepository.findByStatus(boxStatus);

            return boxes.stream()
                    .map(boxMapper::toResponse)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status, e);
        }
    }

    public List<BoxResponse> findByVolumeBetween(Double minVolume, Double maxVolume) {
        return boxRepository.findByVolumeBetween(minVolume, maxVolume)
                .stream()
                .map(boxMapper::toResponse)
                .toList();
    }

    public BoxResponse updateStatus(UUID id, BoxStatus newStatus) {
        Box existingBox = boxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Box with id " + id + " not found"));

        existingBox.setStatus(newStatus);

        Box updatedBox = boxRepository.save(existingBox);

        return boxMapper.toResponse(updatedBox);
    }

    public BigDecimal findPriceById(UUID boxId) {
        Optional<BigDecimal> priceOptional = boxRepository.findPriceById(boxId);

        if (priceOptional.isPresent()) {
            return priceOptional.get();
        } else {
            throw new RuntimeException("Box with ID " + boxId + " not found");
        }
    }
}

