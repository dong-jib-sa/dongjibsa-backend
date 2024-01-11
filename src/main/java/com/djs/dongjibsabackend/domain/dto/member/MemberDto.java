package com.djs.dongjibsabackend.domain.dto.member;

import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private Long memberId;
    private String nickName;

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
    public MemberDto(Long memberId, String nickName, String phoneNumber, String email,
                     SocialType socialType, String socialId, String refreshToken,
                     Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                     List<PostDto> postDtoList) {
        this.memberId = memberId;
        this.nickName = nickName;
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
    public static MemberDto toDto(MemberEntity memberEntity) {

        List<PostDto> postDtos = PostDto.toDtoList(memberEntity.getPostList());

        return MemberDto.builder()
                        .memberId(memberEntity.getId())
                        .nickName(memberEntity.getNickName())
                        .postDtoList(postDtos)
                        .build();
    }

    public static MemberEntity toEntity(MemberDto memberDto) {
        return MemberEntity.builder()
                           .phoneNumber(memberDto.getPhoneNumber())
                           .email(memberDto.getEmail())
                           .build();
    }
}
