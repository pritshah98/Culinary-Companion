package com.ps.culinarycompanion.recipes;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "recipe_details")
public class Recipe {

    @GeneratedValue
    @Id
    @Column(name = "recipe_id")
    private Integer recipeId;

    @Nonnull
    private String title;

    private String description;

    private String instructions;

    private String userEmail;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients;

    @CurrentTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate lastModifiedDate;

    public Recipe() {

    }

    public Recipe(@Nonnull String title, String description, String instructions,
                  String userEmail, List<RecipeIngredient> ingredients, LocalDate createdDate, LocalDate lastModifiedDate) {
        this.title = title;
        this.description = description;
        this.instructions = instructions;
        this.userEmail = userEmail;
        this.ingredients = ingredients;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nonnull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructions='" + instructions + '\'' +
                ", userEmail=" + userEmail +
                ", ingredients=" + ingredients +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
