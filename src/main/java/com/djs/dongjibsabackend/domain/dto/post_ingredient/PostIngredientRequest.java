package com.djs.dongjibsabackend.domain.dto.post_ingredient;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostIngredientRequest {

    /**
     *  재료 이름, 구매 수량, 필요 수량, 판매 수량
     */
    private String ingredientName;
    private double totalQty;
    private double requiredQty;
    private double sharingAvailableQty;

}