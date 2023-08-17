package com.djs.dongjibsabackend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.PostRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostImageService {

    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String s3DirName = "/postImg";

    // 게시글 검증
    public PostEntity validatePost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        return post;
    }

    public String uploadAndSaveToDB(MultipartFile multipartFile, Long postId) throws MissingServletRequestPartException {

        // 파일 검증
        if (multipartFile.isEmpty()) {
            throw new MissingServletRequestPartException("이미지가 존재하지 않습니다!");
        }

        // 파일 정보로 메타데이터 생성
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        // 파일 제목
        String uploadFileName = multipartFile.getOriginalFilename();
        log.debug("uploadFileName", uploadFileName);

        int index;
        try {
            index = uploadFileName.lastIndexOf(".");
        } catch (StringIndexOutOfBoundsException e) {
            throw new AppException(ErrorCode.WRONG_FILE_FORMAT, "잘못된 파일 형식입니다.");
        }

        // 확장자
        String extension = uploadFileName.substring(index + 1);
        log.debug("extension:", extension);

        // 파일명 생성
        String awsS3FileName = UUID.randomUUID() + "." + extension;
        log.debug("awsS3FileName:", awsS3FileName);

        String key = "postImg/" + awsS3FileName;
        log.debug("key:", key);

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(
                new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "통신에 실패했습니다.");
        }

//        String imageUrl = amazonS3Client.getResourceUrl(s3DirName, key);
//        String fileUrl = "https://" + bucket + key;
        String imageUrl = amazonS3Client.getUrl(bucket, key).toString();
        log.debug("imageUrl: ", imageUrl);

//        PostEntity post = validatePost(postId);
//
//        post.updatePostImageUrl(imageUrl);
//
//        // db에 저장
//        PostEntity imageSavedPostEntity = postRepository.save(post);
//
//        PostDto imageSavedPostDto = PostDto.toDto(imageSavedPostEntity);

        return imageUrl;
    }
}
