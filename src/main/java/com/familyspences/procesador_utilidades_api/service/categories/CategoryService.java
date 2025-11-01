package com.familyspences.procesador_utilidades_api.service.categories;

import com.familyspences.procesador_utilidades_api.domain.categories.Category;
import com.familyspences.procesador_utilidades_api.repository.categories.IRepositoryCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final IRepositoryCategory repository;

    public CategoryService(IRepositoryCategory repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveFromProducer(Category category) {
        log.info("Saving category from producer: {}", category);
        repository.save(category);
    }

    @Transactional
    public void updateFromProducer(Category updatedCategory) {
        try {
            UUID categoryId = updatedCategory.getId();

            if (categoryId == null) {
                log.warn("Missing id in update event: {}", updatedCategory);
                return;
            }

            Optional<Category> existingOpt = repository.findById(categoryId);
            if (existingOpt.isEmpty()) {
                log.warn("Category not found for update. Category ID: {}", categoryId);
                return;
            }

            Category existing = existingOpt.get();
            existing.setName(updatedCategory.getName());
            existing.setCategoryType(updatedCategory.getCategoryType());
            existing.setDescription(updatedCategory.getDescription());
            existing.setAllocatedBudget(updatedCategory.getAllocatedBudget());
            existing.setBudgetPeriod(updatedCategory.getBudgetPeriod());
            existing.setFamilyId(updatedCategory.getFamilyId());

            repository.save(existing);
            log.info("Category updated successfully: {}", categoryId);

        } catch (Exception e) {
            log.error("Error processing Category UPDATE event: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteFromProducer(Map<String, String> data) {
        try {
            String categoryStr = data.get("categoryId");

            if (categoryStr == null) {
                log.warn("Missing categoryId in DELETE event: {}", data);
                return;
            }

            UUID categoryId = UUID.fromString(categoryStr);

            if (repository.existsById(categoryId)) {
                repository.deleteById(categoryId);
                log.info("Category deleted successfully: {}", categoryId);
            } else {
                log.warn("Category with id {} not found", categoryId);
            }

        } catch (Exception e) {
            log.error("Error deleting category from producer event: {}", e.getMessage(), e);
        }
    }
}