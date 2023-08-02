package com.djs.dongjibsabackend.domain.dto.ingredient;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddIngredientRequest {

    private String ingredientName;
    private Integer totalQty;
    private Integer requiredQty;
    private Integer sharingAvailableQty;

    public AddIngredientRequest(String ingredientName,
                                Integer totalQty,
                                Integer requiredQty,
                                Integer sharingAvailableQty) {
        this.ingredientName = ingredientName;
        this.totalQty = totalQty;
        this.requiredQty = requiredQty;
        this.sharingAvailableQty = sharingAvailableQty;
    }
}
