import React from "react";
import withAuth from "@/security/withAuth";

const RecList = ({ recipes }) => {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-b from-[#2e026d] to-[#15162c]">
      <div className="container mx-auto px-4 py-8">
        <h2 className="text-3xl font-bold text-white mb-6 mt-16">
          Recipes (Sourced from Food.com)
        </h2>
        {recipes.map((recipe, index) => (
          <div key={index} className="bg-white rounded-lg shadow-md p-6 mb-6">
            <h3 className="text-xl font-semibold mb-2">{recipe.title}</h3>
            <p className="text-gray-700 mb-2">
              <span className="font-semibold">Description:</span>{" "}
              {recipe.description}
            </p>
            <p className="text-gray-700 mb-2">
              <span className="font-semibold">Ingredients:</span>{" "}
              {recipe.ingredients}
            </p>
            <p className="text-gray-700 mb-2">
              <span className="font-semibold">Instructions:</span>{" "}
              {recipe.instructions}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default withAuth(RecList);
