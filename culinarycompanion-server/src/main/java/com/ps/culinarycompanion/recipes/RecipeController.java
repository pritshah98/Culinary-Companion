package com.ps.culinarycompanion.recipes;

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
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Retrieves all recipes from the API.
     *
     * @return         	A ResponseEntity containing a list of Recipe objects and the HTTP status code OK.
     */
    @GetMapping("/api/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    /**
     * Retrieves a specific recipe from the API based on the provided ID.
     *
     * @param  id    the unique identifier of the recipe to retrieve
     * @return       a ResponseEntity containing the requested Recipe object and the HTTP status code OK
     */
    @GetMapping("/api/recipes/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.OK);
    }

    /**
     * Retrieves recipes created by a specific user based on the provided email.
     *
     * @param  email    the email of the user to retrieve recipes for
     * @return          a ResponseEntity containing a list of Recipe objects created by the user and the HTTP status code OK
     */
    @GetMapping("/api/recipes/user/{email}")
    public ResponseEntity<List<Recipe>> getUsersRecipes(@PathVariable String email) {
        return new ResponseEntity<>(recipeService.getAllUserCreatedRecipes(email), HttpStatus.OK);
    }

    /**
     * Retrieves the list of ingredients for a specific recipe based on the provided ID.
     *
     * @param  id    the unique identifier of the recipe
     * @return       a ResponseEntity containing the list of RecipeIngredient objects and the HTTP status code OK
     */
    @GetMapping("/api/recipes/{id}/ingredients")
    public ResponseEntity<List<RecipeIngredient>> getIngredients(@PathVariable int id) {
        return new ResponseEntity<>(recipeService.getRecipeIngredients(id), HttpStatus.OK);
    }

    /**
     * Retrieves the list of ingredients for a specific recipe based on the provided ID.
     *
     * @param  id    the unique identifier of the recipe
     * @return       a ResponseEntity containing the list of RecipeIngredient objects and the HTTP status code OK
     */
    @PostMapping("/api/recipes/{id}/ingredients")
    public ResponseEntity<Recipe> addIngredient(@Valid @RequestBody RecipeIngredient recipeIngredient,
                                          @PathVariable Integer id) {
        return new ResponseEntity<>(recipeService.addIngredient(recipeIngredient, id), HttpStatus.CREATED);
    }

    /**
     * Adds a batch of ingredients to a recipe.
     *
     * @param  recipeIngredients  a list of recipe ingredients to be added
     * @param  id                 the ID of the recipe to add the ingredients to
     * @return                    a ResponseEntity containing the updated recipe and the HTTP status code CREATED
     */
    @PostMapping("/api/recipes/{id}/ingredients/batch")
    public ResponseEntity<Recipe> addIngredients(@Valid @RequestBody List<RecipeIngredient> recipeIngredients,
                                                 @PathVariable Integer id) {
        return new ResponseEntity<>(recipeService.addIngredients(recipeIngredients, id), HttpStatus.CREATED);
    }

    /**
     * Updates the ingredients of a recipe with the given ID.
     *
     * @param  ingredients  the list of recipe ingredients to update
     * @param  id           the ID of the recipe to update
     * @return              a ResponseEntity with no content and the HTTP status code OK
     */
    @PutMapping("/api/recipes/{id}/ingredients")
    public ResponseEntity<?> updateIngredients(@Valid @RequestBody List<RecipeIngredient> ingredients,
                                               @PathVariable Integer id) {
        recipeService.updateIngredients(ingredients, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Creates a new recipe in the API.
     *
     * @param  recipe   the Recipe object to create
     * @return          a ResponseEntity containing the created Recipe object and the HTTP status code CREATED
     */
    @PostMapping("/api/recipes")
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
        return new ResponseEntity<>(recipeService.createRecipe(recipe), HttpStatus.CREATED);
    }

    /**
     * Updates a recipe with the given ID in the API.
     *
     * @param  recipe   the updated Recipe object
     * @param  id       the ID of the recipe to update
     * @return          a ResponseEntity containing the updated Recipe object and the HTTP status code OK
     */
    @PutMapping("/api/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Integer id) {
        return new ResponseEntity<>(recipeService.updateRecipe(id, recipe), HttpStatus.OK);
    }

    /**
     * Deletes a recipe with the specified ID.
     *
     * @param  id  the ID of the recipe to delete
     * @return     a ResponseEntity with no content and the HTTP status code OK
     */
    @DeleteMapping("/api/recipes/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

}
