package com.djs.dongjibsabackend.domain.entity;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_ingredient")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostIngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore // 에러 해결
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne // 에러 해결
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient;

    private Integer totalQty; // 구매 수량
    private Integer requiredQty; // 필요 수량
    private Integer sharingAvailableQty; // 나눔 수량

    // == builder 패턴을 사용한 생성 메서드 == //
    @Builder
    public PostIngredientEntity(PostEntity post, IngredientEntity ingredient,
                                Integer totalQty, Integer requiredQty,
                                Integer sharingAvailableQty) {
        this.post = post;
        this.ingredient = ingredient;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
    }

    public static PostIngredientEntity addIngredientToPost (PostEntity post,
                                                             IngredientEntity ingredient,
                                                             Integer totalQty,
                                                             Integer requiredQty,
                                                             Integer sharingAvailableQty) {
        return PostIngredientEntity.builder()
                                   .post(post)
                                   .ingredient(ingredient)
                                   .totalQty(totalQty)
                                   .requiredQty(requiredQty)
                                   .sharingAvailableQty(sharingAvailableQty)
                                   .build();
    }

    public static PostIngredientDto toDto(PostIngredientEntity entity) {
        return PostIngredientDto.builder()
                                .id(entity.getId())
                                .postId(entity.getPost().getId())
                                .ingredientId(entity.getIngredient().getId())
                                .totalQty(entity.getTotalQty())
                                .requiredQty(entity.getRequiredQty())
                                .sharingAvailableQty(entity.getSharingAvailableQty())
                                .build();
    }
}
