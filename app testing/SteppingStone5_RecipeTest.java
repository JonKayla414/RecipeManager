/**
 * SteppingStone5_RecipeTest is a console-based Java application that builds upon
 * the recipe manager program by integrating object-oriented principles and 
 * testing functionality for creating, managing, and displaying recipe data.
 *
 * In this module, we create instances of the `Recipe` and `Ingredient` classes,
 * demonstrating their interaction and verifying the proper functionality of
 * methods like adding ingredients and calculating recipe details.
 *
 * The program showcases:
 * - Creating recipe objects with and without parameters.
 * - Dynamically adding ingredients to recipes and validating ingredient details.
 * - Displaying the details of each recipe, including its name, servings, 
 *   ingredients, and total calories.
 * - Testing object-oriented principles like encapsulation and reusability 
 *   through methods and class interactions.
 *
 * Code Reflection:
 * - This exercise has deepened my understanding of working with Java classes 
 *   and managing their interactions through object composition.
 * - The ability to create reusable methods, such as `printRecipe()`, improves 
 *   code modularity and readability.
 * - Incorporating `ArrayList` for storing ingredients allows for dynamic 
 *   ingredient management, ensuring scalability for larger recipes.
 * - By testing constructors and setters, this lab reinforces the importance 
 *   of class design and the role of constructors in object initialization.
 *
 * @author Jon-Kayla Pointer
 */


import java.util.ArrayList;
import java.util.List;

public class SteppingStone5_RecipeTest {

    public static void main(String[] args) {
        // Create ingredient lists for the recipes
        ArrayList<Ingredient> recipeIngredients = new ArrayList<>();
        ArrayList<Ingredient> recipeIngredientsTwo = new ArrayList<>();

        // Create and add ingredients for the first recipe
        Ingredient tempIngredient = new Ingredient(
                "Anchovies", 2.0f, Ingredient.Unit.PCS, "none", 20.0, 0.0);
        tempIngredient.setTotalCalories(tempIngredient.calculateTotalCalories());
        recipeIngredients.add(tempIngredient);

        // Create and add ingredients for the second recipe
        Ingredient tempIngredientTwo = new Ingredient(
                "Noodles", 1.0f, Ingredient.Unit.CUP, "boiled", 100.0, 200.0);
        tempIngredientTwo.setTotalCalories(tempIngredientTwo.calculateTotalCalories());
        recipeIngredientsTwo.add(tempIngredientTwo);

        // Create two Recipe objects
        Recipe myFirstRecipe = new Recipe(
                "Ramen", recipeIngredientsTwo, List.of("Boil noodles", "Add seasoning"), 2, "10 min", "5 min");

        Recipe mySecondRecipe = new Recipe(
                "Pizza", recipeIngredients, List.of("Preheat oven", "Bake pizza"), 4, "20 min", "30 min");

        // Print the details of the recipes
        printRecipe(myFirstRecipe);
        printRecipe(mySecondRecipe);
    }

    /**
	 * Prints the details of the given Recipe object.
	 * 
	 * This method displays the recipe's name, number of servings, 
	 * preparation time, cooking time, total calories, ingredients, 
	 * and instructions in a formatted structure.
	 *
	 * @param recipe the Recipe object to be printed. Must not be null.
	 *               It should contain valid data for name, ingredients,
	 *               instructions, servings, prep time, and cook time.
	 */
    private static void printRecipe(Recipe recipe) {
        System.out.println("Recipe Name: " + recipe.getName());
        System.out.println("Servings: " + recipe.getServings());
        System.out.println("Prep Time: " + recipe.getPrepTime());
        System.out.println("Cook Time: " + recipe.getCookTime());
        System.out.println("Total Calories: " + recipe.getTotalRecipeCalories());
        System.out.println("Ingredients:");
        for (Ingredient ingredient : recipe.getIngredients()) {
            System.out.println("- " + ingredient.getName() + ": " +
                    ingredient.getQuantity() + " " + ingredient.getUnit() +
                    " (" + ingredient.getTotalCalories() + " calories)");
        }
        System.out.println("Instructions:");
        int stepNumber = 1;
        for (String instruction : recipe.getInstructions()) {
            System.out.println(stepNumber++ + ". " + instruction);
        }
        System.out.println();
    }
}
