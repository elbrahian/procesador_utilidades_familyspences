package com.familyspences.procesador_utilidades_api.messaging.notifications;

import com.familyspences.procesador_utilidades_api.config.messages.notifications.NotificationQueueConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(NotificationMessage message) {
        rabbitTemplate.convertAndSend(
                NotificationQueueConfig.NOTIFICATION_EXCHANGE,
                NotificationQueueConfig.NOTIFICATION_ROUTING_KEY,
                message
        );
    }
}
