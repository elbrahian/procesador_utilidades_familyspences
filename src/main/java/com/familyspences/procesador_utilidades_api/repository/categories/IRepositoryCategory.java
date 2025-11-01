package com.familyspences.procesador_utilidades_api.repository.categories;

import com.familyspences.procesador_utilidades_api.domain.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRepositoryCategory extends JpaRepository<Category, UUID> {

    Optional<Category> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);

    java.util.List<Category> findByFamilyId(UUID familyId);

    java.util.List<Category> findByFamilyIdIsNull();
}