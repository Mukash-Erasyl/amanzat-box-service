package org.example.amanzatboxservice.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessageDTO {
    private String correlationId;
    private String replyTo;
    private Object data;
}
