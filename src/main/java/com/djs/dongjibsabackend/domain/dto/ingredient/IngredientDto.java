package com.djs.dongjibsabackend.domain.dto.ingredient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientDto {

    private Long id;
    private String name;

    @Builder
    public IngredientDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
