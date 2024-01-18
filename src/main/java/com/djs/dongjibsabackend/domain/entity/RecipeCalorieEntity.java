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
@Table(name = "recipe_calorie")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeCalorieEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeName;
    private double calorie;

    @Builder
    public RecipeCalorieEntity(Long id, String recipeName, double calorie) {
        this.id = id;
        this.recipeName = recipeName;
        this.calorie = calorie;
    }
}