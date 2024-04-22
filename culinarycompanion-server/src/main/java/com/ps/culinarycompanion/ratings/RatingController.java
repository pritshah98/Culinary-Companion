package com.ps.culinarycompanion.ratings;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Retrieves the average rating for a recipe with the given ID.
     *
     * @param  id  the ID of the recipe
     * @return     the average rating of the recipe as a list of Long values
     */
    @GetMapping("/api/recipes/{id}/rating")
    public ResponseEntity<List<Long>> getAvgRecipeRating(@PathVariable Integer id) {
        return new ResponseEntity<>(ratingService.getRecipeRatings(id), HttpStatus.OK);
    }

    /**
     * Retrieves the user rating for a specific recipe and user.
     *
     * @param  recipeId   the ID of the recipe
     * @param  userEmail  the email of the user
     * @return            the user rating for the specified recipe and user, wrapped in a ResponseEntity with the OK status
     */
    @GetMapping("/api/ratings/{recipeId}/{userEmail}")
    public ResponseEntity<Rating> getUserRatingForRecipe(@PathVariable Integer recipeId,
                                                         @PathVariable String userEmail) {
        return new ResponseEntity<>(ratingService.getRating(recipeId, userEmail), HttpStatus.OK);
    }

    /**
     * Creates a new rating for a recipe with the given ID and user email.
     *
     * @param  rating  the rating object to be created
     * @param  id      the ID of the recipe
     * @param  email   the email of the user
     * @return         the ResponseEntity containing the created rating with HttpStatus.CREATED
     */
    @PostMapping("/api/ratings/add/{id}/{email}")
    public ResponseEntity<Rating> createRating(@Valid @RequestBody Rating rating, @PathVariable Integer id,
                                               @PathVariable String email) {
        return new ResponseEntity<>(ratingService.createRating(rating, id, email), HttpStatus.CREATED);
    }

    /**
     * Updates a rating for a recipe with the given rating ID.
     *
     * @param  rating   the updated rating object
     * @param  ratingId the ID of the rating to be updated
     * @return          a ResponseEntity with no content and the OK status
     */
    @PutMapping("/api/recipes/ratings/{ratingId}/update")
    public ResponseEntity<?> updateRating(@Valid @RequestBody Rating rating, @PathVariable Integer ratingId) {
        ratingService.updateRating(rating, ratingId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes ratings for a specific recipe.
     *
     * @param  recipeId  the ID of the recipe for which ratings will be deleted
     * @return          a ResponseEntity with no content
     */
    @DeleteMapping("/api/recipes/{recipeId}/ratings")
    public ResponseEntity<?> deleteRatingsForRecipe(@PathVariable Integer recipeId) {
        ratingService.deleteRatings(recipeId);
        return ResponseEntity.noContent().build();
    }

}
