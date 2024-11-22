package org.example.amanzatboxservice.service;

import org.example.amanzatboxservice.dto.BoxRequest;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.model.Box;

import java.util.List;

public interface BoxService {
    List<BoxRequest> findAll();
    BoxRequest findById(Long id);
    BoxRequest save(BoxRequest boxRequest);
    BoxRequest update(Long id, BoxRequest boxRequest);
    void delete(Long id);
    List<BoxRequest> findByStatus(String status);
    List<Box> findByVolumeBetween(Double minVolume, Double maxVolume);
    BoxRequest updateStatus(Long id, BoxStatus newStatus);

}
