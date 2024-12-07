package org.example.amanzatboxservice.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    private final ObjectMapper objectMapper;
    private final BoxService boxService;
    private final KafkaProducer producer;

    @KafkaListener(topics = "amanzat.box-api.block-price", groupId = "group-0")
    public void listenToBlockGetCostRequest(ConsumerRecord<String, String> record) {
        log.info("message: {}", record.value());

        try {
            JsonNode jsonNode = objectMapper.readTree(record.value());

            String correlationId = jsonNode.get("correlationId").asText();
            String data = jsonNode.get("data").asText();
            String replyTo = jsonNode.get("replyTo").asText();

            log.info("CorrelationId: {}", correlationId);
            log.info("data: {}", data);
            log.info("ReplyTo: {}", replyTo);

            boxService.updateStatus(UUID.fromString(data), BoxStatus.BOOKED);
            var price = boxService.findPriceById(UUID.fromString(data));

            Map<String, Integer> responseMap = new HashMap<>();
            responseMap.put("price", price.intValue());

            String jsonResponse = objectMapper.writeValueAsString(responseMap);

            var responseMessage = KafkaMessageUtils.createKafkaMessageDTO(correlationId, jsonResponse, "");
            producer.sendJsonMessage(replyTo, responseMessage);

            log.info("Sent response to topic '{}' with data: {}", replyTo, jsonResponse);

        } catch (Exception e) {
            log.error("Error parsing the message: {}", e.getMessage());
        }

    }

    @KafkaListener(topics = "amanzat.box-api.price", groupId = "group-0")
    public void listenToGetCostRequest(ConsumerRecord<String, String> record) {
        log.info("Received message from 'amanzat.box-api.price', body: {}", record.value());

        try {
            JsonNode jsonNode = objectMapper.readTree(record.value());

            String correlationId = jsonNode.get("correlationId").asText();
            String data = jsonNode.get("data").asText();
            String replyTo = jsonNode.get("replyTo").asText();

            log.info("CorrelationId: {}", correlationId);
            log.info("data: {}", data);
            log.info("ReplyTo: {}", replyTo);

            var price = boxService.findPriceById(UUID.fromString(data));

            Map<String, Integer> responseMap = new HashMap<>();
            responseMap.put("price", price.intValue());

            String jsonResponse = objectMapper.writeValueAsString(responseMap);

            var responseMessage = KafkaMessageUtils.createKafkaMessageDTO(correlationId, jsonResponse, "");
            producer.sendJsonMessage(replyTo, responseMessage);

            log.info("Sent response to topic '{}' with data: {}", replyTo, jsonResponse);

        } catch (Exception e) {
            log.error("Error parsing the message: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "amanzat.box-api.unblock", groupId = "group-0")
    public void listenToUnblockRequest(ConsumerRecord<String, String> record) {
        log.info("Received message from 'amanzat.box-api.unblock' topic: {}", record.value());

        try {
            JsonNode jsonNode = objectMapper.readTree(record.value());

            String correlationId = jsonNode.get("correlationId").asText();
            String data = jsonNode.get("data").asText();
            String replyTo = jsonNode.get("replyTo").asText();

            log.info("CorrelationId: {}", correlationId);
            log.info("data: {}", data);
            log.info("ReplyTo: {}", replyTo);

            boxService.updateStatus(UUID.fromString(data), BoxStatus.AVAILABLE);

            log.info("Box with id: {} is now available", data);

        } catch (Exception e) {
            log.error("Error parsing the message: {}", e.getMessage());
        }
    }
}
