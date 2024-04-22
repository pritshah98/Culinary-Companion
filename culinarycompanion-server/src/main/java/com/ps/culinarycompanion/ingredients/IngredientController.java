package com.ps.culinarycompanion.ingredients;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Retrieves all ingredients from the ingredient service and returns them as a ResponseEntity containing a list of Ingredient objects.
     *
     * @return A ResponseEntity containing a list of Ingredient objects and an HTTP status code of 200 (OK).
     */
    @GetMapping("/api/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return new ResponseEntity<>(ingredientService.getAllIngredients(), HttpStatus.OK);
    }

    /**
     * Retrieves an ingredient by its ID.
     *
     * @param  id   the ID of the ingredient to retrieve
     * @return      the ingredient object with the specified ID
     */
    @GetMapping("/api/ingredients/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
        return new ResponseEntity<>(ingredientService.getIngredient(id), HttpStatus.OK);
    }

    /**
     * Creates a new ingredient by calling the createIngredient method in the ingredientService and returns a ResponseEntity with the created ingredient and HTTP status code 201 (CREATED).
     *
     * @param  ingredient  the Ingredient object to be created
     * @return             a ResponseEntity containing the created Ingredient and an HTTP status code of 201 (CREATED)
     */
    @PostMapping("/api/ingredients")
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody Ingredient ingredient) {
        return new ResponseEntity<>(ingredientService.createIngredient(ingredient), HttpStatus.CREATED);
    }

    /**
     * Deletes an ingredient by its ID.
     *
     * @param  id  the ID of the ingredient to delete
     * @return     the HTTP response entity indicating the result of the operation
     */
    @DeleteMapping("/api/ingredients/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable int id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

}
