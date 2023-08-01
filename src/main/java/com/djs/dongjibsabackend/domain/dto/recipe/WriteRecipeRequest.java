package com.djs.dongjibsabackend.domain.dto.recipe;

import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WriteRecipeRequest {

//    private Long id;
    private String title;
    private String content;
    private Integer expectingPrice;
    private Integer pricePerOne;
//    private UserEntity user;
    private Integer calorie;
    private Integer peopleCount;
    private LocationEntity location;
    private IngredientEntity ingredient;
    private Integer totalQty;
    private Integer requiredQty;
    private Integer sharingAvailableQty;
    private String imgUrl;

    @Builder
    public WriteRecipeRequest(String title, String content, Integer expectingPrice, Integer pricePerOne, Integer calorie,
                              Integer peopleCount,
                              LocationEntity location, IngredientEntity ingredient, Integer totalQty, Integer requiredQty,
                              Integer sharingAvailableQty, String imgUrl) {
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.calorie = calorie;
        this.peopleCount = peopleCount;
        this.location = location;
        this.ingredient = ingredient;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
        this.imgUrl = imgUrl;
    }

    public RecipeEntity toEntity(UserEntity user, LocationEntity location) {
        return RecipeEntity.builder()
            .title(this.title)
            .content(this.content)
            .expectingPrice(this.expectingPrice)
            .pricePerOne(this.pricePerOne)
            .user(user)
            .calorie(this.calorie)
            .peopleCount(this.peopleCount)
            .location(location)
            .ingredient(this.ingredient)
            .totalQty(this.totalQty)
            .requiredQty(this.requiredQty)
            .sharingAvailableQty(this.sharingAvailableQty)
            .imgUrl(this.imgUrl)
                           .build();
    }
}
