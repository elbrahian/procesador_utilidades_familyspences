package com.familyspences.procesador_utilidades_api.config.messages.notifications;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationQueueConfig {

    public static final String NOTIFICATION_QUEUE = "familyspences.notifications.queue";
    public static final String NOTIFICATION_EXCHANGE = "familyspences.notifications.exchange";
    public static final String NOTIFICATION_ROUTING_KEY = "familyspences.notifications.key";

    public static final String NOTIFICATION_DELETE_QUEUE = "familyspences.notifications.delete.queue";
    public static final String NOTIFICATION_DELETE_ROUTING_KEY = "familyspences.notifications.delete";

    public static final String NOTIFICATION_MARK_READ_QUEUE = "familyspences.notifications.mark.read.queue";
    public static final String NOTIFICATION_MARK_READ_ROUTING_KEY = "familyspences.notifications.mark.read";

    public static final String NOTIFICATION_MARK_ALL_READ_QUEUE = "familyspences.notifications.mark.all.read.queue";
    public static final String NOTIFICATION_MARK_ALL_READ_ROUTING_KEY = "familyspences.notifications.mark.all.read";

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue notificationDeleteQueue() {
        return new Queue(NOTIFICATION_DELETE_QUEUE, true);
    }

    @Bean
    public Queue notificationMarkReadQueue() {
        return new Queue(NOTIFICATION_MARK_READ_QUEUE, true);
    }

    @Bean
    public Queue notificationMarkAllReadQueue() {
        return new Queue(NOTIFICATION_MARK_ALL_READ_QUEUE, true);
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue,
                                       DirectExchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding notificationDeleteBinding(Queue notificationDeleteQueue,
                                             DirectExchange notificationExchange) {
        return BindingBuilder.bind(notificationDeleteQueue).to(notificationExchange).with(NOTIFICATION_DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding notificationMarkReadBinding(Queue notificationMarkReadQueue,
                                               DirectExchange notificationExchange) {
        return BindingBuilder.bind(notificationMarkReadQueue).to(notificationExchange).with(NOTIFICATION_MARK_READ_ROUTING_KEY);
    }

    @Bean
    public Binding notificationMarkAllReadBinding(Queue notificationMarkAllReadQueue,
                                                  DirectExchange notificationExchange) {
        return BindingBuilder.bind(notificationMarkAllReadQueue).to(notificationExchange).with(NOTIFICATION_MARK_ALL_READ_ROUTING_KEY);
    }
}
