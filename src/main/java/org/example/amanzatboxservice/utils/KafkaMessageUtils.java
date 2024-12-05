package org.example.amanzatboxservice.utils;

import org.example.amanzatboxservice.dto.KafkaMessage;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageUtils {

    private KafkaMessageUtils() {

    }

    public static KafkaMessage createKafkaMessage(String correlationId, String data, String replyTo) {
        KafkaMessage responseKafkaMessage = new KafkaMessage();
        responseKafkaMessage.setCorrelationId(correlationId);
        responseKafkaMessage.setData(data);
        responseKafkaMessage.setReplyTo(replyTo);
        return responseKafkaMessage;
    }
}
