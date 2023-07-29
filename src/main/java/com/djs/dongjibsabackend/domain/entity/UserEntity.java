package com.djs.dongjibsabackend.domain.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private Float calorieAvg;
    private Integer totalSharingNum;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<RecipeEntity> recipeList = new ArrayList<>();

    private Integer totalSharingNumPerRecipe; // 단일 레시피 내 각 재료별 나눔 수량의 총합
}
