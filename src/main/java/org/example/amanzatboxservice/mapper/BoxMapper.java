package org.example.amanzatboxservice.mapper;

import org.example.amanzatboxservice.dto.BoxRequest;
import org.example.amanzatboxservice.model.Box;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;
import org.springframework.stereotype.Component;

@Component
public class BoxMapper {

    public Box toEntity(BoxRequest boxRequest) {
        Box box = new Box();
        box.setAddress(boxRequest.getAddress());
        box.setCity(boxRequest.getCity());
        box.setVolume(boxRequest.getVolume());
        box.setVolumeId(boxRequest.getVolumeId());
        box.setPrice(boxRequest.getPrice());
        box.setStatus(BoxStatus.valueOf(boxRequest.getStatus().toUpperCase()));
        box.setType(BoxType.valueOf(boxRequest.getType().toUpperCase()));
        return box;
    }

    public BoxRequest toDto(Box box) {
        BoxRequest boxRequest = new BoxRequest();
        boxRequest.setVolume(box.getVolume());
        boxRequest.setVolumeId(box.getVolumeId());
        boxRequest.setAddress(box.getAddress());
        boxRequest.setCity(box.getCity());
        boxRequest.setPrice(box.getPrice());
        boxRequest.setStatus(box.getStatus().name());
        boxRequest.setType(box.getType().name());
        return boxRequest;
    }
}
