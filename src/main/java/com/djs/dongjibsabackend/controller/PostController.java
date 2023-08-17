package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.post.PostDetailResponse;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.PostResponse;
import com.djs.dongjibsabackend.domain.dto.post.RegisterPostRequest;
import com.djs.dongjibsabackend.domain.dto.post.WritePostRequest;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.service.PostImageService;
import com.djs.dongjibsabackend.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Tag(name = "레시피 API", description = "레시피 등록, 조회를 위한 API")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    // 1. 게시글 작성
    @PostMapping(path = "/new",
        consumes = {"multipart/form-data"}
        ,headers = ("content-type=multipart/*")
    )
    public Response registerPost(@ModelAttribute RegisterPostRequest req) throws IOException, MissingServletRequestPartException {

        PostDto postDto = postService.register(req);

        log.info("등록된 게시글 ID:" + postDto.getId());

        return Response.success(postDto);
    }

//    @PostMapping(path = "/new",
//        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
//        headers = ("content-type=multipart/*"))
//    public Response registerPost(@RequestPart WritePostRequest writePostRequest,
//                                 @RequestParam("image") MultipartFile multipartFile) throws IOException, MissingServletRequestPartException {
//
//        PostDto postDto = postService.register(writePostRequest);
//        PostDto imageSavedPostDto = postImageService.uploadAndSaveToDB(multipartFile, postDto.getId());
//
//        return Response.success(imageSavedPostDto);
//    }

    // 지역별 게시글 전체 조회 (To - Be)
    @GetMapping("/{keywords}")
    public List<PostResponse> getAllRecipes(
        @PathVariable String keywords) {
        String dongName = keywords;

        List<PostDto> postDtoList = postService.searchByLocation(dongName);

        List<PostResponse> postResponse = PostResponse.of(postDtoList);

        return postResponse;
    }

    @GetMapping("/{keywords}/{postId}")
    public PostDetailResponse getRecipeDetail(@PathVariable Long postId) {
        PostDto postDto = postService.getPostDetail(postId);
        PostDetailResponse postResponse = PostDetailResponse.of(postDto);
        return postResponse;
    }
}