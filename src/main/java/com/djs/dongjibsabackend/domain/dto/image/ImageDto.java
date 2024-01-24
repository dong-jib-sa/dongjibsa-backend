package com.djs.dongjibsabackend.domain.dto.image;

import com.djs.dongjibsabackend.domain.entity.ImageEntity;
import com.djs.dongjibsabackend.domain.entity.PostEntity;
import com.djs.dongjibsabackend.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageDto {

    private Long id;
    private Long postId;
    private String imageUrl;

    @Builder
    public ImageDto(Long id, Long postId, String imageUrl) {
        this.id = id;
        this.postId = postId;
        this.imageUrl = imageUrl;
    }

    public static ImageDto toDto(ImageEntity savedImage) {

        return ImageDto.builder()
                       .id(savedImage.getId())
                       .postId(savedImage.getPost().getId())
                       .imageUrl(savedImage.getUrl())
                       .build();
    }

    public static List<ImageEntity> toEntity(List<ImageDto> dtos, PostEntity post) {

        List<ImageEntity> imageEntityList = new ArrayList<>();

        for (ImageDto dto: dtos) {
            ImageEntity imageEntity = ImageEntity.builder()
                                                 .id(dto.getId())
                                                 .url(dto.getImageUrl())
                                                 .post(post)
                                                 .build();
            imageEntityList.add(imageEntity);
        }

        return imageEntityList;
    }

    public static List<String> of (List<ImageEntity> list) {
        List<String> urls = new ArrayList<>();
        for (ImageEntity image: list) {
            String url = image.getUrl();
            urls.add(url);
        }
        return urls;
    }

}
