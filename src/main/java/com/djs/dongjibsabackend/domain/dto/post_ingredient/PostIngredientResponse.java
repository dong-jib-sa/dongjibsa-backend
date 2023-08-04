package com.djs.dongjibsabackend.domain.dto.post_ingredient;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostIngredientResponse {

    private String ingredientName;
    private double totalQty;
    private double requiredQty;
    private double sharingAvailableQty;

    @Builder
    public PostIngredientResponse(String ingredientName, double totalQty, double requiredQty, double sharingAvailableQty) {
        this.ingredientName = ingredientName;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
    }

    public static PostIngredientResponse of(PostIngredientDto dto) {
        return PostIngredientResponse.builder()
                                     .ingredientName(dto.getIngredientName())
                                     .totalQty(dto.getTotalQty())
                                     .requiredQty(dto.getRequiredQty())
                                     .sharingAvailableQty(dto.getSharingAvailableQty())
                                     .build();
    }

    public static List<PostIngredientResponse> of (List<PostIngredientDto> postIngredientDto) {

        List<PostIngredientResponse> resList = new ArrayList<>();

        for (PostIngredientDto dto: postIngredientDto) {
            PostIngredientResponse res = PostIngredientResponse.of(dto);
            resList.add(res);
        }

        return  resList;
    }

}