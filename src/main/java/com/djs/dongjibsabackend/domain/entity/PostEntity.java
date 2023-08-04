package com.djs.dongjibsabackend.domain.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 제목
    private String content; // 내용

    private Integer expectingPrice; // 예상 가격
    private Integer pricePerOne; // 1인당 가격

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // private double calorie; // 1인분 칼로리
    @OneToOne
    @JoinColumn(name = "recipe_calorie_id")
    private RecipeCalorieEntity recipeCalorie; // 1인분 칼로리

    private Integer peopleCount; // 파티원 수

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location; // 위치

    // 재료-수량 객체 리스트
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostIngredientEntity> recipeIngredients = new ArrayList<>();
    private String imgUrl; // 레시피 이미지 Url


    @Builder
    public PostEntity(Long id, String title, String content,
                      Integer expectingPrice, Integer pricePerOne,
                      UserEntity user, RecipeCalorieEntity recipeCalorie,
                      Integer peopleCount, LocationEntity location,
                      List<PostIngredientEntity> recipeIngredients,
                      String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.user = user;
        this.recipeCalorie = recipeCalorie;
        this.peopleCount = peopleCount;
        this.location = location;
        this.recipeIngredients = recipeIngredients;
        this.imgUrl = imgUrl;
    }

    // --- 연관 관계 메서드 --- //


    public void updatePostIngredients(List<PostIngredientEntity> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}