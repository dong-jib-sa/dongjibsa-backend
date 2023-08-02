package com.djs.dongjibsabackend.domain.entity;

import com.djs.dongjibsabackend.domain.dto.recipe_ingredient.RecipeIngredientDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_ingredient")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeIngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore // 에러 해결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient;

    private Integer totalQty; // 구매 수량
    private Integer requiredQty; // 필요 수량
    private Integer sharingAvailableQty; // 나눔 수량

    // == builder 패턴을 사용한 생성 메서드 == //
    @Builder
    public RecipeIngredientEntity(RecipeEntity recipe, IngredientEntity ingredient,
                                  Integer totalQty, Integer requiredQty,
                                  Integer sharingAvailableQty) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
    }

    public static RecipeIngredientEntity addIngredientToRecipe(RecipeEntity recipe,
                                                               IngredientEntity ingredient,
                                                               Integer totalQty,
                                                               Integer requiredQty,
                                                               Integer sharingAvailableQty) {
        return RecipeIngredientEntity.builder()
            .recipe(recipe)
            .ingredient(ingredient)
            .totalQty(totalQty)
            .requiredQty(requiredQty)
            .sharingAvailableQty(sharingAvailableQty)
            .build();
    }

    public static RecipeIngredientDto toDto(RecipeIngredientEntity entity) {
        return RecipeIngredientDto.builder()
                                  .id(entity.getId())
                                  .recipeId(entity.getRecipe().getId())
                                  .ingredientId(entity.getIngredient().getId())
                                  .totalQty(entity.getTotalQty())
                                  .requiredQty(entity.getRequiredQty())
                                  .sharingAvailableQty(entity.getSharingAvailableQty())
                                  .build();
    }
}
