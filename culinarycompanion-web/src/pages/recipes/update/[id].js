import React, { useEffect, useState } from "react";
import withAuth from "@/security/withAuth";
import Head from "next/head";
import {
  getRecipe,
  updateRecipe,
  updateIngredients,
} from "../../api/RecipesApi";
import { useRouter } from "next/router";

function UpdateRecipeForm() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [instructions, setInstructions] = useState("");
  const [ingredients, setIngredients] = useState([
    { name: "", quantity: "", unit: "" },
  ]);

  const [recipe, setRecipe] = useState(null);

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
  ];

  const router = useRouter();

  const recipeId = router.query.id;

  useEffect(() => {
    const fetchRecipeData = async () => {
      try {
        const response = await getRecipe(recipeId);

        setRecipe(response.data);

        setDescription(response.data.description);
        setTitle(response.data.title);
        setInstructions(response.data.instructions);

        const getIngredients = [];
        response.data.ingredients.forEach((ingredient) => {
          getIngredients.push({
            name: ingredient.ingredient.name,
            quantity: ingredient.quantity,
            unit: ingredient.unit,
          });
        });

        setIngredients(getIngredients);
      } catch (error) {
        alert("Unable to get recipe at this time");
      }
    };

    if (recipeId) {
      fetchRecipeData();
    }
  }, [recipeId]);

  const addIngredientField = () => {
    setIngredients([
      ...ingredients,
      { name: "", quantity: "", unit: "TEASPOON" },
    ]);
  };

  const removeIngredientField = (index, e) => {
    e.preventDefault();
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

    const updatedRecipe = {
      title: title,
      description: description,
      instructions: instructions,
    };

    const updatedIngredientList = ingredients.map((ing) => {
      return {
        quantity: ing.quantity,
        unit: ing.unit,
        ingredient: {
          name: ing.name,
        },
      };
    });

    updateRecipe(recipeId, updatedRecipe)
      .then((response) => {

        updateIngredients(updatedIngredientList, response.data.recipeId)
          .then((response) => {
          })
          .catch((error) => {
            alert("Unable to update ingredients at this time");
          });

        router.push("/recipes/all");
      })
      .catch((error) => {
        alert("Unable to update recipe at this time");
      });
  };

  function goBack() {
    router.back();
  }

  if (!recipeId) {
    return <div>Loading...</div>;
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#2e026d] to-[#15162c] flex flex-col items-center justify-center text-gray-700">
      <Head>
        <title>Update Recipe</title>
      </Head>
      <h1 className="text-3xl font-bold text-white mb-6">Update Recipe</h1>
      <form
        className="max-w-3xl w-full p-6 bg-white rounded-lg shadow-lg"
        onSubmit={handleSubmit}
      >
        <div className="mb-6">
          <label className="block mb-2 text-sm font-bold text-gray-700">
            Title:
          </label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full px-3 py-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="mb-6">
          <label className="block mb-2 text-sm font-bold">Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            className="w-full px-3 py-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="mb-6">
          <label className="block mb-2 text-sm font-bold">Instructions:</label>
          <textarea
            value={instructions}
            onChange={(e) => setInstructions(e.target.value)}
            className="w-full px-3 py-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="mb-6">
          <label className="block mb-2 text-sm font-bold">Ingredients:</label>
          {ingredients.map((ingredient, index) => (
            <div key={index} className="ingredientGroup">
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
                className="px-3 py-2 leading-tight text-white bg-red-500 rounded focus:outline-none hover:bg-red-600"
                onClick={(e) => removeIngredientField(index, e)}
              >
                Remove
              </button>
            </div>
          ))}
          <button
            type="button"
            className="px-3 py-2 mt-2 leading-tight text-white bg-green-500 rounded focus:outline-none hover:bg-green-600"
            onClick={addIngredientField}
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

export default withAuth(UpdateRecipeForm);
