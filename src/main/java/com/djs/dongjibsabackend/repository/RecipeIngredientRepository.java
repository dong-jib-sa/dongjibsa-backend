package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.RecipeIngredientEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Long> {

    List<RecipeIngredientEntity> findAllIngredientsByRecipeId(Long recipeId);

}