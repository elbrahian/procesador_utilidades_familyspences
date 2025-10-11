package com.familyspences.procesador_utilidades_api.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //exchange
    public static final String TASKS_EXCHANGE = "tasks.exchange";
    //queue
    public static final String TASKS_QUEUE = "tasks.read.queue";
    //routing key
    public static final String TASK_CREATED_ROUTING_KEY = "task.created";
    public static final String TASK_UPDATED_ROUTING_KEY = "task.updated";
    public static final String TASK_DELETED_ROUTING_KEY = "task.deleted";

    @Bean
    public TopicExchange tasksExchange() {
        return new TopicExchange(TASKS_EXCHANGE);
    }

    @Bean
    public Queue tasksQueue() {
        return new Queue(TASKS_QUEUE, true);
    }

    @Bean
    public Binding bindingCreated(Queue tasksQueue, TopicExchange tasksExchange) {
        return BindingBuilder.bind(tasksQueue).to(tasksExchange).with("task.*");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
