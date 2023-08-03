package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.PostResponse;
import com.djs.dongjibsabackend.domain.dto.post.WritePostRequest;
import com.djs.dongjibsabackend.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    // service 주입
    private final PostService postService;

    // 1. 게시글 작성
    @PostMapping("/new")
    public Response registerPost(@RequestBody WritePostRequest writePostRequest) {

        PostDto postDto = postService.register(writePostRequest);

        return Response.success(postDto);
    }

    // 2. 지역별 게시글 전체 조회
    @GetMapping("/{keywords}")
    public Response<Page<PostResponse>> getAllRecipes(
        @PageableDefault(size = 20, sort = "id", direction = Direction.ASC) Pageable pageable,
        @PathVariable String keywords) {
        String dongName = keywords;

        Page<PostDto> recipeDtoPage = postService.searchByLocation(pageable, dongName);

        return Response.success(recipeDtoPage);
    }
}