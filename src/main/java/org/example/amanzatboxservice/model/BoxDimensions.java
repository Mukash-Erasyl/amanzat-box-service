package org.example.amanzatboxservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "box_dimensions")
public class BoxDimensions extends BaseEntity {

    @Column(name = "height")
    private Double height;

    @Column(name = "width")
    private Double width;

    @Column(name = "length")
    private Double length;

    public Double calculateVolume() {
        if (height != null && width != null && length != null) {
            return height * width * length;
        }
        return null;
    }
}
