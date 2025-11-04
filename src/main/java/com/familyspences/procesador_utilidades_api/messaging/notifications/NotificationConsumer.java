
package com.familyspences.procesador_utilidades_api.messaging.notifications;

import com.familyspences.procesador_utilidades_api.config.notification.NotificationQueueConfig;
import com.familyspences.procesador_utilidades_api.domain.notifications.Notification;
import com.familyspences.procesador_utilidades_api.service.notifications.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);
    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = NotificationQueueConfig.QUEUE_NOTIFICATION_CREATE)
    public void handleCreate(Notification notification) {
        log.info("📩 [CREATE] Notification received: {}", notification.getTitle());
        notificationService.create(notification);
    }

    @RabbitListener(queues = NotificationQueueConfig.QUEUE_NOTIFICATION_READ)
    public void handleRead(String notificationId) {
        log.info("📨 [READ] Marking notification {} as read", notificationId);
        notificationService.markAsRead(java.util.UUID.fromString(notificationId));
    }

    @RabbitListener(queues = NotificationQueueConfig.QUEUE_NOTIFICATION_DELETE)
    public void handleDelete(String notificationId) {
        log.info("🗑️ [DELETE] Removing notification {}", notificationId);
        notificationService.delete(java.util.UUID.fromString(notificationId));
    }
}
