package com.familyspences.procesador_utilidades_api.messaging.categories;

import com.familyspences.procesador_utilidades_api.config.messages.categories.CategoryQueueConfig;
import com.familyspences.procesador_utilidades_api.config.messages.categories.dto.CategoryDTO;
import com.familyspences.procesador_utilidades_api.domain.notifications.Notification.NotificationPriority;
import com.familyspences.procesador_utilidades_api.domain.notifications.Notification.NotificationType;
import com.familyspences.procesador_utilidades_api.messaging.notifications.NotificationMessage;
import com.familyspences.procesador_utilidades_api.service.categories.CategoryService;
import com.familyspences.procesador_utilidades_api.service.notifications.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(CategoryConsumer.class);
    private final CategoryService categoryService;
    private final NotificationService notificationService;

    public CategoryConsumer(CategoryService categoryService, NotificationService notificationService) {
        this.categoryService = categoryService;
        this.notificationService = notificationService;
        log.info("========================================");
        log.info("CategoryConsumer INITIALIZED!");
        log.info("========================================");
    }

    @RabbitListener(queues = CategoryQueueConfig.QUEUE_CATEGORY_CREATE)
    public void handleCategoryCreate(CategoryDTO categoryDTO) {
        log.info("✅ Received Category CREATE event: {}", categoryDTO.getName());
        try {
            categoryService.saveFromProducer(categoryDTO);
            log.info("✅ Category saved successfully: {}", categoryDTO.getId());
            sendCategoryNotification(categoryDTO.getUserId(),
                    "Categoría creada: " + categoryDTO.getName(),
                    NotificationType.SUCCESS, NotificationPriority.NORMAL);
        } catch (Exception e) {
            log.error("❌ Error processing Category CREATE event. Category ID: {}. Error: {}",
                    categoryDTO.getId(), e.getMessage(), e);
        }
    }

    @RabbitListener(queues = CategoryQueueConfig.QUEUE_CATEGORY_UPDATE)
    public void handleCategoryUpdate(CategoryDTO categoryDTO) {
        log.info("✅ Received Category UPDATE event: {}", categoryDTO.getName());
        try {
            categoryService.updateFromProducer(categoryDTO);
            log.info("✅ Category updated successfully: {}", categoryDTO.getId());
            sendCategoryNotification(categoryDTO.getUserId(),
                    "Categoría actualizada: " + categoryDTO.getName(),
                    NotificationType.INFO, NotificationPriority.NORMAL);
        } catch (Exception e) {
            log.error("❌ Error processing Category UPDATE event. Category ID: {}. Error: {}",
                    categoryDTO.getId(), e.getMessage(), e);
        }
    }

    @RabbitListener(queues = CategoryQueueConfig.QUEUE_CATEGORY_DELETE)
    public void handleCategoryDelete(Map<String, Object> data) {
        log.info("✅ Received Category DELETE event: {}", data);
        try {
            String categoryId = String.valueOf(data.get("categoryId"));
            String familyId = String.valueOf(data.get("familyId"));
            String userIdStr = data.get("userId") != null ? String.valueOf(data.get("userId")) : null;

            Map<String, String> safeData = new HashMap<>();
            safeData.put("categoryId", categoryId);
            safeData.put("familyId", familyId);

            categoryService.deleteFromProducer(safeData);
            log.info("✅ Category deleted successfully: {}", categoryId);

            if (userIdStr != null) {
                sendCategoryNotification(java.util.UUID.fromString(userIdStr),
                        "Categoría eliminada",
                        NotificationType.WARNING, NotificationPriority.NORMAL);
            }
        } catch (Exception e) {
            log.error("❌ Error processing Category DELETE event. Data: {}. Error: {}",
                    data, e.getMessage(), e);
        }
    }

    private void sendCategoryNotification(java.util.UUID userId, String message,
                                          NotificationType type, NotificationPriority priority) {
        if (userId == null) return;
        try {
            notificationService.createFromMessage(new NotificationMessage(userId, message, type, priority));
        } catch (Exception e) {
            log.warn("Could not create category notification for userId={}: {}", userId, e.getMessage());
        }
    }
}