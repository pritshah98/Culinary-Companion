package com.ps.culinarycompanion.recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ps.culinarycompanion.ingredients.Ingredient;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.Digits;

@Entity(name = "recipe_ingredients")
public class RecipeIngredient {

    @EmbeddedId
    private RecipeIngredientId recipeIngredientId;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    private Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Digits(integer = 10, fraction = 2)
    private Long quantity;

    @Enumerated
    private CookingUnit unit;

    public RecipeIngredient() {

    }

    public RecipeIngredient(RecipeIngredientId recipeIngredientId, Recipe recipe,
                            Ingredient ingredient, Long quantity, CookingUnit unit) {
        this.recipeIngredientId = recipeIngredientId;
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public RecipeIngredientId getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public void setRecipeIngredientId(RecipeIngredientId recipeIngredientId) {
        this.recipeIngredientId = recipeIngredientId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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
        return "RecipeIngredients{" +
                "recipeIngredientsId=" + recipeIngredientId +
                ", recipe=" + recipe +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}
