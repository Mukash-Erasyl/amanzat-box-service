package org.example.amanzatboxservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "box")
@Getter
@Setter
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "volume_id")
    private Long volumeId;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BoxStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BoxType type;
}
