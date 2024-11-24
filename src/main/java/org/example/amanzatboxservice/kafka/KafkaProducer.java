package org.example.amanzatboxservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    public void sendMessage(String topic, String message){
        kafkaTemplate.send(topic, message.getBytes());
    }
}
