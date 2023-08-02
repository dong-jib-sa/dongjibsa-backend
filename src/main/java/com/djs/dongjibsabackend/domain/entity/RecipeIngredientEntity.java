package com.djs.dongjibsabackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private RecipeEntity recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private IngredientEntity ingredient;

    private Integer totalQty;
    private Integer requiredQty;
    private Integer sharingAvailableQty;

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

    public Long getIngredientId() {
        return ingredient.getId();
    }

}
