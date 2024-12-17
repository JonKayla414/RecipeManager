/**
 * Package: com.jkp.recipe_manager
 * 
 * The RecipeStorage class is responsible for managing the persistence and retrieval of recipe data
 * in the Recipe Manager application. It provides functionality for loading, saving, and updating 
 * recipes from external storage (e.g., files), ensuring that recipe information is preserved between 
 * application sessions.
 * 
 * <p>Features:</p>
 * - Loads recipes from storage (e.g., JSON files).
 * - Saves updated or new recipes to storage.
 * - Provides methods for retrieving all recipes or a specific recipe by its name.
 * - Handles the conversion between recipe objects and their storage representations (e.g., serialization).
 * 
 * <p>Usage:</p>
 * The RecipeStorage class should be used whenever you need to load or save recipes. The typical usage 
 * involves calling the appropriate methods to interact with stored recipe data:
 * <pre>{@code
 * RecipeStorage.loadAllRecipes(filePath); // Load all recipes from a file
 * RecipeStorage.saveRecipe(recipe, filePath); // Save a single recipe
 * }</pre>
 * The methods are designed to work with the Recipe and RecipeCollection classes to ensure consistent 
 * data management.
 * 
 * <p>Dependencies:</p>
 * - Recipe.java
 * - RecipeCollection.java
 * 
 * <p>Notes:</p>
 * - The class uses file-based storage (e.g., JSON) for recipes. You can extend the class to support 
 *   additional storage formats if necessary.
 * - The RecipeStorage class assumes that recipe data is serialized in a specific format (e.g., JSON), 
 *   so ensure consistency in the format used.
 * 
 * @author  Jon-Kayla Pointer
 * @version 1.0.0
 */


package com.jkp.recipe_manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeStorage {

    /**
     * Loads all recipes from JSON files in the specified directory and returns them as a {@link RecipeCollection}.
     * This method scans the directory for all `.json` files, attempts to deserialize each one into a {@link Recipe} 
     * object, and adds it to the collection. Any errors encountered during the file loading or parsing process are logged 
     * to the console, and the method continues loading other files.
     * 
     * <p>Usage:</p>
     * To load all recipes from a directory, call this method with the target directory path:
     * <pre>{@code
     * RecipeCollection collection = loadAllRecipes("/path/to/recipes/directory");
     * }</pre>
     *
     * @param directoryPath The path to the directory containing the JSON recipe files.
     * @return A {@link RecipeCollection} containing all the loaded recipes.
     * @throws IOException If there is an error reading the directory or loading a file.
     */

    public static RecipeCollection loadAllRecipes(String directoryPath) throws IOException {
        RecipeCollection collection = new RecipeCollection();
        
        // Get all JSON files in the directory
        try (var stream = Files.walk(Paths.get(directoryPath))) {
            stream.filter(Files::isRegularFile)
                  .filter(path -> path.toString().endsWith(".json"))
                  .forEach(path -> {
                      try {
                          Recipe recipe = loadRecipe(path);
                          collection.addRecipe(recipe);
                      } catch (IOException | IllegalArgumentException e) { // Handle both IO and parsing issues
                          System.err.println("Error loading recipe from " + path + ": " + e.getMessage());
                      }
                  });
        } catch (IOException e) {
            System.err.println("Failed to access directory " + directoryPath + ": " + e.getMessage());
            throw e;
        }
        return collection;
    }

    // Load a single recipe from a JSON file
    public static Recipe loadRecipe(Path filePath) throws IOException {
        String data = new String(Files.readAllBytes(filePath));
        JSONObject jsonObject = new JSONObject(data);
        JSONObject recipeJson = jsonObject.getJSONObject("recipe");
        String name = recipeJson.getString("name");
        

        return Recipe.fromJson(recipeJson);
    }

    /**
     * Saves the given {@link Recipe} object to a file in the specified directory.
     * The recipe is serialized into a JSON format, including details such as name, 
     * ingredients, instructions, servings, prep time, and cook time. The file name 
     * is sanitized by replacing non-alphanumeric characters with underscores.
     * 
     * <p>Usage:</p>
     * To save a recipe, call this method with the {@code Recipe} object to be saved 
     * and the target directory path:
     * <pre>{@code
     * saveRecipe(recipe, "/path/to/directory");
     * }</pre>
     *
     * @param recipe The {@link Recipe} object to be saved.
     * @param directoryPath The path to the directory where the recipe file should be stored.
     * @throws IOException If an error occurs during the file writing process.
     */
    public static void saveRecipe(Recipe recipe, String directoryPath) throws IOException {
            String fileName = recipe.getName().replaceAll("[^a-zA-Z0-9]", "_") + ".json"; // Sanitize file name
            Path filePath = Paths.get(directoryPath, fileName);
            
            JSONObject recipeJson = new JSONObject();
            JSONObject completeJson = new JSONObject();

            recipeJson.put("name", recipe.getName());
            recipeJson.put("ingredients", new JSONArray());
            for (Ingredient ingredient : recipe.getIngredients()) {
                recipeJson.getJSONArray("ingredients").put(ingredientToJson(ingredient));
            }
            recipeJson.put("instructions", new JSONArray(recipe.getInstructions()));
            recipeJson.put("servings", recipe.getServings());
            recipeJson.put("prep_time", recipe.getPrepTime());
            recipeJson.put("cook_time", recipe.getCookTime());
            completeJson.put("recipe",recipeJson);
            Files.write(filePath, completeJson.toString(2).getBytes());
    }

    /**
     * Converts an {@link Ingredient} object into a {@link JSONObject} for serialization.
     * This method takes an ingredient and populates a JSON object with its properties, including 
     * the name, quantity, unit, preparation (if present), calories per unit, and calories per cup.
     * The resulting JSON object can be used for saving or transmitting the ingredient data.
     *
     * <p>Usage:</p>
     * To convert an ingredient to JSON, call this method with the target ingredient:
     * <pre>{@code
     * JSONObject ingredientJson = ingredientToJson(ingredient);
     * }</pre>
     *
     * @param ingredient The {@link Ingredient} object to be converted into JSON.
     * @return A {@link JSONObject} representing the ingredient.
     */

    private static JSONObject ingredientToJson(Ingredient ingredient) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", ingredient.getName());
        jsonObject.put("quantity", ingredient.getQuantity());
        jsonObject.put("unit", ingredient.getUnit());
        
        // Include preparation if it's not empty
        if (!ingredient.getPreparation().isEmpty()) {
            jsonObject.put("preparation", ingredient.getPreparation());
        }
    
        // Add calories per unit and calories per cup
        jsonObject.put("calories_per_unit", ingredient.getCaloriesPerUnit());
        jsonObject.put("calories_per_cup", ingredient.getCaloriesPerCup());
        
        return jsonObject;
    }
    
}
