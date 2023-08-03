package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.recipe.RecipeDto;
import com.djs.dongjibsabackend.domain.dto.recipe.RecipeResponse;
import com.djs.dongjibsabackend.domain.dto.recipe.WriteRecipeRequest;
import com.djs.dongjibsabackend.domain.entity.RecipeEntity;
import com.djs.dongjibsabackend.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "레시피 API", description = "레시피 등록, 조회를 위한 API")
@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    // service 주입
    private final RecipeService recipeService;

    // 1. 게시글 작성
    @PostMapping("/new")
    public Response registerPost(@RequestBody WriteRecipeRequest writeRecipeRequest) {

        RecipeDto recipeDto = recipeService.register(writeRecipeRequest);

        return Response.success(recipeDto);
    }

    // 2. 지역별 게시글 전체 조회
    @GetMapping("/{DongName}")
    public Response<Page<RecipeResponse>> getAllRecipes(
        @PageableDefault(size = 20, sort = "id", direction = Direction.ASC) Pageable pageable,
        @PathVariable(name = "DongName") String dongName) {

        Page<RecipeDto> recipeDtoPage = recipeService.searchByLocation(pageable, dongName);

        return Response.success(recipeDtoPage);
    }
}