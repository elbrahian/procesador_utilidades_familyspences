package com.familyspences.procesador_utilidades_api.messaging.notifications;

import com.familyspences.procesador_utilidades_api.domain.notifications.Notification.NotificationPriority;
import com.familyspences.procesador_utilidades_api.domain.notifications.Notification.NotificationType;

import java.io.Serializable;
import java.util.UUID;

public class NotificationMessage implements Serializable {

    private UUID userId;
    private String message;
    private NotificationType type;
    private NotificationPriority priority;

    public NotificationMessage() {
    }

    public NotificationMessage(UUID userId, String message,
                               NotificationType type, NotificationPriority priority) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.priority = priority;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public NotificationPriority getPriority() { return priority; }
    public void setPriority(NotificationPriority priority) { this.priority = priority; }
}
