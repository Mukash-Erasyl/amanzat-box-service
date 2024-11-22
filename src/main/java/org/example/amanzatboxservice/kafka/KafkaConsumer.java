package org.example.amanzatboxservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "amanzat.box.find", groupId = "findBoxId")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Received message: {} from topic: {}", record.value(), record.topic());
    }

}
