package controllers;

import domain.Notification;
import service.NotificationService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationService.getAll());
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getByUser(userId));
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<Notification> getById(@PathVariable UUID id) {
        return notificationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users/{userId}/notifications")
    public ResponseEntity<Notification> create(@PathVariable UUID userId,
                                               @RequestBody CreateNotificationRequest body) {
        Notification created =
                notificationService.createForUser(userId, body.title, body.message, body.type);
        return ResponseEntity.created(URI.create("/api/v1/notifications/" + created.getId()))
                .body(created);
    }

    @PatchMapping("/notifications/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable UUID id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        notificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/notifications/unread-count")
    public ResponseEntity<Long> unreadCount(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.unreadCount(userId));
    }

    public static class CreateNotificationRequest {
        @NotBlank public String title;
        @NotBlank public String message;
        @NotNull  public String type;
    }
}
