import React, { useState, useEffect } from "react";
import withAuth from "@/security/withAuth";
import { useAuth } from "@/security/AuthContext";
import {
  getUserIngredients,
  deleteUserIngredient,
  updateUserIngredients,
  addUserIngredients,
} from "./api/RecipesApi";
import { useRouter } from "next/router";

const UserIngredientsPage = () => {
  const [userIngredients, setUserIngredients] = useState([]);
  const [ingredients, setIngredients] = useState([
    { name: "", quantity: "", unit: "TEASPOON" },
  ]);
  const [editIngredients, setEditIngredients] = useState([
    { name: "", quantity: "", unit: "" },
  ]);
  const [editing, setEditing] = useState(false);
  const authContext = useAuth();
  const email = authContext.username;
  const router = useRouter();

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

  useEffect(() => {
    // Fetch user ingredients data from the API and set it to state
    getIngs();
  }, []);

  function getIngs() {
    getUserIngredients(email)
      .then((response) => {
        // console.log(response);
        setUserIngredients(response.data);
      })
      .catch((error) => {
        // console.log(error);
        alert("Unable to get your ingredients at this time");
      });
  }

  function goBack() {
    router.back();
  }

  function getRecs() {
    router.push("/recs");
  }

  function handleAddIngredients() {
    const invalidInputs = ingredients.some(
      (ingredient) =>
        !ingredient.name || !ingredient.quantity || !ingredient.unit
    );

    if (invalidInputs) {
      alert("Please fill in all fields for each ingredient.");
      return;
    }

    if (ingredients.length === 0) {
      alert("Please add atleast one ingredient.");
      return;
    }

    const batchIngredients = ingredients.map((ing) => {
      return {
        user: {
          email: email,
        },
        ingredient: {
          name: ing.name,
        },
        quantity: ing.quantity,
        unit: ing.unit,
      };
    });

    addUserIngredients(email, batchIngredients)
      .then((response) => {})
      .catch((error) => {
        alert("Unable to add ingredients at this time");
      })
      .finally(() => {
        router.reload();
      });
  }

  const handleRemoveIngredient = (ingredientName) => {
    deleteUserIngredient(email, ingredientName)
      .then((response) => {
        router.reload();
      })
      .catch((error) => {
        alert("Unable to delete ingredient at this time");
      });
  };

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
    // console.log(value);
    const updatedIngredients = [...ingredients];
    updatedIngredients[index][field] = value;
    setIngredients(updatedIngredients);
  };

  const handleEditIngredientChange = (index, field, value) => {
    const updatedIngredients = [...editIngredients];
    updatedIngredients[index][field] = value;
    setEditIngredients(updatedIngredients);
  };

  const handleSubmitEditIngredients = () => {
    updateUserIngredients(email, editIngredients)
      .then((response) => {})
      .catch((error) => {
        alert("Unable to update your ingredients at this time");
      });

    router.reload();
  };

  const initEditIngredients = () => {
    if (!editing) {
      setEditIngredients(userIngredients);
      setEditing(true);
    } else {
      setEditIngredients([]);
      setEditing(false);
    }
  };

  const stopEditing = () => {
    setEditing(false);
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-b from-[#2e026d] to-[#15162c]">
      <div className="container mx-auto px-4 py-8">
        <h2 className="text-3xl font-bold text-white mb-6">Your Ingredients</h2>
        <div className="mb-6">
          <button
            className="bg-gray-600 text-white px-4 py-2 rounded mr-2 hover:bg-gray-700"
            onClick={goBack}
          >
            Go Back
          </button>
          <button
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
            onClick={getRecs}
          >
            Get Recipe Recommendations
          </button>
          {!editing && userIngredients.length > 0 && (
            <button
              className="bg-green-500 text-white px-4 py-2 rounded ml-2 hover:bg-green-600 mb-6"
              onClick={initEditIngredients}
            >
              Edit Ingredients
            </button>
          )}
          <h4 className="text-white italic">
            Recipe recommendations are based on your list of ingredients
          </h4>
        </div>
        {editing ? (
          <div className="mb-6 w-1/2 bg-gray-800 p-4 rounded">
            <label htmlFor="ingredients" className="text-white">
              Ingredients:
            </label>
            {editIngredients.map((ingredient, index) => (
              <div key={index} className="ingredientGroup">
                <label htmlFor="name" className="text-white mr-10 mb-2">
                  {ingredient.ingredient.name}
                </label>
                <input
                  type="text"
                  value={ingredient.quantity}
                  placeholder="Quantity"
                  onChange={(e) =>
                    handleEditIngredientChange(
                      index,
                      "quantity",
                      e.target.value.replace(/[^0-9.]/g, "")
                    )
                  }
                  className="w-1/4 px-3 py-2 mr-2 mb-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
                />
                <select
                  className="w-1/4 px-3 py-2 mr-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
                  value={ingredient.unit}
                  placeholder="Unit"
                  onChange={(e) =>
                    handleEditIngredientChange(index, "unit", e.target.value)
                  }
                >
                  {units.map((unit) => (
                    <option key={unit} value={unit}>
                      {unit}
                    </option>
                  ))}
                </select>
              </div>
            ))}
            <div className="flex justify-between">
              <button
                type="submit"
                className="px-6 py-3 text-white bg-blue-500 rounded hover:bg-blue-600 focus:outline-none mt-3"
                onClick={handleSubmitEditIngredients}
              >
                Submit
              </button>
              <button
                type="submit"
                className="px-6 py-3 text-white bg-blue-500 rounded hover:bg-blue-600 focus:outline-none mt-3"
                onClick={stopEditing}
              >
                Cancel
              </button>
            </div>
          </div>
        ) : (
          <table className="table text-white w-full mt-6 text-center border border-gray-300 mb-6">
            <thead>
              <tr>
                {userIngredients.length > 0 && <th>Name</th>}
                {userIngredients.length > 0 && <th>Quantity</th>}
                {userIngredients.length > 0 && <th>Unit</th>}
              </tr>
            </thead>
            <tbody>
              {userIngredients.map((ingredient, index) => (
                <tr key={index}>
                  <td>{ingredient.ingredient.name}</td>
                  <td>{ingredient.quantity}</td>
                  <td>{ingredient.unit}</td>
                  <td>
                    <button
                      className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 focus:outline-none mb-2"
                      onClick={() =>
                        handleRemoveIngredient(ingredient.ingredient.name)
                      }
                    >
                      Remove
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        <div className="">
          <label
            htmlFor="ingredients"
            className="block mb-2 text-lg font-bold text-white"
          >
            Ingredients:
          </label>
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
                className="w-1/4 px-3 py-2 mr-2 leading-tight border border-gray-300 rounded appearance-none focus:outline-none focus:shadow-outline"
                value={ingredient.unit}
                placeholder="Unit"
                onChange={(e) =>
                  handleIngredientChange(index, "unit", e.target.value)
                }
              >
                {units.map((unit) => (
                  <option key={unit} value={unit}>
                    {unit}
                  </option>
                ))}
              </select>
              <button
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 focus:outline-none mb-2"
                onClick={() => removeIngredientField(index)}
              >
                Remove
              </button>
            </div>
          ))}
          <div className="mb-7">
            <button
              type="button"
              className="bg-green-500 text-white px-4 py-2 rounded mt-4 hover:bg-green-600 mr-2"
              onClick={addIngredientField}
            >
              Add Ingredient
            </button>
            <button
              type="submit"
              className="px-6 py-3 text-white bg-blue-500 rounded hover:bg-blue-600 focus:outline-none"
              onClick={handleAddIngredients}
            >
              Submit
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default withAuth(UserIngredientsPage);
