package org.example.amanzatboxservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.amanzatboxservice.dto.KafkaMessage;
import org.example.amanzatboxservice.enums.BoxStatus;
import org.example.amanzatboxservice.service.BoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final BoxService boxService;
    private final KafkaProducer producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "amanzat.box-api.request.block-get-cost", groupId = "group-0")
    public void listenToBlockGetCostRequest(ConsumerRecord<String, byte[]> record) {
        log.info("Received message from 'amanzat.box-api.request.block-get-cost' topic: {}", record.value());

        KafkaMessage message;
        try {
            message = objectMapper.readValue(record.value(), KafkaMessage.class);
        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
            return;
        }

        String correlationId = message.getCorrelationId();
        String replyTo = message.getReplyTo();
        String body = message.getBody();

        boxService.updateStatus(UUID.fromString(body), BoxStatus.BOOKED);
        String responseMessage = boxService.findPriceById(UUID.fromString(body)).toString();

        KafkaMessage responseKafkaMessage = new KafkaMessage();
        responseKafkaMessage.setCorrelationId(correlationId);
        responseKafkaMessage.setRequestType(KafkaMessage.RequestType.RESPONSE);
        responseKafkaMessage.setBody(responseMessage);
        responseKafkaMessage.setReplyTo(replyTo);

        producer.sendMessage(replyTo, responseKafkaMessage.toString());

        log.info("Sent response to topic '{}' with body: {}", replyTo, responseMessage);
    }

    @KafkaListener(topics = "amanzat.box-api.get-cost", groupId = "group-0")
    public void listenToGetCostRequest(ConsumerRecord<String, byte[]> record) {
        log.info("Received message from 'amanzat.box-api.get-cost", record.value());

        KafkaMessage message;
        try {
            message = objectMapper.readValue(record.value(), KafkaMessage.class);
        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
            return;
        }

        String correlationId = message.getCorrelationId();
        String replyTo = message.getReplyTo();
        String body = message.getBody();

        String responseMessage = boxService.findPriceById(UUID.fromString(body)).toString();

        KafkaMessage responseKafkaMessage = new KafkaMessage();
        responseKafkaMessage.setCorrelationId(correlationId);
        responseKafkaMessage.setRequestType(KafkaMessage.RequestType.RESPONSE);
        responseKafkaMessage.setBody(responseMessage);
        responseKafkaMessage.setReplyTo(replyTo);

        producer.sendMessage(replyTo, responseKafkaMessage.toString());

        log.info("Sent response to topic '{}' with body: {}", replyTo, responseMessage);
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

        String body = message.getBody();
        boxService.updateStatus(UUID.fromString(body), BoxStatus.AVAILABLE);
        log.info("Box with id: {} now available", body);

    }

}
