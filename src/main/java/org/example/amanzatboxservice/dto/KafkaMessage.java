package org.example.amanzatboxservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {

    private String correlationId;
    private String data;
    private String replyTo;

}
