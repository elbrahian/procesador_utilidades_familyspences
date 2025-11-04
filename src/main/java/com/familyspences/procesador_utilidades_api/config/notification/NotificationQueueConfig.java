package com.familyspences.procesador_utilidades_api.config.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationQueueConfig {

    private static final Logger log = LoggerFactory.getLogger(NotificationQueueConfig.class);

    public static final String EXCHANGE_NAME = "x.notification.exchange";

    public static final String QUEUE_NOTIFICATION_CREATE = "q.notification.create";
    public static final String QUEUE_NOTIFICATION_READ = "q.notification.read";
    public static final String QUEUE_NOTIFICATION_DELETE = "q.notification.delete";

    public static final String ROUTING_KEY_CREATE = "notification.create";
    public static final String ROUTING_KEY_READ = "notification.read";
    public static final String ROUTING_KEY_DELETE = "notification.delete";

    public NotificationQueueConfig() {
        log.info("========================================");
        log.info("NotificationQueueConfig INITIALIZED!");
        log.info("========================================");
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue notificationCreateQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICATION_CREATE).build();
    }

    @Bean
    public Queue notificationReadQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICATION_READ).build();
    }

    @Bean
    public Queue notificationDeleteQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICATION_DELETE).build();
    }

    @Bean
    public Binding notificationCreateBinding() {
        return BindingBuilder.bind(notificationCreateQueue())
                .to(notificationExchange())
                .with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding notificationReadBinding() {
        return BindingBuilder.bind(notificationReadQueue())
                .to(notificationExchange())
                .with(ROUTING_KEY_READ);
    }

    @Bean
    public Binding notificationDeleteBinding() {
        return BindingBuilder.bind(notificationDeleteQueue())
                .to(notificationExchange())
                .with(ROUTING_KEY_DELETE);
    }
}
