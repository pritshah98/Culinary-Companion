import React, { useState } from "react";
import withAuth from "@/security/withAuth";
import Head from "next/head";
import { useAuth } from "@/security/AuthContext";
import { createRecipe, addIngredients } from "../api/RecipesApi";
import { useRouter } from "next/router";

function AddRecipeForm() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [instructions, setInstructions] = useState("");
  const [ingredients, setIngredients] = useState([
    { name: "", quantity: "", unit: "TEASPOON" },
  ]);
  const authContext = useAuth();
  const router = useRouter();
  const username = authContext.username;

  const units = [
    "TEASPOON",
    "TABLESPOON",
    "CUP",
    "FLUID_OUNCE",
    "PINT",
    "QUART",
    "GALLON",
    "MILLILITER",
    "LITER",
    "GRAM",
    "KILOGRAM",
    "OUNCE",
    "POUND",
    "COUNT",
  ];

  const addIngredientField = () => {
    setIngredients([
      ...ingredients,
      { name: "", quantity: "", unit: "TEASPOON" },
    ]);
  };

  const removeIngredientField = (index) => {
    const newIngredients = [...ingredients];
    newIngredients.splice(index, 1);
    setIngredients(newIngredients);
  };

  const handleIngredientChange = (index, field, value) => {
    const updatedIngredients = [...ingredients];
    updatedIngredients[index][field] = value;
    setIngredients(updatedIngredients);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const newRecipe = {
      title,
      description,
      instructions,
      userEmail: username,
    };

    const invalidInputs = !title || !description || !instructions;

    if (invalidInputs) {
      alert("Please fill in all fields for the recipe.");
      return;
    }

    const invalidIngredientInputs = ingredients.some(
      (ingredient) =>
        !ingredient.name || !ingredient.quantity || !ingredient.unit
    );

    if (invalidIngredientInputs) {
      alert("Please fill in all fields for each ingredient.");
      return;
    }

    if (ingredients.length === 0) {
      alert("Please add atleast one ingredient.");
      return;
    }

    createRecipe(newRecipe)
      .then((response) => {
        const batchIngredients = ingredients.map((item) => {
          return {
            quantity: item.quantity,
            unit: item.unit,
            ingredient: {
              name: item.name,
            },
          };
        });

        addIngredients(batchIngredients, response.data.recipeId)
          .then((response) => {
            // console.log(response);
          })
          .catch((error) => {
            alert("Unable to add ingredients to the recipe at this time");
          })
          .finally(() => {
            router.push("/recipes/all");
          });
      })
      .catch((error) => {
        alert("Unable to add the recipe at this time");
      });
  };

  function goBack() {
    router.back();
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#2e026d] to-[#15162c] flex flex-col items-center justify-center text-gray-700">
      <Head>
        <title>Add Recipe</title>
      </Head>
      <h1 className="text-3xl font-bold mb-6 text-white">Add Recipe</h1>
      <form
        className="max-w-2xl w-full p-6 bg-white rounded-lg shadow-lg"
        onSubmit={handleSubmit}
      >
        <div className="mb-6">
          <label
            htmlFor="title"
            className="block mb-2 text-sm font-bold text-gray-700"
          >
            Title:
          </label>
          <input
            type="text"
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full px-3 py-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="mb-6">
          <label htmlFor="description" className="block mb-2 text-sm font-bold">
            Description:
          </label>
          <textarea
            value={description}
            placeholder="Description"
            onChange={(e) => setDescription(e.target.value)}
            className="w-full px-3 py-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="mb-6">
          <label
            htmlFor="instructions"
            className="block mb-2 text-sm font-bold"
          >
            Instructions:
          </label>
          <textarea
            value={instructions}
            placeholder="Instructions"
            onChange={(e) => setInstructions(e.target.value)}
            className="w-full px-3 py-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="mb-6">
          <label htmlFor="ingredients" className="block mb-2 text-sm font-bold">
            Ingredients:
          </label>
          {ingredients.map((ingredient, index) => (
            <div key={index} className="flex items-center mb-2">
              <input
                type="text"
                value={ingredient.name}
                placeholder="Name"
                onChange={(e) =>
                  handleIngredientChange(index, "name", e.target.value)
                }
                className="w-1/3 px-3 py-2 mr-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
              />
              <input
                type="text"
                value={ingredient.quantity}
                placeholder="Quantity"
                onChange={(e) =>
                  handleIngredientChange(
                    index,
                    "quantity",
                    e.target.value.replace(/[^0-9.]/g, "")
                  )
                }
                className="w-1/4 px-3 py-2 mr-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
              />
              <select
                value={ingredient.unit}
                onChange={(e) =>
                  handleIngredientChange(index, "unit", e.target.value)
                }
                className="w-1/4 px-3 py-2 mr-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
              >
                {units.map((unit) => (
                  <option key={unit} value={unit}>
                    {unit}
                  </option>
                ))}
              </select>
              <button
                type="button"
                onClick={() => removeIngredientField(index)}
                className="px-3 py-2 leading-tight text-white bg-red-500 rounded focus:outline-none hover:bg-red-600"
              >
                Remove
              </button>
            </div>
          ))}
          <button
            type="button"
            onClick={addIngredientField}
            className="px-3 py-2 leading-tight text-white bg-green-500 rounded focus:outline-none hover:bg-green-600"
          >
            Add Ingredient
          </button>
        </div>
        <div className="flex justify-between">
          <button
            type="submit"
            className="px-6 py-3 text-white bg-blue-500 rounded hover:bg-blue-600 focus:outline-none"
          >
            Submit
          </button>
          <button
            type="button"
            onClick={goBack}
            className="px-6 py-3 text-white bg-gray-500 rounded hover:bg-gray-600 focus:outline-none"
          >
            Go Back
          </button>
        </div>
      </form>
    </div>
  );
}

export default withAuth(AddRecipeForm);
