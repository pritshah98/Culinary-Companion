import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import RecipeRating from "./RecipeRating";
import {
  rateRecipe,
  getUserRatingForRecipe,
  updateRecipeRating,
} from "@/pages/api/RecipesApi";
import { useAuth } from "@/security/AuthContext";
import withAuth from "@/security/withAuth";

const RecipeDetails = ({ recipe }) => {
  const router = useRouter();
  const authContext = useAuth();
  const [currRating, setCurrRating] = useState({
    comment: null,
    rating: 0,
    ratingId: null,
  });
  const [loading, setLoading] = useState(true);
  const userEmail = authContext.username;

  useEffect(() => {
    getUserRatingForRecipe(recipe.recipeId, userEmail)
      .then((response) => {
        setCurrRating(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.log(error);
        setLoading(false);
      });
  }, [currRating.rating]);

  function goBack() {
    router.back();
  }

  const handleRatingChange = (rating) => {
    const ratingData = {
      rating: rating,
    };

    if (currRating === null || currRating.rating === 0 || !currRating.rating) {
      rateRecipe(ratingData, recipe.recipeId, userEmail)
        .then((response) => {
          // Handle success
        })
        .catch((error) => {
          console.log(error);
        });
    } else {
      updateRecipeRating(currRating.ratingId, ratingData)
        .then((response) => {
          // Handle success
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  function doesUserHaveRating() {
    return currRating.rating > 0;
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#2e026d] to-[#15162c] flex flex-col items-center justify-center text-white">
      <div className="container mx-auto p-8">
        {loading && <p>Loading...</p>}
        {!loading && (
          <>
            <div className="flex flex-horizontal items-end">
              <h1 className="text-3xl mb-4 mr-4">{recipe.title}</h1>
              <h3 className="text-l mb-4">Uploaded by: {recipe.userEmail}</h3>
            </div>
            <p className="mb-2">{recipe.description}</p>
            <p className="mb-2">
              Rating: {recipe.rating ? recipe.rating : 0}/5, Total Votes:{" "}
              {recipe.ratingCount ? recipe.ratingCount : 0}
            </p>
            <h3 className="text-xl font-bold mb-2">Ingredients:</h3>
            <ul className="list-disc ml-8 mb-4">
              {recipe.ingredients.map((ingredient, index) => (
                <li key={index}>
                  {ingredient.ingredient.name}: {ingredient.quantity}{" "}
                  {ingredient.unit}
                </li>
              ))}
            </ul>
            <h3 className="text-xl font-bold mb-2">Instructions:</h3>
            <p className="mb-4">{recipe.instructions}</p>
            <div className="mb-8">
              <h2 className="text-2xl mb-2">Rate this Recipe:</h2>
              <RecipeRating
                initialRating={doesUserHaveRating() ? currRating.rating : 0}
                onRatingChange={handleRatingChange}
              />
            </div>
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 focus:outline-none"
              onClick={goBack}
            >
              Go Back
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default withAuth(RecipeDetails);
