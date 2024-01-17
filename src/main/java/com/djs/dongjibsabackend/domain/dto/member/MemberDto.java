package com.djs.dongjibsabackend.domain.dto.member;

import com.djs.dongjibsabackend.domain.dto.post.PostDto;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private Long memberId;
    private String nickName;
    private String phoneNumber;

    private Float calorieAvg;
    private Integer totalSharingNumPerRecipe; // 단일 레시피의 재료별 나눔 수량 총합
    private Integer totalSharingNum; // 모든 레시피의 나눔 수량 총합

    private String email;
    private String socialId;
    private SocialType socialType;

    private List<PostDto> postDtoList;

    @Builder
    public MemberDto(Long memberId, String nickName, String phoneNumber, String email, String socialId, SocialType socialType,
                     Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                     List<PostDto> postDtoList) {
        this.memberId = memberId;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.socialId = socialId;
        this.socialType = socialType;
        this.calorieAvg = calorieAvg;
        this.totalSharingNumPerRecipe = totalSharingNumPerRecipe;
        this.totalSharingNum = totalSharingNum;
        this.postDtoList = postDtoList;
    }

    public static MemberDto toDto(MemberEntity memberEntity) {

        /* 작성 게시글이 없는 유저 */
        if (memberEntity.getPostList() == null) {
            if (memberEntity.getPhoneNumber() != null) {
                return MemberDto.builder()
                                .memberId(memberEntity.getId())
                                .nickName(memberEntity.getNickName())
                                .phoneNumber(memberEntity.getPhoneNumber())
                                .build();
            } else if (memberEntity.getSocialType().equals(SocialType.APPLE)) {
                return MemberDto.builder()
                                .memberId(memberEntity.getId())
                                .nickName(memberEntity.getNickName())
                                .email(memberEntity.getEmail())
                                .socialType(memberEntity.getSocialType())
                                .build();
            } else if (memberEntity.getSocialType().equals(SocialType.KAKAO)){
                return MemberDto.builder()
                                .memberId(memberEntity.getId())
                                .nickName(memberEntity.getNickName())
                                .email(memberEntity.getEmail())
                                .socialType(memberEntity.getSocialType())
                                .socialId(memberEntity.getSocialId())
                                .build();
            }

        /* 기존 유저 */
        }
        List<PostDto> postDtos = PostDto.toDtoList(memberEntity.getPostList());

        return MemberDto.builder()
                        .memberId(memberEntity.getId())
                        .nickName(memberEntity.getNickName())
                        .postDtoList(postDtos)
                        .build();

    }

    public static MemberEntity toEntity (MemberDto memberDto) {
        if (memberDto.getPhoneNumber() != null) {
            log.info("success!: {}", memberDto.getPhoneNumber());
            return MemberEntity.builder()
                               .nickName(memberDto.getNickName())
                               .phoneNumber(memberDto.getPhoneNumber())
                               .build();
        } else if (memberDto.getSocialType().equals(SocialType.KAKAO)) {
            log.info("success!: {}", memberDto.getSocialType());
            return MemberEntity.builder()
                               .nickName(memberDto.getNickName())
                               .email(memberDto.getEmail())
                               .socialId(memberDto.getSocialId())
                               .socialType(memberDto.getSocialType())
                               .build();
        } else {
            log.info("success!: {}", memberDto.getSocialType());
            return MemberEntity.builder()
                               .nickName(memberDto.getNickName())
                               .email(memberDto.getEmail())
                               .socialType(memberDto.getSocialType())
                               .build();
        }
    }
}