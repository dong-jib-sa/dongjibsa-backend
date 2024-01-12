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
    private String phoneNumber;

    private Float calorieAvg;
    private Integer totalSharingNumPerRecipe; // 단일 레시피의 재료별 나눔 수량 총합
    private Integer totalSharingNum; // 모든 레시피의 나눔 수량 총합
    private String email;
    private String socialId;
    private SocialType socialType;

    private List<PostDto> postDtoList;

    @Builder
    public UserDto(Long userId, String userName, String phoneNumber, String email, String socialId, SocialType socialType,
                   Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                   List<PostDto> postDtoList) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.socialId = socialId;
        this.socialType = socialType;
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

    public static UserEntity toEntity (UserDto userDto) {
        return UserEntity.builder()
                         .phoneNumber(userDto.getPhoneNumber())
                         .email(userDto.getEmail())
                         .socialId(userDto.getSocialId())
                         .socialType(userDto.getSocialType())
                         .build();
    }

    public static UserDto toDto(String email, SocialType socialType, String socialId) {
         return UserDto.builder()
             .email(email)
             .socialType(socialType)
             .socialId(socialId)
             .build();

    }

    public static UserDto toDto(String phoneNumber) {
        return UserDto.builder()
                      .phoneNumber(phoneNumber)
                      .build();
    }
}
