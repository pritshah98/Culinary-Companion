package com.ps.culinarycompanion.useringredients;

import com.ps.culinarycompanion.dao.UserIngredientRepository;
import com.ps.culinarycompanion.exception.NotFoundException;
import com.ps.culinarycompanion.ingredients.Ingredient;
import com.ps.culinarycompanion.ingredients.IngredientService;
import com.ps.culinarycompanion.user.User;
import com.ps.culinarycompanion.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserIngredientService {

    private final UserIngredientRepository userIngredientRepository;

    private final UserService userService;

    private final IngredientService ingredientService;

    public UserIngredientService(UserIngredientRepository userIngredientRepository, UserService userService,
                                 IngredientService ingredientService) {
        this.userIngredientRepository = userIngredientRepository;
        this.userService = userService;
        this.ingredientService = ingredientService;
    }

    /**
     * Retrieves a list of all user ingredients associated with the given email.
     *
     * @param  email  the email of the user
     * @return        a list of UserIngredient objects associated with the user
     */
    public List<UserIngredient> getAllIngredients(String email) {
        List<UserIngredient> allUserIngs = userIngredientRepository.findAll();
        return allUserIngs.stream()
                .filter(ing -> !ing.getUser().getUsername().isEmpty())
                .filter(ing -> ing.getUser().getUsername().equals(email))
                .toList();
    }

    /**
     * Adds a list of user ingredients to the user's collection.
     *
     * @param  userIngredients  the list of user ingredients to be added
     * @param  userEmail        the email of the user
     * @return                  the list of added ingredients
     */
    public List<UserIngredient> addUserIngredients(List<UserIngredient> userIngredients, String userEmail) {
        List<UserIngredient> addedIngredients = new ArrayList<>();

        userIngredients.forEach(userIngredient -> {
            UserIngredient ing = addUserIngredient(userIngredient, userEmail);
            addedIngredients.add(ing);
        });

        return addedIngredients;
    }

    /**
     * Adds a user ingredient to the user's collection. If the ingredient does not exist, it is created.
     *
     * @param  userIngredient  the user ingredient to be added
     * @param  userEmail       the email of the user
     * @return                 the added user ingredient
     */
    public UserIngredient addUserIngredient(UserIngredient userIngredient, String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        Ingredient ingredient;

        try {
            ingredient = ingredientService.getIngredientByName(userIngredient.getIngredient().getName());
        } catch (Exception ex) {
            ingredient = ingredientService.createIngredient(new Ingredient(userIngredient.getIngredient().getName()));
        }

        UserIngredientId id = new UserIngredientId();

        id.setIngredientId(ingredient.getIngredientId());
        id.setUserId(user.getUserId());

        userIngredient.setUser(user);
        userIngredient.setUserIngredientId(id);
        userIngredient.setIngredient(ingredient);

        return userIngredientRepository.save(userIngredient);
    }

    /**
     * Deletes a user ingredient from the database based on the ingredient name and user email.
     *
     * @param  ingredientName  the name of the ingredient to be deleted
     * @param  email           the email of the user associated with the ingredient
     * @throws NotFoundException if no ingredient with the specified name and email is found
     */
    public void deleteUserIngredient(String ingredientName, String email) {
        Optional<UserIngredient> found = userIngredientRepository.findAll().stream()
                .filter(ing -> ing.getIngredient().getName() != null)
                .filter(ing -> ing.getIngredient().getName().equals(ingredientName)
                && ing.getUser().getUsername().equals(email))
                .findFirst();

        if (found.isEmpty()) {
            throw new NotFoundException("Ingredient not found");
        }

        userIngredientRepository.delete(found.get());
    }

    /**
     * Updates the quantities and units of user ingredients based on the provided list of UserIngredient objects
     * associated with the specified user email.
     *
     * @param  userIngredients  the list of UserIngredient objects to update
     * @param  userEmail         the email of the user
     */
    public void updateUserIngredients(List<UserIngredient> userIngredients, String userEmail) {
        userIngredients.forEach(userIngredient -> {

            UserIngredient found = userIngredientRepository.findAll().stream()
                    .filter(ing -> ing.getIngredient().getName().equals(userIngredient.getIngredient().getName())
                            && ing.getUser().getUsername().equals(userEmail))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Ingredient not found"));

            found.setQuantity(userIngredient.getQuantity());
            found.setUnit(userIngredient.getUnit());

            userIngredientRepository.save(found);
        });
    }

}
