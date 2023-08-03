package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.WritePostRequest;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.IngredientRepository;
import com.djs.dongjibsabackend.repository.LocationRepository;
import com.djs.dongjibsabackend.repository.PostIngredientRepository;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LocationRepository locationRepository;
    private final PostIngredientRepository postIngredientRepository;
    private final IngredientRepository ingredientRepository;

    // 여러 가지 재료 등록 기능 추가하기
    public PostDto register(WritePostRequest writePostRequest) {

        // 동 이름을 기준으로 Location 객체 생성
        LocationEntity validateLocation = locationRepository.findLocationByDong(writePostRequest.getDong());

        // 사용자가 입력한 이름으로 User 객체 생성
        UserEntity validateUser = userRepository.findByUserName(writePostRequest.getUserName());

        // RecipyIngredientEntity 리스트 생성
        List<PostIngredientEntity> ingredients = new ArrayList<>();

        // 레시피 엔터티 생성
        PostEntity savedPost = PostEntity.builder()
                                           .title(writePostRequest.getTitle())
                                           .content(writePostRequest.getContent())
                                           .expectingPrice(writePostRequest.getExpectingPrice())
                                           .pricePerOne(writePostRequest.getPricePerOne())
                                           .user(validateUser)
//                                           .calorie(writePostRequest.getCalorie())
                                           .peopleCount(writePostRequest.getPeopleCount())
                                           .location(validateLocation)
//                                               .recipeIngredients(ingredients) // 빈 객체
                                           .imgUrl(writePostRequest.getImgUrl())
                                           .build();

        // request 내 입력된 재료
        List<PostIngredientDto> dtoList = writePostRequest.getPostIngredientDtos();

        // 재료 dto 리스트를 순회하며 레시피 재료 리스트를 채운다.
        for (PostIngredientDto dto: dtoList) {
            // 재료명으로 재료 코드 조회
            IngredientEntity ingredientEntity = ingredientRepository.findByIngredientName(dto.getIngredientName())
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_AVAILABLE, "해당 재료는 입력할 수 없습니다."));

            if (ingredientEntity != null) {
                PostIngredientEntity postIngredientEntity =
                    PostIngredientEntity.addIngredientToPost (savedPost, // 재료 리스트가 비어 있는 레시피 객체
                                                               ingredientEntity,
                                                               dto.getTotalQty(),
                                                               dto.getRequiredQty(),
                                                               dto.getSharingAvailableQty());
                ingredients.add(postIngredientEntity);
            }
        }

        savedPost.updatePostIngredients(ingredients);

        postRepository.save(savedPost);

        PostDto savedPostDto = PostDto.toDto(savedPost);

        return savedPostDto;
    }

    // 조회
    // List<RecipeIngredientEntity> recipeIngredients = recipeIngredientRepository.findAllIngredientsByRecipeId() -> 조회 로직에 사용할 것
}