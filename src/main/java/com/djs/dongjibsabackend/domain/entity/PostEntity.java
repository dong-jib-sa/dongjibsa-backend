package com.djs.dongjibsabackend.domain.entity;


import com.djs.dongjibsabackend.domain.dto.image.ImageDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OneToOne
    @JoinColumn(name = "recipe_calorie_id")
    private RecipeCalorieEntity recipeCalorie; // 1인분 칼로리

    private Integer peopleCount; // 파티원 수

//    @OneToOne
//    @JoinColumn(name = "location_id")
//    private LocationEntity location; // 위치

    // 재료-수량 객체 리스트
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostIngredientEntity> recipeIngredients = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ImageEntity> imgUrls; // 레시피 이미지 Url

    @Builder
    public PostEntity(Long id, String title, String content,
                      Integer expectingPrice, Integer pricePerOne,
                      MemberEntity member, RecipeCalorieEntity recipeCalorie,
                      Integer peopleCount,
                      // LocationEntity location,
                      List<PostIngredientEntity> recipeIngredients,
                      List<ImageEntity> imgUrls) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.member = member;
        this.recipeCalorie = recipeCalorie;
        this.peopleCount = peopleCount;
        // this.location = location;
        this.recipeIngredients = recipeIngredients;
        this.imgUrls = imgUrls;
    }

    // ** Methods
    /* 1. 재료 목록 업데이트 */
    public void updatePostIngredients(List<PostIngredientEntity> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    /* 2. S3 Url 업데이트 */
    public void updatePostImageUrl(List<ImageEntity> urls) {
        this.imgUrls = urls;
    }

    /* 3. 칼로리 업데이트 */
    public void updateRecipeCalorie(RecipeCalorieEntity recipeCalorieEntity) {
        this.recipeCalorie = recipeCalorieEntity;
    }
}