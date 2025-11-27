package com.familyspences.procesador_utilidades_api.config.messages.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductQueueConfig {

    private static final Logger log = LoggerFactory.getLogger(ProductQueueConfig.class);

    public static final String EXCHANGE_NAME = "x.product.exchange";
    public static final String PRODUCT_CREATE_QUEUE = "product.create.queue";
    public static final String ROUTING_KEY_CREATE = "product.create";

    public ProductQueueConfig() {
        log.info("========================================");
        log.info("ProductQueueConfig INITIALIZED!");
        log.info("========================================");
    }

    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue productCreateQueue() {
        return QueueBuilder.durable(PRODUCT_CREATE_QUEUE).build();
    }

    @Bean
    public Binding productCreateBinding() {
        return BindingBuilder
                .bind(productCreateQueue())
                .to(productExchange())
                .with(ROUTING_KEY_CREATE);
    }
}
