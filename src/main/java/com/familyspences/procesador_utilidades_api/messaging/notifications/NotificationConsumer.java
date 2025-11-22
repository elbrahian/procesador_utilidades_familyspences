package com.familyspences.procesador_utilidades_api.messaging.notifications;

import com.familyspences.procesador_utilidades_api.config.messages.notifications.NotificationQueueConfig;
import com.familyspences.procesador_utilidades_api.service.notifications.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = NotificationQueueConfig.NOTIFICATION_QUEUE)
    public void receiveNotification(NotificationMessage message) {
        log.info("Received notification message: userId={}, type={}, priority={}",
                message.getUserId(), message.getType(), message.getPriority());

        notificationService.createFromMessage(message);

        log.info("Notification persisted successfully");
    }
}
