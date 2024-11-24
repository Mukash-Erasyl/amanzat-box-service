package org.example.amanzatboxservice.repository;

import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.model.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoxRepository extends JpaRepository<Box, UUID> {
    List<Box> findByStatus(BoxStatus status);
    List<Box> findByVolumeBetween(Double minVolume, Double maxVolume);
    @Query("SELECT b.price FROM Box b WHERE b.id = :id")
    Optional<BigDecimal> findPriceById(@Param("id") UUID id);
}
