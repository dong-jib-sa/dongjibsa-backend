package com.djs.dongjibsabackend.domain.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {

    private Long id;
    private String ingredientName;


}
