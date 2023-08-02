package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {

}
