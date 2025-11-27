package com.familyspences.procesador_utilidades_api.repository.categories;

import com.familyspences.procesador_utilidades_api.domain.categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRepositoryCategory extends JpaRepository<Category, UUID> {

    Optional<Category> findByFamilyIdAndId(UUID familyId, UUID id);
    boolean existsByFamilyIdAndId(UUID familyId, UUID id);
    void deleteByFamilyIdAndId(UUID familyId, UUID id);

    List<Category> findByFamilyId(UUID familyId);

}