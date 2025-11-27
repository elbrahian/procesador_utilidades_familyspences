package com.familyspences.procesador_utilidades_api.service.notifications;

import com.familyspences.procesador_utilidades_api.domain.notifications.Notification;
import com.familyspences.procesador_utilidades_api.messaging.notifications.NotificationMessage;
import com.familyspences.procesador_utilidades_api.repository.notifications.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public Notification createFromMessage(NotificationMessage message) {
        Notification notification = new Notification(
                message.getUserId(),
                message.getMessage(),
                message.getType(),
                message.getPriority()
        );

        return notificationRepository.save(notification);
    }
}
