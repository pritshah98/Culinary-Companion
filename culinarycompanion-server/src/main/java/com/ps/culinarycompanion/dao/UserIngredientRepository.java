package com.ps.culinarycompanion.dao;

import com.ps.culinarycompanion.useringredients.UserIngredient;
import com.ps.culinarycompanion.useringredients.UserIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, UserIngredientId> {
}
