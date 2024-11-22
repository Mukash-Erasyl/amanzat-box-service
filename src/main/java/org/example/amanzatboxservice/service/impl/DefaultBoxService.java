package org.example.amanzatboxservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.amanzatboxservice.dto.BoxRequest;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;
import org.example.amanzatboxservice.mapper.BoxMapper;
import org.example.amanzatboxservice.model.Box;
import org.example.amanzatboxservice.repository.BoxRepository;
import org.example.amanzatboxservice.service.BoxService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultBoxService implements BoxService {

    private final BoxRepository boxRepository;
    private final BoxMapper boxMapper;

    public List<BoxRequest> findAll() {
        return boxRepository.findAll()
                .stream()
                .map(boxMapper::toDto)
                .toList();
    }

    public BoxRequest findById(Long id) {
        Optional<Box> existingBox = boxRepository.findById(id);
        if (existingBox.isEmpty()) {
            throw new IllegalArgumentException("Box with id " + id + " not found");
        }
        return boxMapper.toDto(existingBox.get());
    }

    public BoxRequest save(BoxRequest boxRequest) {
        Box box = boxMapper.toEntity(boxRequest);
        Box savedBox = boxRepository.save(box);
        return boxMapper.toDto(savedBox);
    }

    public BoxRequest update(Long id, BoxRequest boxRequest) {
        Optional<Box> existingBoxOptional = boxRepository.findById(id);
        if (existingBoxOptional.isEmpty()) {
            throw new IllegalArgumentException("Box with id " + id + " not found");
        }

        Box existingBox = existingBoxOptional.get();
        existingBox.setVolume(boxRequest.getVolume());
        existingBox.setVolumeId(boxRequest.getVolumeId());
        existingBox.setCity(boxRequest.getCity());
        existingBox.setAddress(boxRequest.getAddress());
        existingBox.setPrice(boxRequest.getPrice());
        existingBox.setStatus(BoxStatus.valueOf(boxRequest.getStatus().toUpperCase()));
        existingBox.setType(BoxType.valueOf(boxRequest.getType().toUpperCase()));

        Box savedBox = boxRepository.save(existingBox);
        return boxMapper.toDto(savedBox);
    }

    public void delete(Long id) {
        boxRepository.deleteById(id);
    }

    public List<BoxRequest> findByStatus(String status) {
        try {
            BoxStatus boxStatus = BoxStatus.valueOf(status.toUpperCase());

            List<Box> boxes = boxRepository.findByStatus(boxStatus);

            return boxes.stream()
                    .map(boxMapper::toDto)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status, e);
        }
    }


    public List<Box> findByVolumeBetween(Double minVolume, Double maxVolume) {
        return boxRepository.findByVolumeBetween(minVolume, maxVolume);
    }

    public BoxRequest updateStatus(Long id, BoxStatus newStatus) {
        Box existingBox = boxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Box with id " + id + " not found"));

        existingBox.setStatus(newStatus);

        Box updatedBox = boxRepository.save(existingBox);

        return boxMapper.toDto(updatedBox);
    }
}

