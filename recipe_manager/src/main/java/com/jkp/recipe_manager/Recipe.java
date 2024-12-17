

/**
 * Recipe class representing a recipe with name, ingredients, instructions,
 * servings, preparation time, cooking time, and total recipe calories.
 * Provides accessors (getters) and mutators (setters) for each field, allowing
 * controlled access and modification of the recipe details.
 * 
 * Accessors (Getters):
 * - getName() - Returns the name of the recipe.
 * - getIngredients() - Returns the list of ingredients in the recipe.
 * - getInstructions() - Returns the list of instructions for the recipe.
 * - getServings() - Returns the number of servings the recipe makes.
 * - getPrepTime() - Returns the preparation time for the recipe.
 * - getCookTime() - Returns the cooking time for the recipe.
 * - getTotalRecipeCalories() - Returns the total calories for the recipe.
 *
 * Mutators (Setters):
 * - setName(String name) - Sets the name of the recipe.
 * - setIngredients(List<Ingredient> ingredients) - Sets the list of ingredients.
 * - setInstructions(List<String> instructions) - Sets the list of instructions.
 * - setServings(int servings) - Sets the number of servings.
 * - setPrepTime(String prepTime) - Sets the preparation time.
 * - setCookTime(String cookTime) - Sets the cooking time.
 * - setTotalRecipeCalories(double calories) - Sets the total calories for the recipe.
 * @version 1.0.0
 * @author Jon-Kayla Pointer
 */

package com.jkp.recipe_manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private List<String> instructions;
    private int servings;
    private String prepTime;
    private String cookTime;
    private double totalRecipeCalories; 

    public Recipe(String name, ArrayList<Ingredient> ingredients, List<String> instructions, int servings, String prepTime, String cookTime) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servings = servings;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.totalRecipeCalories = calculateTotalRecipeCalories();
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public int getServings() {
        return servings;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }
    public double getTotalRecipeCalories() { 
        return totalRecipeCalories; 
    }

    // Method to calculate total recipe calories
    private double calculateTotalRecipeCalories() {
        double totalCalories = 0.0;
        for (Ingredient ingredient : ingredients) {
            totalCalories += ingredient.getTotalCalories();
        }
        return totalCalories;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }
    
    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new ArrayList<>();
        }
        this.ingredients.add(ingredient);
    }

    public void addInstruction(String instruction) {
        if (this.instructions == null) {
            this.instructions = new ArrayList<>();
        }
        this.instructions.add(instruction);
    }


    public static Recipe fromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray ingredientArray = jsonObject.getJSONArray("ingredients");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientArray.length(); i++) {
            ingredients.add(Ingredient.fromJson(ingredientArray.getJSONObject(i)));
        }
        JSONArray instructionArray = jsonObject.getJSONArray("instructions");
        List<String> instructions = new ArrayList<>();
        for (int i = 0; i < instructionArray.length(); i++) {
            instructions.add(instructionArray.getString(i));
        }
        int servings = jsonObject.getInt("servings");
        String prepTime = jsonObject.getString("prep_time");
        String cookTime = jsonObject.getString("cook_time");
        
        return new Recipe(name, ingredients, instructions, servings, prepTime, cookTime);
    }

    public void setTotalRecipeCalories(double totalRecipeCalories) {
       this.totalRecipeCalories = totalRecipeCalories  ; 
    }
}
