package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientRequest;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeCalorieEntity;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WritePostRequest {

    private String title; // 글 제목
    private String nickName; // 작성자 이름
    private String recipeName; // 레시피 이름
    private Integer expectingPrice; // 예상 가격
    private Integer pricePerOne; // 1인 당 예상 가격
    private Integer peopleCount; // 파티원 수
    private String dong; // 위치 dto
    private List<PostIngredientRequest> ingredients; // 게시글 재료 목록
    private String content; // 내용


    @Builder
    public WritePostRequest (String title, String content, Integer expectingPrice,
                             Integer pricePerOne, String recipeName,
                             Integer peopleCount, String imgUrl) {
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.recipeName = recipeName;
        this.peopleCount = peopleCount;
    }

    public PostEntity toEntity(MemberEntity member,
                               LocationEntity location,
                               RecipeCalorieEntity recipeCalorie,
                               List<PostIngredientEntity> recipeIngredients) {
        return PostEntity.builder()
                         .title(this.title)
                         .content(this.content)
                         .expectingPrice(this.expectingPrice)
                         .pricePerOne(this.pricePerOne)
                         .member(member)
                         .recipeCalorie(recipeCalorie)
                         .peopleCount(this.peopleCount)
                         .location(location)
                         .recipeIngredients(recipeIngredients)
                         .build();
    }
}
