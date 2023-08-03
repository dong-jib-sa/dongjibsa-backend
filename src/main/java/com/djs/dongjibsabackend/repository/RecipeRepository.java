package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

}
