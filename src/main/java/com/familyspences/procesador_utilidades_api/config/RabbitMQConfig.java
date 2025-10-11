package com.familyspences.procesador_utilidades_api.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${advertisement.exchange}")
    private String createExchange;

    @Value("${mensaje.publicidad.actualizar.exchange-name}")
    private String updateExchange;

    @Value("${mensaje.publicidad.eliminar.exchange-name}")
    private String deleteExchange;

    @Value("${advertisement.queue.create}")
    private String createQueue;

    @Value("${advertisement.queue.update}")
    private String updateQueue;

    @Value("${advertisement.queue.delete}")
    private String deleteQueue;

    @Value("${advertisement.routing-key.create}")
    private String createRoutingKey;

    @Value("${advertisement.routing-key.update}")
    private String updateRoutingKey;

    @Value("${advertisement.routing-key.delete}")
    private String deleteRoutingKey;

    @Bean
    public DirectExchange createAdvertisementExchange() {
        return new DirectExchange(createExchange);
    }

    @Bean
    public DirectExchange updateAdvertisementExchange() {
        return new DirectExchange(updateExchange);
    }

    @Bean
    public DirectExchange deleteAdvertisementExchange() {
        return new DirectExchange(deleteExchange);
    }

    @Bean
    public Queue createAdvertisementQueue() {
        return new Queue(createQueue);
    }

    @Bean
    public Queue updateAdvertisementQueue() {
        return new Queue(updateQueue);
    }

    @Bean
    public Queue deleteAdvertisementQueue() {
        return new Queue(deleteQueue);
    }

    @Bean
    public Binding createBinding() {
        return BindingBuilder.bind(createAdvertisementQueue())
                .to(createAdvertisementExchange())
                .with(createRoutingKey);
    }

    @Bean
    public Binding updateBinding() {
        return BindingBuilder.bind(updateAdvertisementQueue())
                .to(updateAdvertisementExchange())
                .with(updateRoutingKey);
    }

    @Bean
    public Binding deleteBinding() {
        return BindingBuilder.bind(deleteAdvertisementQueue())
                .to(deleteAdvertisementExchange())
                .with(deleteRoutingKey);
    }
}