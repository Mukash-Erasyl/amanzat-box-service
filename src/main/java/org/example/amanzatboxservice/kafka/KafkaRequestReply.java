package org.example.amanzatboxservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsConfig;
import org.example.amanzatboxservice.dto.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@EnableKafkaStreams
public class KafkaRequestReply {

    private static final Logger log = LoggerFactory.getLogger(KafkaRequestReply.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ConcurrentHashMap<String, CompletableFuture<KafkaMessage>> pendingRequests = new ConcurrentHashMap<>();
    private KafkaStreams kafkaStreams;
    private final ObjectMapper objectMapper;
    private final StreamsConfig kafkaStreamsConfig;
    private static final String RESPONSE_TOPIC = "amanzat.box.response";

    public CompletableFuture<KafkaMessage> sendRequest(String data, String requestTopic) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<KafkaMessage> futureResponse = new CompletableFuture<>();
        pendingRequests.put(requestId, futureResponse);


        KafkaMessage requestMessage = new KafkaMessage(requestId, data, RESPONSE_TOPIC);

        log.info("Send and wait message: {}", requestMessage);
        try {
            String messageBytes = objectMapper.writeValueAsString(requestMessage);
            kafkaTemplate.send(requestTopic, messageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return futureResponse;
    }

    @PostConstruct
    public void startKafkaStream() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> stream = streamsBuilder.stream(RESPONSE_TOPIC);


        stream.map((key, messageBytes) -> {

                    try {
                        KafkaMessage kafkaMessage = objectMapper.readValue(messageBytes, KafkaMessage.class);
                        if (kafkaMessage != null) {
                            log.info("Successfully received response with correlationId: {}", kafkaMessage.getCorrelationId());

                        } else {
                            log.error("Invalid or missing data in response: {}", kafkaMessage);
                        }

                        String requestId = kafkaMessage.getCorrelationId();

                        CompletableFuture<KafkaMessage> futureResponse = pendingRequests.remove(requestId);
                        if (futureResponse != null) {
                            futureResponse.complete(kafkaMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return KeyValue.pair(key, messageBytes);
                })
                .to("audit-response-topic");

        kafkaStreams = new KafkaStreams(streamsBuilder.build(), kafkaStreamsConfig);
        kafkaStreams.start();
    }

    @PreDestroy
    public void close() {
        if (kafkaStreams != null) {
            kafkaStreams.close();
        }
    }
}
