package com.djs.dongjibsabackend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.djs.dongjibsabackend.domain.dto.image.ImageDto;
import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.entity.ImageEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.ImageRepository;
import com.djs.dongjibsabackend.repository.PostRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String s3DirName = "/postImg";

    // 게시글 검증
    public PostEntity validatePost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        return post;
    }

    /* 파일 업로드 */
    public List<ImageDto> uploadAndSaveToDB(List<MultipartFile> multipartFiles, Long postId) throws MissingServletRequestPartException {

        List<ImageDto> imageUrls = new ArrayList<>();
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND,
                                                                            String.format("%s는 존재하지 않는 게시글입니다.", postId)));

        for (MultipartFile file: multipartFiles) {

            /* 1. 이미지 파일 검증*/
            if (file.isEmpty()) {
                throw new MissingServletRequestPartException("이미지가 존재하지 않습니다!");
            }

            /* 2. 파일 정보로 메타데이터 생성 */
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            /* 3. 파일 제목 생성 */
            String uploadFileName = file.getOriginalFilename();
            log.info("생성된 파일 제목: {}", uploadFileName);

            /* 4. 이미지 파일 확장자 검증 */
            int index;
            try {
                index = uploadFileName.lastIndexOf(".");
            } catch (StringIndexOutOfBoundsException e) {
                throw new AppException(ErrorCode.WRONG_FILE_FORMAT, "잘못된 파일 형식입니다.");
            }

            /* 5. 파일 확장자 추출 */
            String extension = uploadFileName.substring(index + 1);
            log.debug("extension:", extension);

            /* 5. 3, 5를 합한 파일명 생성 */
            String awsS3FileName = UUID.randomUUID() + "." + extension;
            log.debug("awsS3FileName:", awsS3FileName);

            String key = "postImg/" + awsS3FileName;
            log.debug("key:", key);

            /* 6. S3 서버에 이미지 파일 업로드 */
            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(
                    new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                log.error("S3 파일 업로드 중 오류 발생", e);
//                   e.printStackTrace();
                throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "통신에 실패했습니다.");
            }

            /* 7. S3 서버에 저장된 이미지 파일의 url 추출 */
            String imageUrl = amazonS3Client.getUrl(bucket, key).toString();
            log.debug("imageUrl: ", imageUrl);

            /* 8. 이미지 엔티티 생성 */
            ImageEntity image = ImageEntity.builder().post(post).url(imageUrl).build();
            ImageEntity savedImage = imageRepository.save(image); // save.
            ImageDto imageDto = ImageDto.toDto(savedImage);
            log.debug("image id: {}", savedImage.getId());
            imageUrls.add(imageDto);
        }

        return imageUrls;
    }

    /* 파일 삭제 */
    public String deleteFile(Long postId, String url) {
        log.debug("이미지 Url: {}", url);
        String expression = ".com/";
        String uuidFileName = url.substring(url.lastIndexOf(expression) + expression.length());
        log.debug("uuidFileName: {}", uuidFileName);
        String result ="";

        try {
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, uuidFileName);
            log.debug("객체 존재 여부: {}", isObjectExist);

            if (isObjectExist) { // true
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, uuidFileName));
                log.debug("S3 파일 삭제 완료: {}", uuidFileName);
                result = "SUCCESS";
            } else { // file Not Exist
                log.error("해당 파일이 존재하지 않습니다.");
                result = "FAIL ...";
            }
        } catch (Exception e) {
            log.debug("파일 삭제에 실패했습니다. {}", e);
        }

        return result;
    }
}
