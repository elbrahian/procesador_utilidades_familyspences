package com.familyspences.procesador_utilidades_api.service.categories;

import com.familyspences.procesador_utilidades_api.config.messages.categories.dto.CategoryDTO;
import com.familyspences.procesador_utilidades_api.domain.categories.BudgetPeriod;
import com.familyspences.procesador_utilidades_api.domain.categories.Category;
import com.familyspences.procesador_utilidades_api.domain.categories.CategoryType;
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
    public void saveFromProducer(CategoryDTO categoryDTO) {
        log.info("💾 Guardando category desde producer...");

        try {
            Category category = new Category();

            category.setId(categoryDTO.getId());
            category.setFamilyId(categoryDTO.getFamilyId());
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            category.setAllocatedBudget(categoryDTO.getAllocatedBudget());

            if (categoryDTO.getCategoryType() != null) {
                category.setCategoryType(CategoryType.valueOf(categoryDTO.getCategoryType()));
            }

            if (categoryDTO.getBudgetPeriod() != null) {
                category.setBudgetPeriod(BudgetPeriod.valueOf(categoryDTO.getBudgetPeriod()));
            }

            repository.save(category);
            log.info("✅ Category guardada: {}", category.getId());

        } catch (Exception e) {
            log.error("❌ Error guardando category: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void updateFromProducer(CategoryDTO updatedCategory) {
        log.info("🔄 Actualizando category desde producer...");
        try {
            UUID categoryId = updatedCategory.getId();
            UUID familyId = updatedCategory.getFamilyId();

            if (categoryId == null || familyId == null) {
                log.warn("⚠️ Missing familyId or id in update event: {}", updatedCategory);
                return;
            }

            Optional<Category> existingOpt = repository.findByFamilyIdAndId(familyId, categoryId);

            if (existingOpt.isEmpty()) {
                log.warn("⚠️ Category not found for update. Family: {}, Category: {}", familyId, categoryId);
                return;
            }

            Category existing = existingOpt.get();

            existing.setName(updatedCategory.getName());
            existing.setDescription(updatedCategory.getDescription());
            existing.setAllocatedBudget(updatedCategory.getAllocatedBudget());

            if (updatedCategory.getCategoryType() != null) {
                existing.setCategoryType(CategoryType.valueOf(updatedCategory.getCategoryType()));
            }
            if (updatedCategory.getBudgetPeriod() != null) {
                existing.setBudgetPeriod(BudgetPeriod.valueOf(updatedCategory.getBudgetPeriod()));
            }

            repository.save(existing);
            log.info("✅ Category updated successfully: {}", categoryId);

        } catch (Exception e) {
            log.error("❌ Error processing Category UPDATE event: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteFromProducer(Map<String, String> data) {
        try {
            String familyStr = data.get("familyId");
            String categoryStr = data.get("categoryId");

            if (familyStr == null || categoryStr == null) {
                log.warn("⚠️ Missing fields in DELETE event: {}", data);
                return;
            }

            UUID familyId = UUID.fromString(familyStr);
            UUID categoryId = UUID.fromString(categoryStr);

            if (repository.existsByFamilyIdAndId(familyId, categoryId)) {
                repository.deleteByFamilyIdAndId(familyId, categoryId);
                log.info("✅ Category deleted successfully: {} for family {}", categoryId, familyId);
            } else {
                log.warn("⚠️ Category with id {} not found for family {}", categoryId, familyId);
            }

        } catch (Exception e) {
            log.error("❌ Error deleting category from producer event: {}", e.getMessage(), e);
        }
    }
}