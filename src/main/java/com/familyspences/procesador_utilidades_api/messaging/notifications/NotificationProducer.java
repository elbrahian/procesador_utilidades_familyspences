package com.familyspences.procesador_utilidades_api.messaging.notifications;

import com.familyspences.procesador_utilidades_api.config.notification.NotificationQueueConfig;
import com.familyspences.procesador_utilidades_api.domain.notifications.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Envía una notificación nueva a la cola "notification.create"
     */
    public void sendCreateNotification(Notification notification) {
        log.info("📤 [PUBLISH] Enviando notificación: {} -> {}", notification.getId(), notification.getTitle());
        rabbitTemplate.convertAndSend(
                NotificationQueueConfig.EXCHANGE_NAME,
                NotificationQueueConfig.ROUTING_KEY_CREATE,
                notification
        );
    }

    /**
     * Envía una señal para marcar una notificación como leída
     */
    public void sendReadNotification(String notificationId) {
        log.info("📤 [PUBLISH] Marcando notificación como leída: {}", notificationId);
        rabbitTemplate.convertAndSend(
                NotificationQueueConfig.EXCHANGE_NAME,
                NotificationQueueConfig.ROUTING_KEY_READ,
                notificationId
        );
    }

    /**
     * Envía una señal para eliminar una notificación
     */
    public void sendDeleteNotification(String notificationId) {
        log.info("📤 [PUBLISH] Eliminando notificación: {}", notificationId);
        rabbitTemplate.convertAndSend(
                NotificationQueueConfig.EXCHANGE_NAME,
                NotificationQueueConfig.ROUTING_KEY_DELETE,
                notificationId
        );
    }
}
