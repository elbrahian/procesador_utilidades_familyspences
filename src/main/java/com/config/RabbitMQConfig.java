package com.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchanges
    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";
    public static final String NOTIFICATION_DLX_EXCHANGE = "notification.dlx.exchange";

    // Queues
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String NOTIFICATION_DLQ = "notification.dlq";
    public static final String HIGH_PRIORITY_QUEUE = "notification.high.priority.queue";
    public static final String URGENT_PRIORITY_QUEUE = "notification.urgent.priority.queue";


    // Routing Keys
    public static final String NOTIFICATION_ROUTING_KEY = "notification.create";
    public static final String HIGH_PRIORITY_ROUTING_KEY = "notification.high.priority";
    public static final String URGENT_PRIORITY_ROUTING_KEY = "notification.urgent.priority";
    public static final String DLQ_ROUTING_KEY = "notification.dlq";


    // Converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // Exchanges
    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange notificationDLXExchange() {
        return new DirectExchange(NOTIFICATION_DLX_EXCHANGE, true, false);
    }

    // Queues
    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .withArgument("x-message-ttl", 3600000)
                .build();
    }

    @Bean
    public Queue highPriorityQueue() {
        return QueueBuilder.durable(HIGH_PRIORITY_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .withArgument("x-max-priority", 10)
                .build();
    }

    @Bean
    public Queue urgentPriorityQueue() {
        return QueueBuilder.durable(URGENT_PRIORITY_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .withArgument("x-max-priority", 10)
                .build();
    }

    @Bean
    public Queue notificationDLQ() {
        return QueueBuilder.durable(NOTIFICATION_DLQ).build();
    }

    // Bindings
    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding highPriorityBinding(Queue highPriorityQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(highPriorityQueue)
                .to(notificationExchange)
                .with(HIGH_PRIORITY_ROUTING_KEY);
    }

    @Bean
    public Binding urgentPriorityBinding(Queue urgentPriorityQueue, TopicExchange notificationExchange) {
        return BindingBuilder.bind(urgentPriorityQueue)
                .to(notificationExchange)
                .with(URGENT_PRIORITY_ROUTING_KEY);
    }

    @Bean
    public Binding dlqBinding(Queue notificationDLQ, DirectExchange notificationDLXExchange) {
        return BindingBuilder.bind(notificationDLQ)
                .to(notificationDLXExchange)
                .with(DLQ_ROUTING_KEY);
    }
}
