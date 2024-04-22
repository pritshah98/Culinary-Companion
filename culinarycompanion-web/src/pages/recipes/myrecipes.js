import React, { useEffect, useState } from "react";
import {
  getUsersRecipes,
  deleteRecipe,
  getRecipeRating,
  deleteRatings,
} from "../api/RecipesApi";
import withAuth from "@/security/withAuth";
import Head from "next/head";
import { useRouter } from "next/router";
import Link from "next/link";
import { useAuth } from "@/security/AuthContext";

const Myrecipes = () => {
  const [recipes, setRecipes] = useState([]);

  const router = useRouter();

  const authContext = useAuth();
  const username = authContext.username;

  useEffect(() => refreshRecipes(), []);

  function refreshRecipes() {
    getUsersRecipes(username)
      .then((response) => {
        setRecipes(response.data);
        getRecipeRatings(response.data);
      })
      .catch((error) => {
        alert("Unable to get your recipes at this time");
      });
  }

  function updateRecipesWithRating(recipe, rating) {
    setRecipes((prevRecipes) => {
      const index = prevRecipes.findIndex(
        (r) => r.recipeId === recipe.recipeId
      );
      const updatedRecipe = {
        ...prevRecipes[index],
        rating: rating[0],
        ratingCount: rating[1],
      };
      const updatedRecipes = [...prevRecipes];
      updatedRecipes[index] = updatedRecipe;
      return updatedRecipes;
    });
  }

  function getRecipeRatings(recipes) {
    recipes.forEach((recipe) => {
      getRecipeRating(recipe.recipeId)
        .then((response) => {
          updateRecipesWithRating(recipe, response.data);
        })
        .catch((error) => {
          alert("Unable to get your recipe ratings at this time");
        });
    });
  }

  function addNewRecipe() {
    router.push("/recipes/add");
  }

  function updateRecipe(recipeId) {
    router.push(`/recipes/update/${recipeId}`);
  }

  function viewAllRecipes() {
    router.push("/recipes/all");
  }

  function viewMyIngredients() {
    router.push("/myingredients");
  }

  function deleteRecipeRatings(recipeId) {
    deleteRatings(recipeId)
      .then((response) => {
        deleteUserRecipe(recipeId);
      })
      .catch((error) => {
        alert("Unable to delete the recipe rating at this time");
      });
  }

  function deleteUserRecipe(recipeId) {
    deleteRecipe(recipeId)
      .then((response) => {
        refreshRecipes();
      })
      .catch((error) => {
        alert("Unable to delete the recipe at this time");
      });
  }

  return (
    <div>
      <Head>
        <title>My Recipes</title>
      </Head>
      <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-b from-[#2e026d] to-[#15162c]">
        <div className="container mx-auto px-4 py-4 md:py-8">
          <h1 className="text-3xl font-bold text-white mb-6">
            View Your Uploaded Recipes!
          </h1>
          <div className="flex items-center space-x-4 mb-4">
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded mr-2 hover:bg-blue-600"
              onClick={addNewRecipe}
            >
              Add New Recipe
            </button>
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded mr-2 hover:bg-blue-600"
              onClick={viewAllRecipes}
            >
              View All Recipes
            </button>
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded mr-2 hover:bg-blue-600"
              onClick={viewMyIngredients}
            >
              View My Ingredients
            </button>
            <Link href="/">
              <button className="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700">
                Home
              </button>
            </Link>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {recipes.map((recipe) => (
              <div
                key={recipe.recipeId}
                className="p-4 bg-white rounded shadow-md"
              >
                <h2 className="text-xl font-bold mb-2">{recipe.title}</h2>
                <p className="text-gray-700">
                  {recipe.description} || Uploaded by: {recipe.userEmail}
                </p>
                <p className="text-gray-700">
                  Rating: {recipe.rating ? recipe.rating : 0}/5, Total Votes:{" "}
                  {recipe.ratingCount ? recipe.ratingCount : 0}
                </p>
                <Link href={`/recipes/${recipe.recipeId}`}>
                  <button className="bg-green-500 text-white px-4 py-2 rounded mt-4 mr-2 hover:bg-green-600">
                    View Details
                  </button>
                </Link>
                <button
                  className="bg-blue-500 text-white px-4 py-2 rounded mr-2 hover:bg-blue-600 focus:outline-none"
                  onClick={() => updateRecipe(recipe.recipeId)}
                >
                  Update Recipe
                </button>
                <button
                  className="bg-red-500 text-white px-4 py-2 rounded mr-2 hover:bg-red-600 focus:outline-none"
                  onClick={() => deleteRecipeRatings(recipe.recipeId)}
                >
                  Delete Recipe
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default withAuth(Myrecipes);
