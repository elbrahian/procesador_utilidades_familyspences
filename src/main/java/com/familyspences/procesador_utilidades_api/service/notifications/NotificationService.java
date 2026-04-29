package com.familyspences.procesador_utilidades_api.service.notifications;

import com.familyspences.procesador_utilidades_api.domain.notifications.Notification;
import com.familyspences.procesador_utilidades_api.messaging.notifications.NotificationMessage;
import com.familyspences.procesador_utilidades_api.repository.notifications.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // ================== CREACIÓN ==================

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

    // ================== CONSULTAS ==================

    @Transactional(readOnly = true)
    public List<Notification> getByUserId(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnreadByUserId(UUID userId) {
        return notificationRepository.findUnreadByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Notification> getRecentByUserId(UUID userId) {
        return notificationRepository.findRecentByUserId(userId, LocalDateTime.now().minusHours(24));
    }

    @Transactional(readOnly = true)
    public Optional<Notification> getById(UUID id) {
        return notificationRepository.findById(id);
    }

    // ================== MARCAR COMO LEÍDA ==================

    @Transactional
    public boolean markAsRead(UUID id) {
        Optional<Notification> opt = notificationRepository.findById(id);
        if (opt.isEmpty()) {
            log.warn("Notification not found for markAsRead: {}", id);
            return false;
        }
        Notification notification = opt.get();
        notification.markAsRead();
        notificationRepository.save(notification);
        log.info("Notification marked as read: {}", id);
        return true;
    }

    @Transactional
    public int markAllAsRead(UUID userId) {
        int updated = notificationRepository.markAllAsReadByUserId(userId, LocalDateTime.now());
        log.info("Marked {} notifications as read for userId={}", updated, userId);
        return updated;
    }

    // ================== ELIMINACIÓN ==================

    @Transactional
    public boolean deleteById(UUID id) {
        if (!notificationRepository.existsById(id)) {
            log.warn("Notification not found for deletion: {}", id);
            return false;
        }
        notificationRepository.deleteById(id);
        log.info("Notification deleted: {}", id);
        return true;
    }

    @Transactional
    public int deleteReadByUserId(UUID userId) {
        int deleted = notificationRepository.deleteByUserIdAndReadTrue(userId);
        log.info("Deleted {} read notifications for userId={}", deleted, userId);
        return deleted;
    }
}
