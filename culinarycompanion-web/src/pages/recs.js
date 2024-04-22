import React, { useEffect, useState } from "react";
import { getUserIngredients, getRecipeRecommendations } from "./api/RecipesApi";
import RecList from "@/components/RecList";
import withAuth from "@/security/withAuth";
import { useAuth } from "@/security/AuthContext";
import { useRouter } from "next/router";

const Recs = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const authContext = useAuth();
  const router = useRouter();
  const email = authContext.username;

  useEffect(() => {
    displayRecs();
  }, []);

  function displayRecs() {
    getUserIngredients(email)
      .then((response) => {
        const userIngs = response.data;
        getRecs(userIngs);
      })
      .catch((error) => {
        alert("Unable to get your ingredients at this time");
      });
  }

  function getRecs(userIngs) {
    const names = userIngs.map((ing) => ing.ingredient.name);
    getRecipeRecommendations(names)
      .then((response) => {
        setRecipes(response.data);
      })
      .catch((error) => {
        alert("Unable to get recommendations at this time");
      })
      .finally(() => setLoading(false));
  }

  function goBack() {
    router.back();
  }

  if (recipes.length === 0 && loading === false) {
    return (
      <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-b from-[#2e026d] to-[#15162c]">
        <button
          className="absolute top-8 left-8 px-6 py-3 text-white bg-gray-500 rounded hover:bg-gray-600 focus:outline-none "
          onClick={goBack}
        >
          Go Back
        </button>
        <p className="text-white font-bold text-3xl">
          No recommendations found at this time
        </p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-b from-[#2e026d] to-[#15162c]">
      <button
        className="absolute top-8 left-8 px-6 py-3 text-white bg-gray-500 rounded hover:bg-gray-600 focus:outline-none "
        onClick={goBack}
      >
        Go Back
      </button>
      <RecList recipes={recipes} />
    </div>
  );
};

export default withAuth(Recs);
