package com.djs.dongjibsabackend.domain.dto.recipe;

import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import java.time.LocalDateTime;
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
    private String title;
    private String content;
    private Integer expectingPrice;
    private Integer pricePerOne;
    private UserEntity user;
    private Integer calorie;
    private Integer peopleCount;
    private LocationEntity location;
    private IngredientEntity ingredient;
    private Integer totalQty;
    private Integer requiredQty;
    private Integer sharingAvailableQty;
    private String imgUrl;
    private Integer commentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RecipeDto(Long id, String title, String content, Integer expectingPrice, Integer pricePerOne, Integer calorie,
                     Integer peopleCount,
                     Integer totalQty, Integer requiredQty, Integer sharingAvailableQty, String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.calorie = calorie;
        this.peopleCount = peopleCount;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
        this.imgUrl = imgUrl;
    }

    public RecipeEntity toEntity(UserEntity user, LocationEntity location,
                                 IngredientEntity ingredient) {
        return RecipeEntity.builder()
                           .title(this.title)
                           .content(this.content)
                           .expectingPrice(this.expectingPrice)
                           .pricePerOne(this.pricePerOne)
                           .user(user)
                           .calorie(this.calorie)
                           .peopleCount(this.peopleCount)
                           .location(location)
                           .ingredient(ingredient)
                           .totalQty(this.totalQty)
                           .requiredQty(this.requiredQty)
                           .sharingAvailableQty(this.sharingAvailableQty)
                           .imgUrl(this.imgUrl)
                           .commentsCount(this.commentsCount)
                           .build();
    }

    public static RecipeDto toDto(RecipeEntity recipe) {
        return RecipeDto.builder()
                        .id(recipe.getId())
                        .title(recipe.getTitle())
                        .content(recipe.getContent())
                        .expectingPrice(recipe.getExpectingPrice())
                        .pricePerOne(recipe.getPricePerOne())
                        .user(recipe.getUser())
                        .calorie(recipe.getCalorie())
                        .peopleCount(recipe.getPeopleCount())
                        .location(recipe.getLocation())
                        .ingredient(recipe.getIngredient())
                        .totalQty(recipe.getTotalQty())
                        .requiredQty(recipe.getRequiredQty())
                        .sharingAvailableQty(recipe.getSharingAvailableQty())
                        .imgUrl(recipe.getImgUrl())
                        .commentsCount(recipe.getCommentsCount())
                        .createdAt(recipe.getCreatedAt())
                        .updatedAt(recipe.getUpdatedAt())
                        .build();
    }

}
