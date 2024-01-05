package com.djs.dongjibsabackend.domain.entity;

import com.djs.dongjibsabackend.domain.enums.Role;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName; // 닉네임

    /**
     * Case 1: 전화번호 로그인
     */
    private String phoneNumber;

    /**
     *  Case 2: 소셜 로그인
     */
    private String email;
    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, APPLE
    private String socialId; // 로그인 Case 2의 경우에만 저장됨

    private Role role;

    /**
     *  Access Token 재발급을 위한 Refresh Token
     */
    private String refreshToken;

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
    @OneToMany(mappedBy = "user")
    private List<PostEntity> postList = new ArrayList<>();

    @Builder
    public UserEntity(Long id, String userName, String phoneNumber, String email,
                      SocialType socialType, String socialId, Role role, String refreshToken,
                      Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                      List<PostEntity> postList) {
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
        this.role = role;
        this.refreshToken = refreshToken;
        this.calorieAvg = calorieAvg;
        this.totalSharingNumPerRecipe = totalSharingNumPerRecipe;
        this.totalSharingNum = totalSharingNum;
        this.postList = postList;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}