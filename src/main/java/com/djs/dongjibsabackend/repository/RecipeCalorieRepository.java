package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.RecipeCalorieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCalorieRepository extends JpaRepository<RecipeCalorieEntity, Long> {

    RecipeCalorieEntity findByRecipeName(String recipeName);

}
