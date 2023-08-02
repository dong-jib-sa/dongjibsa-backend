package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.ingredient.AddIngredientRequest;
import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDto;
import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDtoOld;
import com.djs.dongjibsabackend.domain.dto.recipe.RecipeResponse;
import com.djs.dongjibsabackend.domain.dto.recipe.WriteRecipeRequest;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    // service 주입
    private final RecipeService recipeService;

    // 1. 게시글 작성
//    @PostMapping("/new")
//    public Response registerPost(@RequestBody WriteRecipeRequest writeRecipeRequest,
//                                 @RequestBody AddIngredientRequest addIngredientRequest,
//                                 UserEntity user) {
//
//        RecipeDtoOld recipeDtoOld = recipeService.register(writeRecipeRequest, user);
//
//        return Response.success(recipeDtoOld);
//    }

    // 2. 게시글 조회
    @GetMapping
    public Response<Page<RecipeResponse>> getAllRecipes(
        @PageableDefault(size = 20, sort = "id", direction = Direction.ASC) Pageable pageable
//        ,@RequestParam(value = "sort", required = false) String sort
                                                       ) {

        Page<RecipeDto> recipeDtos = recipeService.findAllRecipes(pageable);
        return Response.success(RecipeResponse.of(recipeDtos));
    }
}