package org.example.amanzatboxservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        handlePrePersistAndUpdate();
    }

    @PreUpdate
    public void onPreUpdate(){
        this.updatedAt = LocalDateTime.now();
        handlePrePersistAndUpdate();
    }
    protected void handlePrePersistAndUpdate() {
    }

}
