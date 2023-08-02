package com.djs.dongjibsabackend.domain.dto.recipe;

import com.djs.dongjibsabackend.domain.dto.recipe_ingredient.RecipeIngredientDto;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private List<RecipeIngredientEntity> recipeIngredients;
    private String imgUrl;
    private Integer commentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RecipeDto(Long id, String title, String content, Integer expectingPrice, Integer pricePerOne, UserEntity user, Integer calorie,
                     Integer peopleCount, LocationEntity location, List<RecipeIngredientEntity> recipeIngredients, String imgUrl,
                     Integer commentsCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.user = user;
        this.calorie = calorie;
        this.peopleCount = peopleCount;
        this.location = location;
        this.recipeIngredients = recipeIngredients;
        this.imgUrl = imgUrl;
        this.commentsCount = commentsCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
                        .recipeIngredients(recipe.getRecipeIngredients())
                        .imgUrl(recipe.getImgUrl())
                        .createdAt(recipe.getCreatedAt())
                        .updatedAt(recipe.getUpdatedAt())
                        .build();

    }

    public static List<RecipeIngredientDto> toDtoList(List<RecipeIngredientEntity> entityList) {
        List<RecipeIngredientDto> dtoList = new ArrayList<>();
        for (RecipeIngredientEntity entity: entityList) {
            RecipeIngredientDto dto = RecipeIngredientDto.toDto(entity);
            dtoList.add(dto);
            }
        return dtoList;

    }
}