package com.ps.culinarycompanion.useringredients;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserIngredientId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "ingredient_id")
    private Integer ingredientId;

    public UserIngredientId(Integer userId, Integer ingredientId) {
        this.userId = userId;
        this.ingredientId = ingredientId;
    }

    public UserIngredientId() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIngredientId that = (UserIngredientId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, ingredientId);
    }
}
