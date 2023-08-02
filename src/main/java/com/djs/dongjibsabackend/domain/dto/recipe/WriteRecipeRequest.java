package com.djs.dongjibsabackend.domain.dto.recipe;

import com.djs.dongjibsabackend.domain.dto.ingredient.IngredientDto;
import com.djs.dongjibsabackend.domain.dto.ingredient.IngredientResponse;
import com.djs.dongjibsabackend.domain.dto.location.LocationDto;
import com.djs.dongjibsabackend.domain.dto.recipe_ingredient.RecipeIngredientDto;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WriteRecipeRequest {

    private String title; // 레시피 제목
    private String content; // 내용
    private Integer expectingPrice; // 예상 가격
    private Integer pricePerOne; // 1인 당 예상 가격
    private Integer calorie; // 1인분 칼로리
    private Integer peopleCount; // 파티원 수
    private String dong; // 위치 dto
    private List<RecipeIngredientDto> recipeIngredientDtos; //레시피-재료 dto 리스트 }
    private String imgUrl;


    @Builder
    public WriteRecipeRequest(String title, String content, Integer expectingPrice,
                              Integer pricePerOne, Integer calorie,
                              Integer peopleCount, String imgUrl) {
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.calorie = calorie;
        this.peopleCount = peopleCount;
        this.imgUrl = imgUrl;
    }

    public RecipeEntity toEntity(UserEntity user,
                                 LocationEntity location,
                                 List<RecipeIngredientEntity> recipeIngredients) {
        return RecipeEntity.builder()
                           .title(this.title)
                           .content(this.content)
                           .expectingPrice(this.expectingPrice)
                           .pricePerOne(this.pricePerOne)
                           .user(user)
                           .calorie(this.calorie)
                           .peopleCount(this.peopleCount)
                           .location(location)
                           .recipeIngredients(recipeIngredients)
                           .imgUrl(this.imgUrl)
                           .build();
    }


}
