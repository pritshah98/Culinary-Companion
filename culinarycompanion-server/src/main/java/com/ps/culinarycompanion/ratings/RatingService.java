package com.ps.culinarycompanion.ratings;

import com.ps.culinarycompanion.dao.RatingRepository;
import com.ps.culinarycompanion.recipes.Recipe;
import com.ps.culinarycompanion.recipes.RecipeService;
import com.ps.culinarycompanion.user.User;
import com.ps.culinarycompanion.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    private final UserService userService;

    private final RecipeService recipeService;

    private final EntityManager entityManager;

    public RatingService(RatingRepository ratingRepository, UserService userService,
                         RecipeService recipeService, EntityManager entityManager) {
        this.ratingRepository = ratingRepository;
        this.entityManager = entityManager;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    /**
     * Retrieves the average rating and total number of ratings for a given recipe ID.
     *
     * @param  id  the ID of the recipe
     * @return     a list containing the average rating and total number of ratings, in that order. If no ratings are found,
     *             returns a list with two zeros.
     */
    public List<Long> getRecipeRatings(Integer id) {
        List<Rating> allRatings = ratingRepository.findAll().stream()
                .filter(rating -> rating.getRecipe().getRecipeId().equals(id))
                .toList();
        if (allRatings.isEmpty()) {
            return List.of(0L, 0L);
        }
        long sum = allRatings.stream()
                .mapToLong(Rating::getRating)
                .sum();
        return List.of(sum / allRatings.size(), (long) allRatings.size());
    }

    /**
     * Retrieves the rating for a specific recipe and user.
     *
     * @param  recipeId   the ID of the recipe
     * @param  userEmail  the email of the user
     * @return            the rating for the recipe and user, or null if not found
     */
    public Rating getRating(Integer recipeId, String userEmail) {
        Optional<Rating> userRating = ratingRepository.findAll().stream()
                .filter(rating -> rating.getRecipe().getRecipeId().equals(recipeId)
                        && rating.getUser().getUsername().equals(userEmail))
                .findFirst();

        return userRating.orElse(null);

    }

    /**
     * Creates a new rating for a recipe and user.
     *
     * @param  rating    the rating object to be created
     * @param  recipeId  the ID of the recipe
     * @param  email     the email of the user
     * @return           the created rating object
     */
    public Rating createRating(Rating rating, Integer recipeId, String email) {

        Recipe recipe = recipeService.getRecipe(recipeId);

        User user = userService.getUserByEmail(email);

        rating.setUser(user);
        rating.setRecipe(recipe);

        return ratingRepository.save(rating);
    }

    /**
     * Updates the rating for a given ID with the provided rating object.
     *
     * @param  rating  the rating object containing the new rating and comment
     * @param  id      the ID of the rating to be updated
     */
    @Transactional
    public void updateRating(Rating rating, Integer id) {

        System.out.println(rating.toString());

        try {
            Rating existingRating = entityManager.find(Rating.class, id);

            if (existingRating != null) {
                existingRating.setRating(rating.getRating());
                existingRating.setComment(rating.getComment());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Deletes all ratings associated with a specific recipe ID.
     *
     * @param  recipeId  the ID of the recipe for which ratings are to be deleted
     */
    public void deleteRatings(Integer recipeId) {
        List<Rating> ratings = ratingRepository.findAll().stream()
                .filter(rating -> rating.getRecipe().getRecipeId().equals(recipeId))
                .toList();
        ratingRepository.deleteAll(ratings);
    }

}
