import { apiClient } from "./ApiClient";

export const getAllRecipes = async () => {
  return apiClient.get("/recipes");
};

export const createRecipe = async (recipe) => {
  return apiClient.post("/recipes", recipe);
};

export const addIngredient = async (ingredient, recipeId) => {
  return apiClient.post(`/recipes/${recipeId}/ingredients`, ingredient);
};

export const addIngredients = async (ingredients, recipeId) => {
  return apiClient.post(`/recipes/${recipeId}/ingredients/batch`, ingredients);
};

export const getRecipe = async (recipeId) => {
  return apiClient.get(`/recipes/${recipeId}`);
};

export const getUsersRecipes = async (username) => {
  return apiClient.get(`/recipes/user/${username}`);
};

export const deleteRecipe = async (recipeId) => {
  return apiClient.delete(`/recipes/${recipeId}`);
};

export const updateRecipe = async (recipeId, recipe) => {
  return apiClient.put(`/recipes/${recipeId}`, recipe);
};

export const updateIngredients = async (ingredients, recipeId) => {
  return apiClient.put(`/recipes/${recipeId}/ingredients`, ingredients);
};

export const getRecipeRating = async (recipeId) => {
  return apiClient.get(`/recipes/${recipeId}/rating`);
};

export const rateRecipe = async (rating, id, email) => {
  return apiClient.post(`/ratings/add/${id}/${email}`, rating);
};

export const getUserRatingForRecipe = async (recipeId, username) => {
  return apiClient.get(`/ratings/${recipeId}/${username}`);
};

export const updateRecipeRating = async (ratingId, rating) => {
  return apiClient.put(`/recipes/ratings/${ratingId}/update`, rating);
};

export const getUserIngredients = async (username) => {
  return apiClient.get(`/myingredients/${username}`);
};

export const addUserIngredient = async (username, ingredient) => {
  return apiClient.post(`/myingredients/${username}`, ingredient);
};

export const addUserIngredients = async (username, ingredients) => {
  return apiClient.post(`/myingredients/batch/${username}`, ingredients);
};

export const deleteUserIngredient = async (username, ingredient) => {
  return apiClient.delete(`/myingredients/${username}/${ingredient}`);
};

export const updateUserIngredients = async (username, ingredients) => {
  return apiClient.put(`/myingredients/${username}`, ingredients);
};

export const getRecipeRecommendations = async (ingredients) => {
  return apiClient.post("/recipes/recommendations", ingredients);
};

export const deleteRatings = async (recipeId) => {
  return apiClient.delete(`/recipes/${recipeId}/ratings`);
};
