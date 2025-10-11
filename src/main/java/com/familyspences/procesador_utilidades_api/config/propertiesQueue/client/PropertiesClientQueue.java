package com.familyspences.procesador_utilidades_api.config.propertiesQueue.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client")
@PropertySource("classpath:client.properties")
public class PropertiesClientQueue {

    private QueueDetails create;
    private QueueDetails delete;
    private QueueDetails update;

    @Getter
    @Setter
    public static class QueueDetails {
        private String exchangeName;
        private String routingKey;
        private String queueName;
    }
}
