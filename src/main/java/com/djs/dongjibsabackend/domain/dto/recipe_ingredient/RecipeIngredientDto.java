package com.djs.dongjibsabackend.domain.dto.recipe_ingredient;

import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeIngredientEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeIngredientDto {

    private Long id;
    private Long recipeId; // Recipe Entity
    private Long ingredientId; // Ingredient Entity
    private Integer totalQty;
    private Integer requiredQty;
    private Integer sharingAvailableQty;

    @Builder
    public RecipeIngredientDto(Long id, Long recipeId, Long ingredientId,
                               Integer totalQty, Integer requiredQty,
                               Integer sharingAvailableQty) {
        this.id = id;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
    }

    /*
    static method turning Entity to DTO
     */
    public static RecipeIngredientDto toDto (RecipeIngredientEntity recipeIngredientEntity) {
        return new RecipeIngredientDto(
            recipeIngredientEntity.getId(),
            recipeIngredientEntity.getRecipe().getId(), //recipe Entity id
            recipeIngredientEntity.getIngredient().getId(), // Ingredient Entity id
            recipeIngredientEntity.getTotalQty(),
            recipeIngredientEntity.getRequiredQty(),
            recipeIngredientEntity.getSharingAvailableQty()
        );
    }

}
