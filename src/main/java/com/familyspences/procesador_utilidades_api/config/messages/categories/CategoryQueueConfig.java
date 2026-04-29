package com.familyspences.procesador_utilidades_api.config.messages.categories;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryQueueConfig {

    public static final String EXCHANGE_NAME = "x.category.exchange";

    public static final String QUEUE_CATEGORY_CREATE = "q.category.create";
    public static final String QUEUE_CATEGORY_UPDATE = "q.category.update";
    public static final String QUEUE_CATEGORY_DELETE = "q.category.delete";

    public static final String ROUTING_KEY_CREATE = "category.create";
    public static final String ROUTING_KEY_UPDATE = "category.update";
    public static final String ROUTING_KEY_DELETE = "category.delete";

    @Bean
    public DirectExchange categoryExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue categoryCreateQueue() {
        return new Queue(QUEUE_CATEGORY_CREATE, true); // true = durable
    }

    @Bean
    public Queue categoryUpdateQueue() {
        return new Queue(QUEUE_CATEGORY_UPDATE, true);
    }

    @Bean
    public Queue categoryDeleteQueue() {
        return new Queue(QUEUE_CATEGORY_DELETE, true);
    }


    @Bean
    public Binding bindCategoryCreate(Queue categoryCreateQueue, DirectExchange categoryExchange) {
        return BindingBuilder
                .bind(categoryCreateQueue)
                .to(categoryExchange)
                .with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding bindCategoryUpdate(Queue categoryUpdateQueue, DirectExchange categoryExchange) {
        return BindingBuilder
                .bind(categoryUpdateQueue)
                .to(categoryExchange)
                .with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindCategoryDelete(Queue categoryDeleteQueue, DirectExchange categoryExchange) {
        return BindingBuilder
                .bind(categoryDeleteQueue)
                .to(categoryExchange)
                .with(ROUTING_KEY_DELETE);
    }
}