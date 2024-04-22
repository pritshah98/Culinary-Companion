package com.ps.culinarycompanion.ratings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ps.culinarycompanion.recipes.Recipe;
import com.ps.culinarycompanion.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import org.hibernate.validator.constraints.Range;

@Entity(name = "rating_details")
public class Rating {

    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private Long ratingId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", referencedColumnName = "recipe_id")
    @JsonIgnore
    private Recipe recipe;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_id")
    @JsonIgnore
    private User user;

    @Digits(integer = 2,fraction = 1)
    @Range(min = 0, max = 5)
    private Long rating;

    private String comment;

    public Rating(Recipe recipe, User user, Long rating, String comment) {
        this.recipe = recipe;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }

    public Rating() {

    }

    public Long getRatingId() {
        return ratingId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", recipe=" + recipe +
                ", user=" + user +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
