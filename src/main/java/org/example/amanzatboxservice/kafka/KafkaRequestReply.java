package org.example.amanzatboxservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.example.amanzatboxservice.dto.KafkaMessageDTO;
import org.example.amanzatboxservice.utils.KafkaMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class KafkaRequestReply {
    private static final Logger log = LoggerFactory.getLogger(KafkaRequestReply.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ConcurrentHashMap<String, CompletableFuture<KafkaMessageDTO>> pendingRequests = new ConcurrentHashMap<>();
    private KafkaStreams kafkaStreams;
    private final ObjectMapper objectMapper;
    private final StreamsConfig kafkaStreamsConfig;
    private static final String RESPONSE_TOPIC = "amanzat.box.response";

    public CompletableFuture<KafkaMessageDTO> sendRequest(Object data, String requestTopic) {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<KafkaMessageDTO> futureResponse = new CompletableFuture<>();
        pendingRequests.put(requestId, futureResponse);

//        String serializedData;
//        try {
//            serializedData = objectMapper.writeValueAsString(data);
//        } catch (Exception e) {
//            log.error("Error serializing data", e);
//            return futureResponse;
//        }


        var requestMessage = KafkaMessageUtils.createKafkaMessageDTO(requestId, data, RESPONSE_TOPIC);
        log.info("Send and wait message: {}", requestMessage);
        try {
            kafkaTemplate.send(requestTopic, requestMessage);
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
                        var kafkaMessage = objectMapper.readValue(messageBytes, KafkaMessageDTO.class);
                        if (kafkaMessage != null) {
                            log.info("Successfully received response with correlationId: {}", kafkaMessage.getCorrelationId());

                        } else {
                            log.error("Invalid or missing data in response: {}", kafkaMessage);
                        }

                        String requestId = kafkaMessage.getCorrelationId();

                        CompletableFuture<KafkaMessageDTO> futureResponse = pendingRequests.remove(requestId);
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
