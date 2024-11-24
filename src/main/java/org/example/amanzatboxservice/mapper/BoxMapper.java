package org.example.amanzatboxservice.mapper;

import org.example.amanzatboxservice.dto.BoxRequest;
import org.example.amanzatboxservice.dto.BoxResponse;
import org.example.amanzatboxservice.model.Box;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.enums.BoxType;
import org.springframework.stereotype.Component;

@Component
public class BoxMapper {

    public BoxResponse toResponse(Box box) {
        BoxResponse response = new BoxResponse();
        response.setAddress(box.getAddress());
        response.setCity(box.getCity());
        response.setBoxDimensions(box.getBoxDimensions());
        response.setPrice(box.getPrice());
        response.setStatus(box.getStatus());
        response.setType(box.getType());
        response.setVolume(box.getVolume());
        return response;
    }

    public Box toEntity(BoxRequest boxRequest) {
        Box box = new Box();
        box.setAddress(boxRequest.getAddress());
        box.setCity(boxRequest.getCity());
        box.setPrice(boxRequest.getPrice());
        box.setStatus(BoxStatus.valueOf(boxRequest.getStatus().toUpperCase()));
        box.setType(BoxType.valueOf(boxRequest.getType().toUpperCase()));
        return box;
    }

}
