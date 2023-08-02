package com.djs.dongjibsabackend.domain.entity;

import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeEntity extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public RecipeEntity (Long id, String recipeTitle, Integer calorie, Integer peopleNum, Long ingredientId, String ingredientName,
                         Integer totalQty, Integer requiredQty, Integer sharingAvailableQty, String imgUrl) {
        this.id = id;
        this.recipeTitle = recipeTitle;
        this.calorie = calorie;
        this.peopleNum = peopleNum;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
        this.imgUrl = imgUrl;
    }

    public RecipeDto toDto() {
        return RecipeDto.builder()
            .id(this.id)
            .recipeTitle(this.recipeTitle)
            .calorie(this.calorie)
            .peopleNum(this.peopleNum)
            .ingredientId(this.ingredientId)
            .ingredientName(this.ingredientName)
            .totalQty(this.totalQty)
            .requiredQty(this.requiredQty)
            .sharingAvailableQty(this.sharingAvailableQty)
            .imgUrl(this.imgUrl).build();
    }
}
