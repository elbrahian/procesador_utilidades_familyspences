package com.familyspences.procesador_utilidades_api.repositories.goal;

import com.familyspencesapi.domain.goals.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRepositoryGoal extends JpaRepository<Goal, UUID> {
}