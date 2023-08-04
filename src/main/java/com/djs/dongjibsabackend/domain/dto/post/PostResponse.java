package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientResponse;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
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

    private String title;
    private String content;
    private Integer expectingPrice;
    private Integer pricePerOne;
    private String userName; // 작성자 이름
    private double calorie;
    private Integer peopleCount;
    private List<PostIngredientResponse> recipeIngredients; //List<PostIngredientDto>로 타입 변경
    // private LocationEntity locationName; // LocationDto로 타입 변경
    // private String locationName;
    private String imgUrl;
    private Integer commentsCount;




    // - of (레시피 재료 dto를 받아서 response 객체로 만드는 메소드 작성하기)
    public static PostResponse of(PostDto postDto) {

        List<PostIngredientDto> ingredientDto = postDto.getRecipeIngredients();

        List<PostIngredientResponse> ingredientResponse = ingredientDto.stream()
                                                                        .map(PostIngredientResponse::of)
                                                                        .collect(Collectors.toList());

        return PostResponse.builder()
                           .title(postDto.getTitle())
                           .content(postDto.getContent())
                           .expectingPrice(postDto.getExpectingPrice())
                           .pricePerOne(postDto.getExpectingPrice())
                           .userName(postDto.getUser().getUserName())
                           .calorie(postDto.getCalorie())
                           .peopleCount(postDto.getPeopleCount())
                           .recipeIngredients(ingredientResponse)
                           .imgUrl(postDto.getImgUrl())
                           .build();
    }

    public static Page<PostResponse> of(Page<PostDto> posts) {

        return posts.map(post -> PostResponse.builder()
                                             .title(post.getTitle())
                                             .content(post.getContent())
                                             .expectingPrice(post.getExpectingPrice())
                                             .pricePerOne(post.getPricePerOne())
                                             .userName(post.getUser().getUserName())
                                             .calorie(post.getCalorie())
                                             .peopleCount(post.getPeopleCount())
                                             .recipeIngredients(PostIngredientResponse.of(post.getRecipeIngredients()))
                                             .imgUrl(post.getImgUrl())
                                             .build());
    }

    // 리스트로 반환하는 메서드
    public static List<PostResponse> of(List<PostDto> posts) {

        return posts.stream().map(post -> PostResponse.builder()
                                             .title(post.getTitle())
                                             .content(post.getContent())
                                             .expectingPrice(post.getExpectingPrice())
                                             .pricePerOne(post.getPricePerOne())
                                             .userName(post.getUser().getUserName())
                                             .calorie(post.getCalorie())
                                             .peopleCount(post.getPeopleCount())
                                             .recipeIngredients(PostIngredientResponse.of(post.getRecipeIngredients()))
                                             .imgUrl(post.getImgUrl())
                                             .build()).collect(Collectors.toList());
    }

}