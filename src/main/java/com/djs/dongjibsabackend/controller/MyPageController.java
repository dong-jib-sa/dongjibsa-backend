package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.myPage.MyIndicatorResponse;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.PostResponse;
import com.djs.dongjibsabackend.domain.dto.post.RegisterPostResponse;
import com.djs.dongjibsabackend.service.MyPageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "마이 페이지 API", description = "주요 지표, 나의 작성글 리스트 조회를 위한 API")
@RestController
@RequestMapping("/api/v1/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 마이페이지 평균 칼로리, 총 나눔 건수 조회
     @GetMapping("/{memberId}/indicator")
    public Response getMyIndicator(@PathVariable Long memberId) {
        MyIndicatorResponse myIndicatorResponse = myPageService.calculate(memberId);
        return Response.success(myIndicatorResponse);
    }

    // 마이페이지 - 작성자의 모든 나눔 글 조회
    @GetMapping("/{memberId}/posts")
    public List<RegisterPostResponse> getMyPostList(@PathVariable Long memberId) {
        List<PostDto> postDtoList = myPageService.getMyPostList(memberId);
        // List<PostResponse> postResponseList = PostResponse.of(postDtoList);
        List<RegisterPostResponse> responseList = RegisterPostResponse.of(postDtoList);
        return responseList;
    }
}