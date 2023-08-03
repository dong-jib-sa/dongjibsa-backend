package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

//    private List<PostIngredientEntity> recipeIngredients;
    private List<PostIngredientDto> recipeIngredients;
    private String imgUrl;
    private Integer commentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PostDto(Long id, String title, String content, Integer expectingPrice, Integer pricePerOne, UserEntity user, Integer calorie,
                   Integer peopleCount, LocationEntity location, List<PostIngredientDto> recipeIngredients, String imgUrl,
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

    /*
          엔터티 내 엔터티 리스트를 dto 리스트로 바꿔서 dto로 반환...
     */
    public static PostDto toDto(PostEntity post) {
        // entity list 꺼내기
        List<PostIngredientEntity> entities = post.getRecipeIngredients();

        // entity list -> dto list
        List<PostIngredientDto> postIngredientDtoList = entities.stream().map(PostIngredientDto::of).collect(Collectors.toList());

        return PostDto.builder()
                      .id(post.getId())
                      .title(post.getTitle())
                      .content(post.getContent())
                      .expectingPrice(post.getExpectingPrice())
                      .pricePerOne(post.getPricePerOne())
                      .user(post.getUser())
                      .calorie(post.getCalorie())
                      .peopleCount(post.getPeopleCount())
                      .location(post.getLocation())
                      .recipeIngredients(postIngredientDtoList)
                      .imgUrl(post.getImgUrl())
                      .createdAt(post.getCreatedAt())
                      .updatedAt(post.getUpdatedAt())
                      .build();
    }
}