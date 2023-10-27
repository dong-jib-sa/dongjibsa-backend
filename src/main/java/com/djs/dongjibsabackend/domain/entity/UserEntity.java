package com.djs.dongjibsabackend.domain.entity;

import com.djs.dongjibsabackend.domain.Role;
import com.djs.dongjibsabackend.domain.SocialType;
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
    private String phoneNumber; // id type 1. 휴대폰 번호
    private String userName;
    private Float calorieAvg;
    private Integer totalSharingNumPerRecipe; // 단일 레시피의 재료별 나눔 수량 총합
    private Integer totalSharingNum; // 모든 레시피의 나눔 수량 총합
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private String socialId; // id type 2. 소셜 ID

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PostEntity> postList = new ArrayList<>();

    @Builder
    public UserEntity(Long id, String userName, Float calorieAvg, Integer totalSharingNumPerRecipe, Integer totalSharingNum,
                      SocialType socialType,
                      String socialId,
                      List<PostEntity> postList) {
        this.id = id;
        this.userName = userName;
        this.calorieAvg = calorieAvg;
        this.totalSharingNumPerRecipe = totalSharingNumPerRecipe;
        this.totalSharingNum = totalSharingNum;
        this.socialType = socialType;
        this.socialId = socialId;
        this.postList = postList;
    }
}
