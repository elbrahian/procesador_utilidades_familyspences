package com.familyspences.procesador_utilidades_api.service.notifications;

import com.familyspences.procesador_utilidades_api.domain.notifications.Notification;
import com.familyspences.procesador_utilidades_api.repository.notifications.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public List<Notification> getAllByUser(UUID userId) {
        return repository.findByUserId(userId);
    }

    public List<Notification> getUnreadByUser(UUID userId) {
        return repository.findByUserIdAndReadFalse(userId);
    }

    public Notification create(Notification notification) {
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        return repository.save(notification);
    }

    public Notification markAsRead(UUID id) {
        Notification n = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        n.setReadAt(LocalDateTime.now());
        return repository.save(n);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
