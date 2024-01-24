package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.post.EditPostRequest;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.RegisterPostRequest;
import com.djs.dongjibsabackend.domain.dto.post.RegisterPostResponse;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping(path = "/new", consumes = {"multipart/form-data"}, headers = ("content-type=multipart/*"))
    public Response<RegisterPostResponse> registerPost(@ModelAttribute RegisterPostRequest req) throws IOException, MissingServletRequestPartException {
        PostDto postDto = postService.register(req);
        log.info("등록된 게시글 ID:" + postDto.getId());
        boolean includeCalorie = false;
        if (postDto.getRecipeCalorie().getCalorie() > 0.0) {
            includeCalorie = true;
        }
        RegisterPostResponse response = RegisterPostResponse.builder()
                                                            .postDto(postDto)
                                                            .includeCalorie(includeCalorie)
                                                            .build();
        return Response.success(response);
    }


    // 게시글 리스트 조회
    @GetMapping("")
    public Response<List<PostDto>> getAllRecipeList() {
        List<PostDto> postDtoList = postService.getRecipeList();
        return Response.success(postDtoList);
    }

    /* 게시글 1건 조회  */
    @GetMapping("/{postId}")
    public Response<RegisterPostResponse> getRecipeDetail(@PathVariable Long postId) {
        PostDto postDto = postService.getPostDetail(postId);
        log.info("조회된 게시글 ID:" + postDto.getId());
        boolean includeCalorie = false;
        if (postDto.getRecipeCalorie().getCalorie() > 0.0) {
            includeCalorie = true;
        }
        RegisterPostResponse postResponse = RegisterPostResponse.builder()
                                                                .postDto(postDto)
                                                                .includeCalorie(includeCalorie)
                                                                .build();
        return Response.success(postResponse);
    }

    /* 게시글 수정 */
    @PutMapping(path ="/{postId}", consumes = {"multipart/form-data"}, headers = ("content-type=multipart/*"))
    public Response<RegisterPostResponse> editPost(@PathVariable Long postId, @ModelAttribute EditPostRequest editPostRequest) throws IOException, MissingServletRequestPartException{
        PostDto postDto = postService.editPost(postId, editPostRequest);
        log.info("{}번 게시글 수정이 완료되었습니다", postId);
        boolean includeCalorie = false;
        if (postDto.getRecipeCalorie().getCalorie() > 0.0) {
            includeCalorie = true;
        }
        RegisterPostResponse updatedPostResponse = RegisterPostResponse.builder()
                                                                       .postDto(postDto)
                                                                       .includeCalorie(includeCalorie)
                                                                       .build();
        return Response.success(updatedPostResponse);
    }

    /* 게시글 삭제 */
    @DeleteMapping("/{postId}/{memberId}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long postId, @PathVariable Long memberId) {
        if (memberId == null) {
            throw new AppException(ErrorCode.MISSING_PARAMETER, "memberId값이 없습니다.");
        }
        String result = postService.deletePost(postId, memberId);
        return ResponseEntity.ok(Response.success(result));
    }
}