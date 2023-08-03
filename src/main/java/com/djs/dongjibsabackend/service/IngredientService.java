package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.ingredient.IngredientDto;
import com.djs.dongjibsabackend.domain.entity.IngredientEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    // 재료 이름으로 재료 Dto 구하는 메서드
    public IngredientDto findIngredientCode(String ingredientName) {

        IngredientEntity ingredient = ingredientRepository.findByIngredientName(ingredientName)
            .orElseThrow(() -> new AppException(ErrorCode.INGREDIENT_NOT_AVAILABLE));

        return new IngredientDto(ingredient.getId(), ingredientName);

    }

}
