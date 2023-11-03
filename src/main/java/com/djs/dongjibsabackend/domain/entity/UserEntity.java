package com.djs.dongjibsabackend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String userName; // 닉네임, 변경 가능
    private String phoneNumber; // 전화번호 로그인 시 저장되는 필드

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
    public UserEntity(Long id, String userName, String phoneNumber,
                      Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                      List<PostEntity> postList) {
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.calorieAvg = calorieAvg;
        this.totalSharingNumPerRecipe = totalSharingNumPerRecipe;
        this.totalSharingNum = totalSharingNum;
        this.postList = postList;
    }
}