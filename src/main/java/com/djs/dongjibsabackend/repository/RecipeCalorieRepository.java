package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.RecipeCalorieEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCalorieRepository extends JpaRepository<RecipeCalorieEntity, Long> {

     Optional<RecipeCalorieEntity> findByRecipeName(String recipeName);
//    RecipeCalorieEntity findByRecipeName(String recipeName);

}
