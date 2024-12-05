package org.example.amanzatboxservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    private String bootstrapServers;
    private Consumer consumer;
    private Producer producer;
    private Streams streams;
    private Topics topics;

    @Getter
    @Setter
    public static class Consumer {
        private String groupId;
    }

    @Getter
    @Setter
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
    }

    @Getter
    @Setter
    public static class Streams {
        private String applicationId;
        private String defaultKeySerde;
        private String defaultValueSerde;
    }

    @Getter
    @Setter
    public static class Topics {
        private String boxFind;
        private String boxResponse;
        private String auditResponse;
    }
}
