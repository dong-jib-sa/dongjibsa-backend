package com.djs.dongjibsabackend.domain.dto.post_ingredient;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostIngredientRequest {

    private String ingredientName;
    private double totalQty;
    private double requiredQty;
    private double sharingAvailableQty;

}