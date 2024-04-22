package com.ps.culinarycompanion.ingredients;

import com.ps.culinarycompanion.dao.IngredientRepository;
import com.ps.culinarycompanion.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository recipeRepository) {
        this.ingredientRepository = recipeRepository;
    }

    /**
     * Retrieves all the ingredients from the ingredient repository.
     *
     * @return  a list of Ingredient objects representing all the ingredients
     */
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    /**
     * Retrieves an ingredient by its ID.
     *
     * @param  id   the ID of the ingredient to retrieve
     * @return      the ingredient object with the specified ID
     */
    public Ingredient getIngredient(Integer id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);

        if (ingredient.isEmpty()) {
            throw new NotFoundException("Ingredient id:"+id);
        }

        return ingredient.get();
    }

    /**
     * Retrieves an Ingredient object by its name from the ingredient repository.
     *
     * @param  name  the name of the ingredient to retrieve
     * @return       the Ingredient object with the specified name
     * @throws NotFoundException if no ingredient with the specified name is found
     */
    public Ingredient getIngredientByName(String name) {
        Optional<Ingredient> ingredient = ingredientRepository.findByName(name);

        if (ingredient.isEmpty()) {
            throw new NotFoundException("Ingredient not found: "+name);
        }

        return ingredient.get();
    }

    /**
     * Creates an ingredient by saving it to the ingredient repository.
     *
     * @param  ingredient  the ingredient to be saved
     * @return             the saved ingredient
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    /**
     * Deletes an ingredient by its ID.
     *
     * @param  id  the ID of the ingredient to delete
     */
    public void deleteIngredient(int id) {
        ingredientRepository.deleteById(id);
    }

}
