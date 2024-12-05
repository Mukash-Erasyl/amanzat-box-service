package org.example.amanzatboxservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "working_schedule")
@Getter
@Setter
public class WorkSchedule extends BaseEntity {

    @Column(name = "opening_time_seconds")
    private long openingTimeSeconds;

    @Column(name = "closing_time_seconds")
    private long closingTimeSeconds;

    @Column(name = "working_days")
    private String workingDays;
}

