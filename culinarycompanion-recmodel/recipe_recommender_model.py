from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd
import joblib

# Load dataset
dataset_path = "recipes.csv"
recipes = pd.read_csv(dataset_path)

# Create a TF-IDF vectorizer
vectorizer = TfidfVectorizer(stop_words='english')

# Fit and transform the vectorizer on the ingredients column
X = vectorizer.fit_transform(recipes['ingredients'])

# Save the vectorizer
vectorizer_path = "tfidf_vectorizer.pkl"
joblib.dump(vectorizer, vectorizer_path)

# Save the model
model = { 'vectorizer': vectorizer, 'X': X }
joblib.dump(model, 'recipe_model.pkl')