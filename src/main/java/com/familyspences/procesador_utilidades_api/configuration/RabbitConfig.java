package com.familyspences.procesador_utilidades_api.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange("product.exchange");
    }

    @Bean
    public Queue productCreateQueue() {
        return new Queue("product.create.queue");
    }

    @Bean
    public Binding binding(Queue productCreateQueue, DirectExchange productExchange) {
        return BindingBuilder.bind(productCreateQueue).to(productExchange).with("product.create");
    }
}
