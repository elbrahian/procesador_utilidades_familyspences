package repository;

import domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // Más recientes primero
    List<Notification> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    // Paginado
    Page<Notification> findAllByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    // Conteo de no leídas
    long countByUserIdAndReadIsFalse(UUID userId);

    // Filtros por tipo / prioridad (como String para evitar el error de enums)
    List<Notification> findAllByUserIdAndTypeOrderByCreatedAtDesc(UUID userId, String type);

    List<Notification> findAllByUserIdAndPriorityOrderByCreatedAtDesc(UUID userId, String priority);

    // Recientes desde 'since'
    List<Notification> findAllByUserIdAndCreatedAtAfterOrderByCreatedAtDesc(UUID userId, LocalDateTime since);

    // Marcar todas como leídas
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           UPDATE Notification n
           SET n.read = true, n.readAt = :readAt
           WHERE n.userId = :userId AND n.read = false
           """)
    int markAllAsReadByUserId(@Param("userId") UUID userId,
                              @Param("readAt") LocalDateTime readAt);

    // Borrar leídas de un usuario
    @Transactional
    int deleteByUserIdAndReadTrue(UUID userId);
}
