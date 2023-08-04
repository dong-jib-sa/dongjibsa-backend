package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.myPage.MyIndicatorResponse;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.dto.post.PostResponse;
import com.djs.dongjibsabackend.service.MyPageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 마이페이지 평균 칼로리, 총 나눔 건수 조회
    @GetMapping("/indicator")
    // @GetMapping("/{userId}/indicator")
    public Response getMyIndicator() {

        MyIndicatorResponse myIndicatorResponse = myPageService.calculate();
        // MyIndicatorResponse myIndicatorResponse = myPageService.calculate(userId);

        return Response.success(myIndicatorResponse);
    }

    // 마이페이지 - 작성자의 모든 나눔 글 조회
    @GetMapping("/posts")
    // @GetMapping("/{userId}/posts")
    public List<PostResponse> getMyPostList() {

        List<PostDto> postDtoList = myPageService.getMyPostList();
        // List<PostDto> postDtoList = myPageService.getMyPostList(userId);

        List<PostResponse> postResponseList = PostResponse.of(postDtoList);

        return postResponseList;
    }
}