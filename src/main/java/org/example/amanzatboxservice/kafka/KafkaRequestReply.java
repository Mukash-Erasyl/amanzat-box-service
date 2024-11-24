package org.example.amanzatboxservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsConfig;
import org.example.amanzatboxservice.dto.KafkaMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@EnableKafkaStreams
public class KafkaRequestReply {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ConcurrentHashMap<String, CompletableFuture<KafkaMessage>> pendingRequests = new ConcurrentHashMap<>();
    private KafkaStreams kafkaStreams;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StreamsConfig kafkaStreamsConfig;
    private static final String RESPONSE_TOPIC = "amanzat.box.response";

    public CompletableFuture<KafkaMessage> sendRequest(String data, String requestTopic) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<KafkaMessage> futureResponse = new CompletableFuture<>();
        pendingRequests.put(requestId, futureResponse);

        KafkaMessage requestMessage = new KafkaMessage(requestId, data, "REQUEST", RESPONSE_TOPIC);

        try {
            byte[] messageBytes = objectMapper.writeValueAsBytes(requestMessage);
            kafkaTemplate.send(requestTopic, messageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return futureResponse;
    }

    @PostConstruct
    public void startKafkaStream() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, byte[]> stream = streamsBuilder.stream(RESPONSE_TOPIC);

        stream.map((key, messageBytes) -> {
                    try {
                        KafkaMessage kafkaMessage = objectMapper.readValue(messageBytes, KafkaMessage.class);
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
