package com.djs.dongjibsabackend.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeEntity extends BaseEntity {

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

    private Integer calorie; // 1인분 칼로리
    private Integer peopleCount; // 파티원 수

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location; // 위치

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient; //재료

    private Integer totalQty; // 구매 수량 (=작성자가 구매한 수량)
    private Integer requiredQty; //필요 수량 (=작성자가 사용할 수량)
    private Integer sharingAvailableQty; //나눔 수량 (=필요수량 외, 나눔 가능한 수량)
    private String imgUrl; // 레시피 이미지 Url
    private Integer commentsCount; // 댓글 수

    @Builder
    public RecipeEntity(Long id, String title, String content, Integer expectingPrice, Integer pricePerOne, UserEntity user,
                        Integer calorie,
                        Integer peopleCount, LocationEntity location, IngredientEntity ingredient, Integer totalQty, Integer requiredQty,
                        Integer sharingAvailableQty, String imgUrl, Integer commentsCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.expectingPrice = expectingPrice;
        this.pricePerOne = pricePerOne;
        this.user = user;
        this.calorie = calorie;
        this.peopleCount = peopleCount;
        this.location = location;
        this.ingredient = ingredient;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
        this.imgUrl = imgUrl;
        this.commentsCount = commentsCount;
    }
}