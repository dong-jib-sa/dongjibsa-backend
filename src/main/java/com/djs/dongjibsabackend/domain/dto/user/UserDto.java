package com.djs.dongjibsabackend.domain.dto.user;

import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private Long userId;
    private String userName;

    private String phoneNumber; // PhoneNumber Auth Login
    private String email; // OAuth Login

    private SocialType socialType;
    private String socialId;

    private String refreshToken;

    private Float calorieAvg;
    private Integer totalSharingNumPerRecipe; // 단일 레시피의 재료별 나눔 수량 총합
    private Integer totalSharingNum; // 모든 레시피의 나눔 수량 총합

    private List<PostDto> postDtoList;

    @Builder
    public UserDto(Long userId, String userName, String phoneNumber, String email,
                   SocialType socialType, String socialId, String refreshToken,
                   Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                   List<PostDto> postDtoList) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
        this.refreshToken = refreshToken;
        this.calorieAvg = calorieAvg;
        this.totalSharingNumPerRecipe = totalSharingNumPerRecipe;
        this.totalSharingNum = totalSharingNum;
        this.postDtoList = postDtoList;
    }

    /*
    [ ] 인증, 인가 방식에 따라 toDto 메서드 보완 필요
     */
    public static UserDto toDto(UserEntity userEntity) {

        List<PostDto> postDtos = PostDto.toDtoList(userEntity.getPostList());

        return UserDto.builder()
                      .userId(userEntity.getId())
                      .userName(userEntity.getUserName())
                      .postDtoList(postDtos)
                      .build();
    }

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                         .phoneNumber(userDto.getPhoneNumber())
                         .email(userDto.getEmail())
                         .build();
    }
}
