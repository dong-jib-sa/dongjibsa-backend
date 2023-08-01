package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDto;
import com.djs.dongjibsabackend.domain.dto.recipe.WriteRecipeRequest;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.LocationRepository;
import com.djs.dongjibsabackend.repository.RecipeRepository;
import com.djs.dongjibsabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final LocationRepository locationRepository;

    // 여러 가지 재료 등록 기능 추가하기
    public RecipeDto register(WriteRecipeRequest writeRecipeRequest, UserEntity user) {
        Long userId = user.getId();
        Long locationId = writeRecipeRequest.getLocation().getId();

        LocationEntity validateLocation = locationRepository.findById(locationId)
            .orElseThrow(() -> new AppException(ErrorCode.LOCATION_NOT_FOUND, "해당 지역이 존재하지 않습니다."));

        UserEntity validateUser = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "사용자가 존재하지 않습니다."));

        RecipeEntity savedRecipe = recipeRepository.save(writeRecipeRequest.toEntity(validateUser, validateLocation));

        RecipeDto savedRecipeDto = RecipeDto.toDto(savedRecipe);

        return savedRecipeDto;
    }
}