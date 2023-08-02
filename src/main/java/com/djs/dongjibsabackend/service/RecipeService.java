package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDto;
import com.djs.dongjibsabackend.domain.dto.recipe.WriteRecipeRequest;
import com.djs.dongjibsabackend.domain.dto.recipe_ingredient.RecipeIngredientDto;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeIngredientEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.IngredientRepository;
import com.djs.dongjibsabackend.repository.LocationRepository;
import com.djs.dongjibsabackend.repository.RecipeIngredientRepository;
import com.djs.dongjibsabackend.repository.RecipeRepository;
import com.djs.dongjibsabackend.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final LocationRepository locationRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;

    // 여러 가지 재료 등록 기능 추가하기
    public RecipeDto register(WriteRecipeRequest writeRecipeRequest) {
        // 동 이름을 기준으로 Location 객체 생성
        LocationEntity validateLocation = locationRepository.findLocationByDong(writeRecipeRequest.getDong());

        // 회원가입, 로그인 기능 미구현 -> User 객체 생성 및 DB 저장
        UserEntity validateUser = UserEntity.builder()
                                            .id(1L)
                                            .userName("이현주").build();
        userRepository.save(validateUser);

        // RecipyIngredientEntity 리스트 생성
        List<RecipeIngredientEntity> ingredients = new ArrayList<>();

        // 레시피 엔터티 생성
        RecipeEntity savedRecipe = RecipeEntity.builder()
                                               .title(writeRecipeRequest.getTitle())
                                               .content(writeRecipeRequest.getContent())
                                               .expectingPrice(writeRecipeRequest.getExpectingPrice())
                                               .pricePerOne(writeRecipeRequest.getPricePerOne())
                                               .user(validateUser)
                                               .calorie(writeRecipeRequest.getCalorie())
                                               .peopleCount(writeRecipeRequest.getPeopleCount())
                                               .location(validateLocation)
//                                               .recipeIngredients(ingredients) // 빈 객체
                                               .imgUrl(writeRecipeRequest.getImgUrl())
                                               .build();

        // request 내 입력된 재료
        List<RecipeIngredientDto> dtoList = writeRecipeRequest.getRecipeIngredientDtos();

        // 재료 dto 리스트를 순회하며 레시피 재료 리스트를 채운다.
        for (RecipeIngredientDto dto: dtoList) {
            // 레시피에 등록된 재료가 있는지 탐색..
            IngredientEntity ingredientEntity = ingredientRepository.findById(dto.getIngredientId())
                .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_AVAILABLE, "재료 없어"));
            if (ingredientEntity != null) {
                RecipeIngredientEntity recipeIngredientEntity =
                    RecipeIngredientEntity.addIngredientToRecipe(savedRecipe, // 재료 리스트가 비어 있는 레시피 객체
                                                                 ingredientEntity,
                                                                 dto.getTotalQty(),
                                                                 dto.getRequiredQty(),
                                                                 dto.getSharingAvailableQty());
                ingredients.add(recipeIngredientEntity);
            }
        }

        savedRecipe.updateRecipeIngredients(ingredients);

        recipeRepository.save(savedRecipe);

        RecipeDto savedRecipeDto = RecipeDto.toDto(savedRecipe);

        return savedRecipeDto;
    }

    // 조회
    // List<RecipeIngredientEntity> recipeIngredients = recipeIngredientRepository.findAllIngredientsByRecipeId() -> 조회 로직에 사용할 것
}