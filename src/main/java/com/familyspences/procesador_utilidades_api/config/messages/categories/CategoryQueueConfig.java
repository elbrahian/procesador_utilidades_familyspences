package com.familyspences.procesador_utilidades_api.config.messages.categories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryQueueConfig {

    private static final Logger log = LoggerFactory.getLogger(CategoryQueueConfig.class);

    public static final String EXCHANGE_NAME = "x.category.exchange";

    public static final String QUEUE_CATEGORY_CREATE = "q.category.create";
    public static final String QUEUE_CATEGORY_UPDATE = "q.category.update";
    public static final String QUEUE_CATEGORY_DELETE = "q.category.delete";

    public static final String ROUTING_KEY_CREATE = "category.create";
    public static final String ROUTING_KEY_UPDATE = "category.update";
    public static final String ROUTING_KEY_DELETE = "category.delete";

    public CategoryQueueConfig() {
        log.info("========================================");
        log.info("CategoryQueueConfig INITIALIZED!");
        log.info("========================================");
    }

    @Bean
    public TopicExchange categoryExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue categoryCreateQueue() {
        return QueueBuilder.durable(QUEUE_CATEGORY_CREATE).build();
    }

    @Bean
    public Queue categoryUpdateQueue() {
        return QueueBuilder.durable(QUEUE_CATEGORY_UPDATE).build();
    }

    @Bean
    public Queue categoryDeleteQueue() {
        return QueueBuilder.durable(QUEUE_CATEGORY_DELETE).build();
    }

    @Bean
    public Binding categoryCreateBinding() {
        return BindingBuilder
                .bind(categoryCreateQueue())
                .to(categoryExchange())
                .with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding categoryUpdateBinding() {
        return BindingBuilder
                .bind(categoryUpdateQueue())
                .to(categoryExchange())
                .with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding categoryDeleteBinding() {
        return BindingBuilder
                .bind(categoryDeleteQueue())
                .to(categoryExchange())
                .with(ROUTING_KEY_DELETE);
    }
}