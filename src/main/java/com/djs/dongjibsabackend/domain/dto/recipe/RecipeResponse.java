package com.djs.dongjibsabackend.domain.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponse {

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

    public static Page<RecipeResponse> of(Page<RecipeDto> recipeDtos) {
        return recipeDtos.map(recipe -> RecipeResponse.builder()
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
                                                      .build());
    }

}
