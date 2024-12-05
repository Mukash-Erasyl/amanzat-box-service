package org.example.amanzatboxservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.amanzatboxservice.enums.CityEnum;
import org.example.amanzatboxservice.enums.WarehouseStatus;

@Entity
@Table(name = "warehouse")
@Getter
@Setter
public class Warehouse extends BaseEntity{
    @Column(name = "address")
    private String address;

    @Column(name = "city")
    @Enumerated(EnumType.STRING)
    private CityEnum city;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WarehouseStatus status;

    @ManyToOne
    @JoinColumn(name = "working_schedule_id", referencedColumnName = "id")
    private WorkSchedule workSchedule;

    @Embedded
    @AttributeOverride(name = "latitude", column = @Column(name = "lat"))
    @AttributeOverride(name = "longitude", column = @Column(name = "lon"))
    private Geo geoPoint;

}
