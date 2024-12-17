/**
 * Package: com.jkp.recipe_manager
 * 
 * The RecipeCollection class manages a collection of Recipe objects. 
 * It provides functionality to add recipes and retrieve the list of recipes.
 * This class uses an ArrayList to store Recipe instances.
 * 
 * <p>Features:</p>
 * <ul>
 *     <li>Adding a Recipe to the collection</li>
 *     <li>Retrieving the list of stored recipes</li>
 *     <li>Setting a new list of recipes</li>
 * </ul>
 * 
 * @author Jon-Kayla Pointer
 * @version 1.0.0
 */


package com.jkp.recipe_manager;

import java.util.ArrayList;
/**
 * The RecipeCollection class provides a container for managing Recipe objects.
 */

public class RecipeCollection {
    /**
     * A list to store Recipe objects.
     */
    private ArrayList<Recipe> recipes;

    /**
     * Constructs a new RecipeCollection with an empty list of recipes.
     */
    public RecipeCollection() {
        this.recipes = new ArrayList<>();
        
    }
     /**
     * Adds a Recipe to the collection.
     * 
     * @param recipe The Recipe object to add.
     */

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
    
    /**
     * Returns the list of all Recipe objects in the collection.
     * 
     * @return An ArrayList of Recipe objects.
     */

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Sets the list of recipes for this collection.
     * 
     * @param recipes The ArrayList of Recipe objects to set.
     */
    
    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

}
