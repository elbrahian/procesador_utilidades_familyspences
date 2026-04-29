package com.familyspences.procesador_utilidades_api.repository.notifications;

import com.familyspences.procesador_utilidades_api.domain.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query("SELECT n FROM Notification n WHERE n.userId = :userId ORDER BY n.createdAtInternal DESC")
    List<Notification> findByUserIdOrderByCreatedAtDesc(@Param("userId") UUID userId);

    @Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.read = false ORDER BY n.createdAtInternal DESC")
    List<Notification> findUnreadByUserId(@Param("userId") UUID userId);

    @Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.createdAtInternal > :since ORDER BY n.createdAtInternal DESC")
    List<Notification> findRecentByUserId(@Param("userId") UUID userId, @Param("since") LocalDateTime since);

    long countByUserIdAndReadFalse(UUID userId);

    @Modifying
    @Query("UPDATE Notification n SET n.read = true, n.readAtInternal = :readAt WHERE n.userId = :userId AND n.read = false")
    int markAllAsReadByUserId(@Param("userId") UUID userId, @Param("readAt") LocalDateTime readAt);

    @Modifying
    int deleteByUserIdAndReadTrue(UUID userId);
}
