package com.djs.dongjibsabackend.domain.dto.recipe;

import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntityOld;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private Long id;
    private String recipeTitle;
    private Integer calorie;
    private Integer peopleNum;
    private Long ingredientId;
    private String ingredientName;
    private Integer totalQty;
    private Integer requiredQty;
    private Integer sharingAvailableQty;
    private String imgUrl;

    public static RecipeDto toDto(RecipeEntity recipe) {

        return RecipeDto.builder()
            .id(recipe.getId())
            .recipeTitle(recipe.getRecipeTitle())
            .calorie(recipe.getCalorie())
            .peopleNum(recipe.getPeopleNum())
            .ingredientId(recipe.getIngredientId())
            .ingredientName(recipe.getIngredientName())
            .totalQty(recipe.getTotalQty())
            .requiredQty(recipe.getRequiredQty())
            .sharingAvailableQty(recipe.getSharingAvailableQty())
            .imgUrl(recipe.getImgUrl())
                        .build();

    }
}