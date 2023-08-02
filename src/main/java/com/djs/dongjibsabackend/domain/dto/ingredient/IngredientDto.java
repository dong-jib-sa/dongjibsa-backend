package com.djs.dongjibsabackend.domain.dto.ingredient;

import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDto;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientDto {

    private Long id;
    private String name;

    @Builder
    public IngredientDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
