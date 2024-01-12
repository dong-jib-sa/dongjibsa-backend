package com.djs.dongjibsabackend.domain.entity;

import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName; // 닉네임
    private String phoneNumber; // 전화번호 로그인 시 저장되는 필드
    private String email;
    private String socialId;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    /**
     * 회원별 데이터
     * calorieAge: 평균 섭취 칼로리
     * totalSharingNumPerPrice: 총 나눔 수량 (하나의 레시피)
     * totalSharingNum: 총 나눔 수량 (모든 레시피)
     */
    private Float calorieAvg;
    private Integer totalSharingNumPerRecipe;
    private Integer totalSharingNum;

    /**
     * 회원이 작성한 게시글 목록
     */
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<PostEntity> postList = new ArrayList<>();

    @Builder
    public MemberEntity(Long id, String nickName, String phoneNumber,
                        String email, String socialId, SocialType socialType,
                        Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                        List<PostEntity> postList) {
        this.id = id;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.socialId = socialId;
        this.socialType = socialType;
        this.calorieAvg = calorieAvg;
        this.totalSharingNumPerRecipe = totalSharingNumPerRecipe;
        this.totalSharingNum = totalSharingNum;
        this.postList = postList;
    }
}