package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.service.PostImageService;
import com.djs.dongjibsabackend.service.PostService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestController
@RequestMapping("api/v1/posts/images")
@RequiredArgsConstructor
public class PostImageController {

    private final PostService postService;
    private final PostImageService postImageService;

    @PostMapping(path = "/{postId}/uploadImage", consumes = {"multipart/form-data"}, headers = ("content-type=multipart/*"))
    @ResponseBody
    public Response<String> uploadRecipeImage (
        @RequestParam("image") MultipartFile multipartFile,
        @PathVariable Long postId) throws IOException, MissingServletRequestPartException {

        String fileUrl = postImageService.uploadAndSaveToDB(multipartFile, postId);

        return Response.success(fileUrl);
    }

}
