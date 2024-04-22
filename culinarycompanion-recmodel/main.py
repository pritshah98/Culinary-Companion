from sklearn.metrics.pairwise import cosine_similarity
from flask import Flask, request, json
from flask_cors import CORS
import pandas as pd
import joblib
import os

# Load the TF-IDF vectorizer
model = joblib.load('./recipe_model.pkl')
vectorizer = model['vectorizer']
X = model['X']

# Load the dataset, source: https://www.kaggle.com/datasets/shuyangli94/food-com-recipes-and-user-interactions
dataset_path = "./recipes.csv"

recipes = pd.read_csv(dataset_path)
recipes.reset_index(drop=True, inplace=True)

def compute_similarity(input_ingredients, n=5):
    # Transform input ingredients using the vectorizer
    input_vec = vectorizer.transform([' '.join(input_ingredients)])
    
    # Calculate cosine similarity between input and all recipes
    similarity = cosine_similarity(X, input_vec)
    
    # Get top 5 recommendations
    sorted_indices = similarity.argsort(axis=0).flatten()[::-1].tolist()

    top_indices = sorted_indices[:n]

    if len(top_indices) < n:
        n = len(top_indices)

    top_recipes = recipes.iloc[top_indices[:n]]
    
    return top_recipes

app = Flask(__name__)
CORS(app)

@app.route('/', methods=['POST', 'GET'])
def recipe_recommender():
    if request.method == 'POST':
        input_ingredients = request.get_json()
        top_recipes = compute_similarity(input_ingredients)

        top_recipes = top_recipes.drop(columns=['contributor_id', 'nutrition', 'minutes', 'n_steps', 'n_ingredients', 'tags', 'id', 'submitted'])
        top_recipes = top_recipes.rename(columns={'name': 'title', 'steps': 'instructions'})
        recipes_formatted = top_recipes.to_dict(orient='records')

        response = json.dumps(recipes_formatted)

        recipe_data = json.loads(response)

        # Convert None values to empty strings
        for recipe in recipe_data:
            for key, value in recipe.items():
                if value is None or (isinstance(value, float) and value != value):
                    recipe[key] = ""
        
        modified_json_string = json.dumps(recipe_data)

        return modified_json_string
    else:
        return 'Invalid request'

if __name__ == '__main__':
    app.run(debug=True, port=os.getenv("PORT", default=5000))