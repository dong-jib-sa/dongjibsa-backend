package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Integer expectingPrice;
    private Integer pricePerOne;
    private UserEntity user;
    private Integer calorie;
    private Integer peopleCount;
    private LocationEntity location;

    private List<PostIngredientEntity> recipeIngredients;
    private String imgUrl;
    private Integer commentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDto(Long id, String title, String content, Integer expectingPrice, Integer pricePerOne, UserEntity user, Integer calorie,
                   Integer peopleCount, LocationEntity location, List<PostIngredientEntity> recipeIngredients, String imgUrl,
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

    public static PostDto toDto(PostEntity recipe) {
        return PostDto.builder()
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

    public static List<PostIngredientDto> toDtoList(List<PostIngredientEntity> entityList) {
        List<PostIngredientDto> dtoList = new ArrayList<>();
        for (PostIngredientEntity entity: entityList) {
            PostIngredientDto dto = PostIngredientDto.toDto(entity);
            dtoList.add(dto);
            }
        return dtoList;
    }
}