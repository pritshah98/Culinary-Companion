-- Dummy data for Users table
INSERT INTO user_details (User_id, Email, Join_Date)
VALUES
    (100, 'user1@example.com', '2024-03-28 10:00:00'),
    (200, 'user2@example.com', '2024-03-28 10:00:00'),
    (300, 'user3@example.com', '2024-03-28 10:00:00');

-- Dummy data for Ingredients table
INSERT INTO ingredient_details (ingredient_id, Name)
VALUES
    (10, 'Flour'),
    (20, 'Sugar'),
    (30, 'Salt'),
    (40, 'Eggs'),
    (50, 'Milk');

-- Dummy data for Recipes table
INSERT INTO recipe_details (recipe_id, Title, Description, Instructions, user_email, created_date, last_modified_date)
VALUES
    (10, 'Chocolate Cake', 'Delicious chocolate cake recipe', '1. Preheat oven to 350°F. 2. Mix flour, sugar, cocoa powder, and baking soda. ...', 'user1@example.com', '2024-03-28 10:00:00', '2024-03-28 10:00:00'),
    (20, 'Pasta Carbonara', 'Classic pasta dish with bacon and eggs', '1. Cook pasta according to package instructions. 2. Fry bacon until crispy. ...', 'user2@example.com', '2024-03-28 10:00:00', '2024-03-28 10:00:00'),
    (30, 'Vegetable Stir Fry', 'Healthy stir fry with assorted vegetables', '1. Heat oil in a pan. 2. Add chopped vegetables and stir-fry until tender. ...', 'user3@example.com', '2024-03-28 10:00:00', '2024-03-28 10:00:00');

-- Dummy data for RecipeIngredients table
INSERT INTO recipe_ingredients (recipe_id, ingredient_id, Quantity, Unit)
VALUES
    (10, 10, 250, 2), -- Chocolate Cake: 250g Flour
    (10, 20, 200, 3), -- Chocolate Cake: 200g Sugar
    (10, 50, 250, 5), -- Chocolate Cake: 250ml Milk
    (20, 40, 200, 1), -- Pasta Carbonara: 200g Eggs
    (20, 50, 250, 6), -- Pasta Carbonara: 250ml Milk
    (30, 10, 300, 8), -- Vegetable Stir Fry: 300g Flour
    (30, 30, 10, 10); -- Vegetable Stir Fry: 10g Salt

-- Dummy data for Ratings table
INSERT INTO rating_details (rating_id, recipe_id, user_id, Rating, Comment)
VALUES
    (100, 10, 200, 4.5, 'Great cake recipe!'),
    (200, 20, 300, 5.0, 'Best pasta ever!'),
    (300, 30, 100, 4.0, 'Healthy and delicious!');

-- Dummy data for UserIngredients table
INSERT INTO user_ingredients (user_id, ingredient_id, quantity, unit)
VALUES
    (100, 10, 1.5, 3),
    (100, 20, 1.0, 6),
    (200, 50, 6, 4),
    (300, 40, 0.5, 1),
    (300, 30, 1.5, 8);
