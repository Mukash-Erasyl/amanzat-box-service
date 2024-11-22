package org.example.amanzatboxservice.repository;

import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoxRepository extends JpaRepository<Box, UUID> {
    List<Box> findByStatus(BoxStatus status);
    List<Box> findByVolumeBetween(Double minVolume, Double maxVolume);
}
