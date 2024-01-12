package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientResponse;
import com.djs.dongjibsabackend.domain.dto.recipe_calorie.RecipeCalorieDto;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Integer expectingPrice;
    private Integer pricePerOne;
    private String nickName; // 작성자 이름
    private double calorie;
    private Integer peopleCount;
    private List<PostIngredientResponse> recipeIngredients; //List<PostIngredientDto>로 타입 변경
    private Integer commentsCount;
    private String imgUrl;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    // - of (레시피 재료 dto를 받아서 response 객체로 만드는 메소드 작성하기)
    public static PostResponse of(PostDto postDto) {

        List<PostIngredientDto> ingredientDto = postDto.getRecipeIngredients();

        List<PostIngredientResponse> ingredientResponse = ingredientDto.stream()
                                                                        .map(PostIngredientResponse::of)
                                                                        .collect(Collectors.toList());
        double calorie = postDto.getRecipeCalorie().getCalorie();

        return PostResponse.builder()
                           .id(postDto.getId())
                           .title(postDto.getTitle())
                           .content(postDto.getContent())
                           .expectingPrice(postDto.getExpectingPrice())
                           .pricePerOne(postDto.getExpectingPrice())
                           .nickName(postDto.getMember().getNickName())
                           .calorie(calorie)
                           .peopleCount(postDto.getPeopleCount())
                           .recipeIngredients(ingredientResponse)
                           .imgUrl(postDto.getImgUrl())
                           .createdAt(postDto.getCreatedAt())
                           .updatedAt(postDto.getUpdatedAt())
                           .build();
    }

    // 리스트로 반환하는 메서드
    public static List<PostResponse> of(List<PostDto> posts) {

        return posts.stream().map(post -> PostResponse.builder()
                                             .id(post.getId())
                                             .title(post.getTitle())
                                             .content(post.getContent())
                                             .expectingPrice(post.getExpectingPrice())
                                             .pricePerOne(post.getPricePerOne())
                                             .nickName(post.getMember().getNickName())
                                             .calorie(post.getRecipeCalorie().getCalorie())
                                             .peopleCount(post.getPeopleCount())
                                             .recipeIngredients(PostIngredientResponse.of(post.getRecipeIngredients()))
                                             .imgUrl(post.getImgUrl())
                                             .createdAt(post.getCreatedAt())
                                             .updatedAt(post.getUpdatedAt())
                                                      .build()).collect(Collectors.toList());
    }

    public static Page<PostResponse> of(Page<PostDto> posts) {

        return posts.map(post -> PostResponse.builder()
                                             .title(post.getTitle())
                                             .content(post.getContent())
                                             .expectingPrice(post.getExpectingPrice())
                                             .pricePerOne(post.getPricePerOne())
                                             .nickName(post.getMember().getNickName())
                                             .calorie(post.getRecipeCalorie().getCalorie())
                                             .peopleCount(post.getPeopleCount())
                                             .recipeIngredients(PostIngredientResponse.of(post.getRecipeIngredients()))
                                             .imgUrl(post.getImgUrl())
                                             .build());
    }
}