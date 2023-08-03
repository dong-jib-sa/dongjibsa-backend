package com.djs.dongjibsabackend.domain.dto.post_ingredient;

import com.djs.dongjibsabackend.domain.entity.PostIngredientEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostIngredientDto {

    private Long id;
    private Long postId; // Post Entity
    private Long ingredientId; // Ingredient Entity
    private String ingredientName; // 재료명
    private Integer totalQty;
    private Integer requiredQty;
    private Integer sharingAvailableQty;

    @Builder
    public PostIngredientDto(Long id, Long postId, Long ingredientId, String ingredientName, Integer totalQty, Integer requiredQty,
                             Integer sharingAvailableQty) {
        this.id = id;
        this.postId = postId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
    }




    /*
    static method turning Entity to DTO
     */
    public static PostIngredientDto of (PostIngredientEntity postIngredientEntity) {

        return new PostIngredientDto(
            postIngredientEntity.getId(),
            postIngredientEntity.getPost().getId(), //recipe Entity id
            postIngredientEntity.getIngredient().getId(), // Ingredient Entity id
            postIngredientEntity.getIngredient().getIngredientName(), // Ingredient Entity name
            postIngredientEntity.getTotalQty(),
            postIngredientEntity.getRequiredQty(),
            postIngredientEntity.getSharingAvailableQty()
        );
    }

    public static List<PostIngredientDto> of (List<PostIngredientEntity> entityList) {
        List<PostIngredientDto> dtoList = new ArrayList<>();

        for (PostIngredientEntity entity: entityList) {
            PostIngredientDto dto = PostIngredientDto.of(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

}