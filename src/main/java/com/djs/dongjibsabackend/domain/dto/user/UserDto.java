package com.djs.dongjibsabackend.domain.dto.user;

import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private Long userId;
    private String phoneNumber;
    private String userName;
    private Float calorieAvg;
    private Integer totalSharingNumPerRecipe; // 단일 레시피의 재료별 나눔 수량 총합
    private Integer totalSharingNum; // 모든 레시피의 나눔 수량 총합
    private List<PostDto> postDtoList;

    @Builder
    public UserDto(Long userId, String phoneNumber,
                   String userName, Float calorieAvg, Integer totalSharingNumPerRecipe,
                   Integer totalSharingNum,
                   List<PostDto> postDtoList) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.calorieAvg = calorieAvg;
        this.totalSharingNumPerRecipe = totalSharingNumPerRecipe;
        this.totalSharingNum = totalSharingNum;
        this.postDtoList = postDtoList;
    }

    public static UserDto toDto(UserEntity userEntity) {

        List<PostDto> postDtos = PostDto.toDtoList(userEntity.getPostList());

        return UserDto.builder()
                      .userId(userEntity.getId())
                      .userName(userEntity.getUserName())
                      .postDtoList(postDtos)
                      .build();
    }
}
