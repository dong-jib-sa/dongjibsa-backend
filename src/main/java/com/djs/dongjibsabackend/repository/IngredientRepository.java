package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {

    Optional<IngredientEntity> findByIngredientName(String name);

}
