package com.djs.dongjibsabackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ingredient")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ingredientName;

    @Builder
    public IngredientEntity(Long id, String ingredientName) {
        this.id = id;
        this.ingredientName = ingredientName;
    }
}