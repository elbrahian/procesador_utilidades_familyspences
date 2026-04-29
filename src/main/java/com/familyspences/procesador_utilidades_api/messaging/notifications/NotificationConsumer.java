package com.familyspences.procesador_utilidades_api.messaging.notifications;

import com.familyspences.procesador_utilidades_api.config.messages.notifications.NotificationQueueConfig;
import com.familyspences.procesador_utilidades_api.service.notifications.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

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

    @RabbitListener(queues = NotificationQueueConfig.NOTIFICATION_DELETE_QUEUE)
    public void handleDelete(Map<String, Object> data) {
        try {
            UUID id = UUID.fromString(String.valueOf(data.get("notificationId")));
            log.info("Received DELETE notification event: id={}", id);
            boolean deleted = notificationService.deleteById(id);
            if (!deleted) log.warn("Notification not found for deletion: {}", id);
        } catch (Exception e) {
            log.error("Error processing DELETE notification event: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = NotificationQueueConfig.NOTIFICATION_MARK_READ_QUEUE)
    public void handleMarkAsRead(Map<String, Object> data) {
        try {
            UUID id = UUID.fromString(String.valueOf(data.get("notificationId")));
            log.info("Received MARK_READ notification event: id={}", id);
            boolean updated = notificationService.markAsRead(id);
            if (!updated) log.warn("Notification not found for markAsRead: {}", id);
        } catch (Exception e) {
            log.error("Error processing MARK_READ notification event: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = NotificationQueueConfig.NOTIFICATION_MARK_ALL_READ_QUEUE)
    public void handleMarkAllAsRead(Map<String, Object> data) {
        try {
            UUID userId = UUID.fromString(String.valueOf(data.get("userId")));
            log.info("Received MARK_ALL_READ notification event: userId={}", userId);
            int updated = notificationService.markAllAsRead(userId);
            log.info("Marked {} notifications as read for userId={}", updated, userId);
        } catch (Exception e) {
            log.error("Error processing MARK_ALL_READ notification event: {}", e.getMessage(), e);
        }
    }
}
