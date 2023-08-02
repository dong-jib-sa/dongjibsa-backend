package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.ingredient.AddIngredientRequest;
import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDto;
import com.djs.dongjibsabackend.domain.dto.recipe.WriteRecipeRequest;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    // service 주입
    private final RecipeService recipeService;

    // 1. 게시글 작성
    @PostMapping("/new")
    public Response registerPost(@RequestBody WriteRecipeRequest writeRecipeRequest,
                                 @RequestBody AddIngredientRequest addIngredientRequest,
                                 UserEntity user) {

        RecipeDto recipeDto = recipeService.register(writeRecipeRequest, user);

        return Response.success(recipeDto);
    }
}