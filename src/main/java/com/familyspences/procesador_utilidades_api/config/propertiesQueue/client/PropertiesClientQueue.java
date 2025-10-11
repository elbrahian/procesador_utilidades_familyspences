package com.familyspences.procesador_utilidades_api.config.propertiesQueue.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mensaje.certificacion-usuarios.borrado")
public class PropertiesClientQueue {

    private String exchangeName;
    private String routingKey;
    private String queueName;
}