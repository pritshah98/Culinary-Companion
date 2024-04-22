package com.ps.culinarycompanion.dao;

import com.ps.culinarycompanion.recipes.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}
