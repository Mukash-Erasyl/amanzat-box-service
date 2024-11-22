package org.example.amanzatboxservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {

    private String correlationId;
    private String body;
    private String requestType;
    private String replyTo;

    public static class RequestType {
        public static final String REQUEST = "REQUEST";
        public static final String RESPONSE = "RESPONSE";
    }

}
