package org.example.amanzatboxservice.service;

import org.example.amanzatboxservice.dto.BoxRequest;
import org.example.amanzatboxservice.dto.BoxResponse;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.model.Box;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BoxService {
    List<BoxResponse> findAll();
    BoxResponse findById(UUID id);
    BoxResponse save(BoxRequest boxRequest);
    BoxResponse update(UUID id, BoxRequest boxRequest);
    void delete(UUID id);
    List<BoxResponse> findByStatus(String status);
    List<BoxResponse> findByVolumeBetween(Double minVolume, Double maxVolume);
    BoxResponse updateStatus(UUID id, BoxStatus newStatus);
    BigDecimal findPriceById(UUID boxId);

}
