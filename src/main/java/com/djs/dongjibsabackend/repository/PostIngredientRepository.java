package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostIngredientRepository extends JpaRepository<PostIngredientEntity, Long> {

    List<PostIngredientEntity> findAllIngredientsByPostId (Long postId);

}