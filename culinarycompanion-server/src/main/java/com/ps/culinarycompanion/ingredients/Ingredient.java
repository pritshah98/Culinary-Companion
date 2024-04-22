package com.ps.culinarycompanion.ingredients;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "ingredient_details")
public class Ingredient {

    @GeneratedValue
    @Id
    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @Column(unique = true)
    @Nonnull
    private String name;

    public Ingredient(@Nonnull String name) {
        this.name = name;
    }

    public Ingredient() {

    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", name='" + name + '\'' +
                '}';
    }
}
