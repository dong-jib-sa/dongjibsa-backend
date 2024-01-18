package com.djs.dongjibsabackend.domain.dto.recipe_calorie;

import com.djs.dongjibsabackend.domain.entity.RecipeCalorieEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeCalorieDto {

    private Long id;
    private String recipeName;
    private double calorie;

    @Builder
    public RecipeCalorieDto(Long id, String recipeName, double calorie) {
        this.id = id;
        this.recipeName = recipeName;
        this.calorie = calorie;
    }

    // entity -> dto
    public static RecipeCalorieDto of (RecipeCalorieEntity recipeCalorie) {
        return RecipeCalorieDto.builder()
                               .id(recipeCalorie.getId())
                               .recipeName(recipeCalorie.getRecipeName())
                               .calorie(recipeCalorie.getCalorie())
                               .build();
    }

    public static RecipeCalorieEntity toEntity (RecipeCalorieDto dto) {
        return RecipeCalorieEntity.builder()
                                  .recipeName(dto.recipeName)
                                  .calorie(dto.calorie)
                                  .build();
    }
}
