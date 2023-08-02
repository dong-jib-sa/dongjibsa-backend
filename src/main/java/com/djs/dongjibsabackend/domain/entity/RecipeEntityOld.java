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
@Table(name = "recipeOld")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeEntityOld extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 제목
    private String content; // 내용

    private Integer expectingPrice; // 예상 가격
    private Integer pricePerOne; // 1인당 가격

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;

    private Integer calorie; // 1인분 칼로리
    private Integer peopleCount; // 파티원 수

//    @OneToOne
//    @JoinColumn(name = "location_id")
//    private LocationEntity location; // 위치

//    @OneToMany(mappedBy = "recipe", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<RecipeIngredientEntity> ingredientList = new ArrayList<>();
//    private String imgUrl; // 레시피 이미지 Url


    @Builder
    public RecipeEntityOld(Long id, String title, String content,
                           Integer expectingPrice, Integer pricePerOne,
                           UserEntity user, Integer calorie, Integer peopleCount,
                           LocationEntity location,
                           List<RecipeIngredientEntity> ingredientList,
                           String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
//        this.user = user;
        this.calorie = calorie;
        this.peopleCount = peopleCount;
//        this.location = location;
//        this.ingredientList = ingredientList;
//        this.imgUrl = imgUrl;
    }

//    public void addIngredientToRecipe(RecipeIngredientEntity ingredient) {
//        ingredientList.add(ingredient);
//    }
}