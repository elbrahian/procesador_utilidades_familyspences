package com.familyspences.procesador_utilidades_api.domain.categories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "categories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {

    @Id
    // CORRECCIÓN 1: Quitamos @GeneratedValue.
    // Debemos usar el ID que nos manda la API en el mensaje, no crear uno nuevo.
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    // CORRECCIÓN 2: nullable = false
    // Ya no existen globales, siempre debe tener familia.
    @Column(name = "family_id", nullable = false)
    private UUID familyId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryType categoryType;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "allocated_budget", precision = 19, scale = 4, nullable = false)
    private BigDecimal allocatedBudget;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_period", nullable = false)
    private BudgetPeriod budgetPeriod;

    public Category() {
    }

    // Constructor útil para convertir desde DTO
    public Category(UUID id, UUID familyId, String name, CategoryType categoryType, String description, BigDecimal allocatedBudget, BudgetPeriod budgetPeriod) {
        this.id = id;
        this.familyId = familyId;
        this.name = name;
        this.categoryType = categoryType;
        this.description = description;
        this.allocatedBudget = allocatedBudget;
        this.budgetPeriod = budgetPeriod;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFamilyId() {
        return familyId;
    }

    public void setFamilyId(UUID familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAllocatedBudget() {
        return allocatedBudget;
    }

    public void setAllocatedBudget(BigDecimal allocatedBudget) {
        this.allocatedBudget = allocatedBudget;
    }

    public BudgetPeriod getBudgetPeriod() {
        return budgetPeriod;
    }

    public void setBudgetPeriod(BudgetPeriod budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", familyId=" + familyId +
                ", name='" + name + '\'' +
                '}';
    }
}