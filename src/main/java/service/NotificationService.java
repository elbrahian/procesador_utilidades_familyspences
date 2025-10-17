package service;

import domain.Notification;
import repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Notification> getByUser(UUID userId) {
        return notificationRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public Optional<Notification> getById(UUID id) {
        return notificationRepository.findById(id);
    }

    public Notification createForUser(UUID userId, String title, String message, String type) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        n.setRead(false);
        return notificationRepository.save(n);
    }

    public Notification save(Notification n) {
        return notificationRepository.save(n);
    }

    public Notification markAsRead(UUID id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));
        if (!n.isRead()) {
            n.setRead(true);
            n.setReadAt(LocalDateTime.now());
        }
        return notificationRepository.save(n);
    }

    public void delete(UUID id) {
        notificationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long unreadCount(UUID userId) {
        return notificationRepository.countByUserIdAndReadIsFalse(userId);
    }
}
