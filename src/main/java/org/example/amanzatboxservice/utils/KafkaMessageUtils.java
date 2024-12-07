package org.example.amanzatboxservice.utils;

import org.example.amanzatboxservice.dto.KafkaMessageDTO;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageUtils {

    private KafkaMessageUtils() {
    }

    public static KafkaMessageDTO createKafkaMessageDTO(String correlationId, Object data, String replyTo) {
        KafkaMessageDTO kafkaMessageDTO = new KafkaMessageDTO();
        kafkaMessageDTO.setCorrelationId(correlationId);
        kafkaMessageDTO.setData(data);
        kafkaMessageDTO.setReplyTo(replyTo);
        return kafkaMessageDTO;
    }
}