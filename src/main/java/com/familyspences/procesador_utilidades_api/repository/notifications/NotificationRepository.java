package com.familyspences.procesador_utilidades_api.repository.notifications;

import com.familyspences.procesador_utilidades_api.domain.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
