package com.ps.culinarycompanion.recipes;

import com.ps.culinarycompanion.dao.RecipeRepository;
import com.ps.culinarycompanion.exception.NotFoundException;
import com.ps.culinarycompanion.ingredients.Ingredient;
import com.ps.culinarycompanion.ingredients.IngredientService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final IngredientService ingredientService;

    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
    }

    /**
     * Retrieves all the recipes from the recipe repository.
     *
     * @return  a list of Recipe objects representing all the recipes in the repository
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Retrieves all the recipes created by the user with the specified email.
     *
     * @param  email  the email of the user
     * @return        a list of Recipe objects representing all the recipes created by the user
     */
    public List<Recipe> getAllUserCreatedRecipes(String email) {
        return recipeRepository.findAll().stream()
                .filter(recipe -> recipe.getUserEmail().equals(email))
                .toList();
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param  id  the ID of the recipe to retrieve
     * @return     the Recipe object representing the retrieved recipe
     */
    public Recipe getRecipe(int id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe id:"+id);
        }

        return recipe.get();
    }

    /**
     * Retrieves the ingredients of a recipe by its ID.
     *
     * @param  id  the ID of the recipe to retrieve the ingredients from
     * @return     a list of RecipeIngredient objects representing the ingredients of the recipe
     */
    public List<RecipeIngredient> getRecipeIngredients(int id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe id:"+id);
        }

        return recipe.get().getIngredients();
    }

    /**
     * Adds a list of ingredients to a recipe.
     *
     * @param  recipeIngredients  the list of ingredients to be added
     * @param  recipeId           the ID of the recipe to add the ingredients to
     * @return                    the updated recipe with the added ingredients
     */
    @Transactional
    public Recipe addIngredients(List<RecipeIngredient> recipeIngredients, Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        recipeIngredients.forEach(recipeIngredient -> {
            addIngredient(recipeIngredient, recipeId);
        });

        return recipe;
    }

    /**
     * Adds an ingredient to a recipe.
     *
     * @param  recipeIngredient  the ingredient to be added to the recipe
     * @param  recipeId          the ID of the recipe to add the ingredient to
     * @return                   the updated recipe with the added ingredient
     */
    @Transactional
    public Recipe addIngredient(RecipeIngredient recipeIngredient, Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        String ingredientName = recipeIngredient.getIngredient().getName();

        Ingredient ingredient;

        try {
            ingredient = ingredientService.getIngredientByName(ingredientName);
        } catch (Exception ex) {
            ingredient = ingredientService.createIngredient(new Ingredient(ingredientName));
        }

        RecipeIngredient addRecipeIngredient = new RecipeIngredient();


        addRecipeIngredient.setIngredient(ingredient);
        addRecipeIngredient.setQuantity(recipeIngredient.getQuantity());
        addRecipeIngredient.setUnit(recipeIngredient.getUnit());
        addRecipeIngredient.setRecipe(recipe);
        addRecipeIngredient.setRecipeIngredientId(new RecipeIngredientId(recipeId,
                recipeIngredient.getIngredient().getIngredientId()));

        recipe.getIngredients().add(addRecipeIngredient);
        return recipeRepository.save(recipe);
    }

    /**
     * Updates the ingredients of a recipe.
     *
     * @param  ingredients  the list of RecipeIngredient objects representing the new ingredients
     * @param  recipeId     the ID of the recipe to update the ingredients for
     * @throws NotFoundException  if the recipe with the given ID is not found
     */
    @Transactional
    public void updateIngredients(List<RecipeIngredient> ingredients, Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        List<RecipeIngredient> currIngredients = recipe.getIngredients();
        List<RecipeIngredient> remove = new ArrayList<>();

        currIngredients.forEach(ing -> {
            boolean found = false;
            for (RecipeIngredient ingredient : ingredients) {
                if (ing.getIngredient().getName().equals(ingredient.getIngredient().getName())) {
                    found = true;
                    ing.setQuantity(ingredient.getQuantity());
                    ing.setUnit(ingredient.getUnit());
                    ing.getIngredient().setName(ingredient.getIngredient().getName());
                    break;
                }
            }
            if (!found) {
                remove.add(ing);
            }
        });

        if (!remove.isEmpty()) {
            remove.forEach(currIngredients::remove);
        }

        recipe.setIngredients(currIngredients);
        recipeRepository.save(recipe);

        ingredients.forEach(ing -> {
            boolean found = false;
            for (RecipeIngredient recipeIngredient: currIngredients) {
                if (recipeIngredient.getIngredient().getName().equals(ing.getIngredient().getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                addIngredient(ing, recipeId);
            }
        });
    }

    /**
     * Creates a new recipe by saving it to the recipe repository.
     *
     * @param  recipe  the recipe to be created
     * @return         the created recipe
     */
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    /**
     * Updates an existing recipe with the provided ID using the information from the given Recipe object.
     *
     * @param  id      the ID of the recipe to update
     * @param  recipe  the Recipe object containing the updated information
     * @return         the updated Recipe object
     */
    @Transactional
    public Recipe updateRecipe(int id, Recipe recipe) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setInstructions(recipe.getInstructions());
        existingRecipe.setLastModifiedDate(LocalDate.now());
        existingRecipe.setTitle(recipe.getTitle());

        recipeRepository.save(existingRecipe);
        return existingRecipe;
    }

    /**
     * Deletes a recipe by its ID.
     *
     * @param  id  the ID of the recipe to delete
     * @return     void
     */
    public void deleteRecipe(int id) {
        recipeRepository.deleteById(id);
    }

}
