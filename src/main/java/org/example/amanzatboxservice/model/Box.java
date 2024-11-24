package org.example.amanzatboxservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;

import java.math.BigDecimal;

@Entity
@Table(name = "box")
@Getter
@Setter
public class Box extends BaseEntity {

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "box_dimension_id", referencedColumnName = "id")
    private BoxDimensions boxDimensions;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BoxStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BoxType type;

    @Column(name = "volume")
    private Double volume;

    public void handlePrePersistAndUpdate() {
        this.volume = boxDimensions.calculateVolume();
    }
}
