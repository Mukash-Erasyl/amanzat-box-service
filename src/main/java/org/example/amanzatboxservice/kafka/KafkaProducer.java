package org.example.amanzatboxservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendStringMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Sent string message: " + message + " to topic: " + topic);
    }


    public void sendJsonMessage(String topic, Object message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Sent JSON message: " + message + " to topic: " + topic);
    }
}