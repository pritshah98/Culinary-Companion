package com.ps.culinarycompanion.useringredients;

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
public class UserIngredientController {

    private final UserIngredientService userIngredientService;

    public UserIngredientController(UserIngredientService userIngredientService) {
        this.userIngredientService = userIngredientService;
    }

    /**
     * Retrieves the list of user ingredients for the given user email.
     *
     * @param  userEmail  the email of the user
     * @return            the list of user ingredients
     */
    @GetMapping("/api/myingredients/{userEmail}")
    public ResponseEntity<List<UserIngredient>> getUserIngredients(@PathVariable String userEmail) {
        return new ResponseEntity<>(userIngredientService.getAllIngredients(userEmail), HttpStatus.OK);
    }

    /**
     * Adds a new user ingredient for the specified user.
     *
     * @param  userIngredient  the user ingredient to add
     * @param  userEmail       the email of the user
     * @return                a ResponseEntity containing the added user ingredient
     */
    @PostMapping("/api/myingredients/{userEmail}")
    public ResponseEntity<UserIngredient> addUserIngredient(@Valid @RequestBody UserIngredient userIngredient,
                                            @PathVariable String userEmail) {
        return new ResponseEntity<>(userIngredientService.addUserIngredient(userIngredient, userEmail),
                HttpStatus.CREATED);
    }

    /**
     * Adds a batch of user ingredients for the specified user.
     *
     * @param  userIngredients  the list of user ingredients to add
     * @param  userEmail        the email of the user
     * @return                  a ResponseEntity containing the added user ingredients
     */
    @PostMapping("/api/myingredients/batch/{userEmail}")
    public ResponseEntity<List<UserIngredient>> addUserIngredients(@Valid @RequestBody List<UserIngredient> userIngredients,
                                                            @PathVariable String userEmail) {
        return new ResponseEntity<>(userIngredientService.addUserIngredients(userIngredients, userEmail),
                HttpStatus.CREATED);
    }

    /**
     * Deletes a user ingredient for the specified user.
     *
     * @param  userEmail      the email of the user
     * @param  ingredientName the name of the ingredient to delete
     * @return               a ResponseEntity with no content
     */
    @DeleteMapping("/api/myingredients/{userEmail}/{ingredientName}")
    public ResponseEntity<?> deleteUserIngredient(@PathVariable String userEmail, @PathVariable String ingredientName) {
        userIngredientService.deleteUserIngredient(ingredientName, userEmail);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the user ingredients for the specified user.
     *
     * @param  userEmail        the email of the user
     * @param  userIngredients  the list of user ingredients to update
     * @return                  a ResponseEntity with no content
     */
    @PutMapping("/api/myingredients/{userEmail}")
    public ResponseEntity<?> editUserIngredients(@PathVariable String userEmail,
                                                      @Valid @RequestBody List<UserIngredient> userIngredients) {
        userIngredientService.updateUserIngredients(userIngredients, userEmail);
        return ResponseEntity.noContent().build();
    }

}
