package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.image.ImageDto;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.dto.recipe_calorie.RecipeCalorieDto;
import com.djs.dongjibsabackend.domain.entity.ImageEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Integer expectingPrice;
    private Integer pricePerOne;
    private MemberEntity member;
    private RecipeCalorieDto recipeCalorie;
    private Integer peopleCount;
    // private LocationEntity location;
    private List<PostIngredientDto> recipeIngredients;
//    private List<ImageEntity> imgUrls;
    private List<String> imgUrls;
    private Integer commentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PostDto (Long id, String title, String content, Integer expectingPrice, Integer pricePerOne, MemberEntity member,
                    RecipeCalorieDto recipeCalorie, Integer peopleCount,
                    // LocationEntity location,
                    List<PostIngredientDto> recipeIngredients, List<String> imgUrls,
                    Integer commentsCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.member = member;
        this.recipeCalorie = recipeCalorie;
        this.peopleCount = peopleCount;
        // this.location = location;
        this.recipeIngredients = recipeIngredients;
        this.imgUrls = imgUrls;
        this.commentsCount = commentsCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /*
          엔터티 내 엔터티 리스트를 dto 리스트로 바꿔서 dto로 반환...
     */
    public static PostDto toDto (PostEntity post) {
        // entity list 꺼내기
        List<PostIngredientEntity> entities = post.getRecipeIngredients();

        // entity list -> dto list
        List<PostIngredientDto> postIngredientDtoList = entities.stream().map(PostIngredientDto::of).collect(Collectors.toList());
        RecipeCalorieDto recipeCalorieDto = RecipeCalorieDto.of((post.getRecipeCalorie()));

        List<ImageEntity> images = post.getImgUrls();
        if (CollectionUtils.isEmpty(images)) { // images에 데이터가 없다면, String으로 만든다.
            return PostDto.builder()
                          .id(post.getId())
                          .title(post.getTitle())
                          .content(post.getContent())
                          .expectingPrice(post.getExpectingPrice())
                          .pricePerOne(post.getPricePerOne())
                          .member(post.getMember())
                          .recipeCalorie(recipeCalorieDto)
                          .peopleCount(post.getPeopleCount())
                          // .location(post.getLocation())
                          .recipeIngredients(postIngredientDtoList)
                          // .imgUrls(urls)
                          .createdAt(post.getCreatedAt())
                          .updatedAt(post.getUpdatedAt())
                          .build();
        } else {
            List<String> urls = ImageDto.of(images);
            return PostDto.builder()
                          .id(post.getId())
                          .title(post.getTitle())
                          .content(post.getContent())
                          .expectingPrice(post.getExpectingPrice())
                          .pricePerOne(post.getPricePerOne())
                          .member(post.getMember())
                          .recipeCalorie(recipeCalorieDto)
                          .peopleCount(post.getPeopleCount())
                          // .location(post.getLocation())
                          .recipeIngredients(postIngredientDtoList)
                          .imgUrls(urls)
                          .createdAt(post.getCreatedAt())
                          .updatedAt(post.getUpdatedAt())
                          .build();
        }
    }

    public static List<PostDto> toDtoList (List<PostEntity> entityList) {
        List<PostDto> dtoList = new ArrayList<>();

        for (PostEntity entity: entityList) {
            PostDto dto = PostDto.toDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }
}