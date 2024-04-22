import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import { getRecipe, getRecipeRating } from "../api/RecipesApi";
import withAuth from "@/security/withAuth";
import RecipeDetails from "@/components/RecipeDetails";
import Head from "next/head";

const Viewrecipe = () => {
  const router = useRouter();
  const recipeId = router.query.id;

  const [recipe, setRecipe] = useState(null);

  useEffect(() => {
    const fetchRecipeData = async () => {
      try {
        const response = await getRecipe(recipeId);
        setRecipe(response.data);
        getRecipeRatingData(response.data);
      } catch (error) {
        alert("Unable to get recipe at this time");
      }
    };

    if (recipeId) {
      fetchRecipeData();
    }
  }, [recipeId]);

  function updateRecipeWithRating(rating) {
    setRecipe((prevRecipe) => {
      const updatedRecipe = {
        ...prevRecipe,
        rating: rating[0],
        ratingCount: rating[1],
      };
      return updatedRecipe;
    });
  }

  function getRecipeRatingData(recipe) {
    getRecipeRating(recipe.recipeId)
      .then((response) => {
        updateRecipeWithRating(response.data);
      })
      .catch((error) => {
        alert("Unable to get recipe rating at this time");
      });
  }

  if (!recipeId) {
    return <div>Loading...</div>;
  }

  if (!recipe) {
    return <div>Recipe not found</div>;
  }

  return (
    <>
      <Head>
        <title>{recipe.title}</title>
      </Head>
      <RecipeDetails recipe={recipe} />
    </>
  );
};

export default withAuth(Viewrecipe);
