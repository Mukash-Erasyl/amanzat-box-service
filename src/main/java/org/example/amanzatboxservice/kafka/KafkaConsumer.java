package org.example.amanzatboxservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.amanzatboxservice.dto.KafkaMessage;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.service.BoxService;
import org.example.amanzatboxservice.utils.KafkaMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final BoxService boxService;
    private final KafkaProducer producer;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "amanzat.box-api.block-price", groupId = "group-0")
    public void listenToBlockGetCostRequest(ConsumerRecord<String, byte[]> record) throws JsonProcessingException {
        log.info("Received message from 'amanzat.box-api.block-price' topic: {}", record.value());

        KafkaMessage message;
        try {
            message = objectMapper.readValue(record.value(), KafkaMessage.class);
        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
            return;
        }

        String correlationId = message.getCorrelationId();
        String replyTo = message.getReplyTo();
        String data = message.getData();

        boxService.updateStatus(UUID.fromString(data), BoxStatus.BOOKED);
        String price = boxService.findPriceById(UUID.fromString(data)).toString();

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("price", price);

        String jsonResponse = objectMapper.writeValueAsString(responseMap);

        KafkaMessage responseKafkaMessage = KafkaMessageUtils.createKafkaMessage(correlationId, jsonResponse, replyTo);

        producer.sendMessage(replyTo, responseKafkaMessage.toString());

        log.info("Sent response to topic '{}' with data: {}", replyTo, jsonResponse);
    }

    @KafkaListener(topics = "amanzat.box-api.price", groupId = "group-0")
    public void listenToGetCostRequest(ConsumerRecord<String, byte[]> record) throws JsonProcessingException {
        log.info("Received message from 'amanzat.box-api.price', body: {}", record.value());

        KafkaMessage message;
        try {
            message = objectMapper.readValue(record.value(), KafkaMessage.class);
        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
            return;
        }

        String correlationId = message.getCorrelationId();
        String replyTo = message.getReplyTo();
        String data = message.getData();

        String price = boxService.findPriceById(UUID.fromString(data)).toString();

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("price", price);

        String jsonResponse = objectMapper.writeValueAsString(responseMap);

        KafkaMessage responseKafkaMessage = KafkaMessageUtils.createKafkaMessage(correlationId, jsonResponse, replyTo);

        producer.sendMessage(replyTo, responseKafkaMessage.toString());

        log.info("Sent response to topic '{}' with body: {}", replyTo, jsonResponse);
    }

    @KafkaListener(topics = "amanzat.box-api.unblock", groupId = "group-0")
    public void listenToUnblockRequest(ConsumerRecord<String, byte[]> record) {
        log.info("Received message from 'amanzat.box-api.unblock' topic: {}", record.value());

        KafkaMessage message;
        try {
            message = objectMapper.readValue(record.value(), KafkaMessage.class);
        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
            return;
        }

        String body = message.getData();
        boxService.updateStatus(UUID.fromString(body), BoxStatus.AVAILABLE);
        log.info("Box with id: {} is now available", body);
    }
}