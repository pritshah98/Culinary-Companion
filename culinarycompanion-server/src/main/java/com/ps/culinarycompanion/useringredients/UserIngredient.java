package com.ps.culinarycompanion.useringredients;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ps.culinarycompanion.ingredients.Ingredient;
import com.ps.culinarycompanion.recipes.CookingUnit;
import com.ps.culinarycompanion.user.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Digits;

@Entity(name = "user_ingredients")
public class UserIngredient {

    @EmbeddedId
    private UserIngredientId userIngredientId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Digits(integer = 10, fraction = 2)
    private Long quantity;

    @Enumerated
    private CookingUnit unit;

    public UserIngredientId getUserIngredientId() {
        return userIngredientId;
    }

    public void setUserIngredientId(UserIngredientId userIngredientId) {
        this.userIngredientId = userIngredientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public CookingUnit getUnit() {
        return unit;
    }

    public void setUnit(CookingUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "UserIngredient{" +
                "userIngredientId=" + userIngredientId +
                ", user=" + user +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}
