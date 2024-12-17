/**
 * Recipe Test is a console-based Java application
 * that builds upon the previous recipe manager program by integrating
 * enhanced looping structures and input validation. This module focuses
 * on utilizing loop constructs like `do-while` and `for` loops to gather
 * multiple pieces of information from the user until they indicate completion. This tests the Recipe Class. 
 *
 * The program allows users to input recipe details, including ingredients,
 * measurements, and preparation methods. It employs loops to repeatedly
 * request these inputs until the user is finished, ensuring that they can
 * enter as many ingredients as needed. Each input is validated to confirm
 * the correctness of the data type, unit of measurement, and preparation
 * method, ensuring the recipe is properly formatted and error-free.
 *
 * The key features of this module include:
 * - Loops that enable users to continuously add ingredients to their recipe
 *   until they choose to stop, making the process flexible and user-friendly.
 * - Validation methods that check the correctness of user inputs, including
 *   verifying quantities, units, and preparation methods, providing immediate
 *   feedback to the user if an error is made.
 * - Utilization of enums to standardize unit of measurement options, reducing
 *   hard-coding and improving the readability and maintainability of the code.
 * 
 * Code Reflection:
 * - The use of `do-while` loops in this project has helped me understand how
 *   to control user input flow, repeatedly prompting for data until the user
 *   confirms they are done, leading to a more efficient and interactive user
 *   experience.
 * - Input validation functions such as `verifyQuantity()` and `verifyUnitOfMeasurement()`
 *   have proven to be effective in ensuring only valid data is entered, helping
 *   to prevent runtime errors or incorrect recipe formatting.
 * - Incorporating enums to manage units of measurement has simplified the code,
 *   making it easier to expand the program with additional units in the future.
 * - Overall, this project has deepened my understanding of loops, user input
 *   handling, and data validation, and how these concepts can be applied in a
 *   real-world recipe manager application.
 *
 * @author Jon-Kayla Pointer
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Recipe_Test {
    /**
     * verifyQuantity prompts the user to enter a valid quantity value for an ingredient.
     * It ensures that the input is a valid floating-point number and returns the value.
     * If the input is invalid, the user is repeatedly asked to provide a valid input.
     *
     * The function handles errors gracefully by ensuring that non-numeric values are not accepted
     * and only valid float values are returned. It utilizes the Scanner class for reading user input.
     *
     * @param scnr The Scanner object used to read the user input.
     * @param userInput The initial input from the user that is being validated. 
     *                  This input is checked to see if it's a valid float.
     * @return The validated quantity value as a float. If the input is invalid, the function will
     *         keep asking for a valid float until one is provided.
     */
    public static float verifyQuantity(Scanner scnr, String userInput)
     {
        float validFloat = 1.0f;
        while (true) {
            try {
                validFloat = Float.parseFloat(userInput);
                if (validFloat > 0) {
                    break; // Valid float found
                } else {
                    System.out.println("Quantity must be greater than 0. Please enter again: ");
                    userInput = scnr.next();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid float: ");
                userInput = scnr.next();
            }
        }

        return validFloat;
     }
     /**
     * verifyUnitOfMeasurement prompts the user to input a valid unit of measurement for an ingredient.
     * It checks if the input matches one of the predefined valid units. If the user input is invalid,
     * the program will keep asking for a correct input until a valid unit is provided.
     * 
     * The valid units include: 'cup', 'tbsp', 'tsp', 'pcs', 'g', 'kg', 'l', and 'ml'. This method is used
     * to validate the unit provided by the user when adding an ingredient to the recipe.
     *
     * @param scnr The Scanner object used to read the user input.
     * @param userInput The input entered by the user which is checked against the valid units.
     * @return The valid unit of measurement as a string. If the input is invalid, it will prompt the user until a valid unit is entered.
     */
    public static Ingredient.Unit verifyUnitOfMeasurement(Scanner scnr, String userInput) {
        String[] validUnits = {"cup", "tbsp", "tsp", "pcs", "g", "kg", "l", "ml"};
        
        // Simplified loop using list containment
        while (true) {
            if (Arrays.asList(validUnits).contains(userInput.toLowerCase())) {
                return Ingredient.Unit.valueOf(userInput.toUpperCase()); // Valid unit found
            }
            scnr.nextLine();
            System.out.println("Invalid unit. Please enter a valid unit (OPTIONS: 'cup', 'tbsp', 'tsp', 'pcs', 'g', 'kg', 'l', and 'ml'): ");
            userInput = scnr.next();
        }
    } 
    /**
     * verifyPreparation prompts the user to input the preparation method for an ingredient.
     * If the input is left blank, it returns an empty string. This method ensures that the user 
     * can either provide a valid preparation method or leave it blank if not necessary.
     * 
     * @param scnr The Scanner object used to read the user input.
     * @param userInput The input entered by the user, representing the preparation method of the ingredient.
     * @return The preparation method as a string. If left blank, it returns an empty string.
     */
    public static String verifyPreparation(Scanner scnr, String userInput) {
        String validPreperation = userInput;
        boolean isPreparationNeeded = true;
        String reply;
      

        do{
           
            System.out.println("You have entered the following for preperation: " + validPreperation + "\n Is this correct?");
            reply = scnr.next().toLowerCase();
            switch(reply) {
                case "y" -> {
                    isPreparationNeeded = false;
                    validPreperation = userInput;
                }
                case "n" -> {
                    scnr.nextLine();
                    System.out.println("Enter the preparation method (or leave blank): ");
                    validPreperation  = scnr.nextLine();
                }
                default -> {
                    scnr.nextLine();
                    System.out.println("Please enter 'y' or 'n'.");
                }
                   
            }
        }
        while (isPreparationNeeded);
        return validPreperation; 
    }
    /**
     * verifyCalories prompts the user to input the calories per unit of an ingredient. It ensures that 
     * the input is a valid numeric value and keeps asking the user for a valid input until one is provided.
     * 
     * This method handles user input validation, making sure the user only enters valid calorie values.
     * The method is used for validating the calories per unit of an ingredient when the user is adding or editing it.
     *
     * @param scnr The Scanner object used to read the user input.
     * @param userInput The input entered by the user, representing the calories per unit of the ingredient.
     * @return The valid calorie value as a double. If the input is invalid, it will prompt the user until a valid value is entered.
     */
    public static double verifyCalories(Scanner scnr, String userInput) {
        double validDouble = 0.0;
        while (true) {
            try {
                validDouble = Double.parseDouble(userInput);
                if (validDouble >= 0) {
                    break; // Valid double found
                } else {
                    System.out.println("Calories must be 0 or greater. Please enter again: ");
                    userInput = scnr.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
                userInput = scnr.nextLine();
            }
        }
        return validDouble;
    }
    /**
     * Prompts the user to enter a list of instructions.
     * The user is asked if they want to add more instructions and can add them until they confirm they are done.
     * The method continues to collect instructions until the user indicates they're finished.
     *
     * @param scnr the Scanner object used for user input
     * @return a list of instructions entered by the user
     */

    public static List<String> getInstructionListFromUser(Scanner scnr) {
        List<String> instructions = new ArrayList<>();
        String reply;
        boolean isInstructionsNeeded = true;
    
        // Get instructions from the user until they confirm they are done
        do {
            System.out.println("There are currently " + instructions.size() + " instructions.");
            System.out.println("Would you like to add an instruction? (y/n)");
            reply = scnr.next().toLowerCase();
            
            switch(reply) {
                case "y" -> {
                    // Verify the instruction before adding it to the list
                    String newInstruction = verifyInstruction(scnr);
                    instructions.add(newInstruction);
                }
                case "n" -> {
                    System.out.println("Done adding instructions.");
                    isInstructionsNeeded = false;
                }
                default -> System.out.println("Please enter 'y' or 'n'.");
            }
        } while (isInstructionsNeeded);
    
        return instructions;
    }
    /**
     * Prompts the user to enter an instruction and verifies its correctness.
     * The user is asked to confirm if the entered instruction is correct.
     * If the user confirms, the instruction is returned; if not, the user can re-enter the instruction.
     * 
     * @param scnr the Scanner object used for user input
     * @return the confirmed instruction entered by the user
     */

    public static String verifyInstruction(Scanner scnr) {
        String instruction;
        String reply;
    
        // Prompt the user to enter the instruction
        System.out.println("Please enter the instruction: ");
        scnr.nextLine();  // Consume newline left over from previous input
        instruction = scnr.nextLine();
    
        // Allow the user to confirm or correct the entered instruction
        do {
            System.out.println("You have entered: " + instruction);
            System.out.println("Is this correct? (y/n)");
            reply = scnr.next().toLowerCase();
            
            switch(reply) {
                case "y" -> {
                    // Return the confirmed instruction
                    return instruction;
                }
                case "n" -> {
                    // Let the user re-enter the instruction if not confirmed
                    System.out.println("Please enter the instruction again: ");
                    scnr.nextLine();  // Consume newline
                    instruction = scnr.nextLine();
                }
                default -> System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        } while (!reply.equals("y"));
    
        return instruction;  // Shouldn't reach here, but for safety
    }
    /**
     * printIngredients prints a list of ingredients in a formatted table. It iterates through the list of 
     * ingredients and displays their details, including name, quantity, unit, preparation method, calories per unit, 
     * and total calories.
     * 
     * The ingredients are printed in a user-friendly table format, with each ingredient shown on a new line, numbered sequentially.
     * This method is used to display all the ingredients currently added to a recipe.
     *
     * @param ingredientList The ArrayList containing all the ingredients to be printed.
     */
    public static void printIngredients(ArrayList<Ingredient> ingredientList) {
        // Print the table header
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf("| %-3s | %-15s | %-10s | %-10s | %-15s | %-15s | %15s | \n", 
                          "#", "Ingredient Name", "Quantity", "Unit", "Preparation", "Total Calories", "Calories Per Unit");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        
        // Loop through the ingredients and print each one
        for (int i = 0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            System.out.printf("| %-3d | %-15s | %-10.2f | %-10s | %-15s | %-15.2f |  %-15.2f |\n",
                              i + 1, // Index number starting from 1
                              ingredient.getName(),
                              ingredient.getQuantity(),
                              ingredient.getUnit(),
                              ingredient.getPreparation(),
                              ingredient.getTotalCalories(),
                              ingredient.getCaloriesPerUnit()
                              );
        }
        
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    /**
     * Prints the list of instructions to the console.
     * Each instruction is printed with a step number.
     *
     * @param instructions the list of instructions to be printed
     */
    public static void printInstructions(List<String> instructions) {
        // Check if there are instructions to print
        if (!instructions.isEmpty()) {
            System.out.println("\nInstructions:");
            for (int i = 0; i < instructions.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, instructions.get(i));
            }
        } else {
            System.out.println("\nInstructions: None provided.");
        }
    }

    /**
     * printRecipe displays the complete details of a recipe, including its name, servings,
     * prep time, cook time, total calories, and the list of ingredients.
     *
     * @param recipe The Recipe object containing the recipe details and ingredient list.
     */
    public static void printRecipe(Recipe recipe) {
        // Print the basic details of the recipe
        System.out.println("=================================================================================");
        System.out.println("Recipe Name: " + recipe.getName());
        System.out.println("Servings: " + recipe.getServings());
        System.out.println("Preparation Time: " + recipe.getPrepTime());
        System.out.println("Cooking Time: " + recipe.getCookTime());
        System.out.println("Total Calories: " + recipe.getTotalRecipeCalories());
        System.out.println("=================================================================================");
        
        // Print the ingredient list
        System.out.println("Ingredients:");
        printIngredients(recipe.getIngredients());
        
        printInstructions(recipe.getInstructions());
    }


    
    public static void main(String[] args) {
       Scanner scnr = new Scanner(System.in);
       String recipeName = "";
       ArrayList<Ingredient> ingredientList = new ArrayList(); // Use ArrayList to store Ingredient objects
      
       boolean addMoreIngredients = true;
       
       System.out.println("Please enter the recipe name: ");
       recipeName = scnr.nextLine();
    
       System.out.println("Enter the number of servings (as an integer): ");
       int servings = (int) verifyQuantity(scnr, scnr.next());

       System.out.println("Enter the preparation time (in minutes): ");
       String prepTime = scnr.next() + " min";

       System.out.println("Enter the cook time (in minutes): ");
       String cookTime = scnr.next() + " min";
       
        
       do {           
           System.out.println("Would you like to enter an ingredient: (y or n)");
           String reply = scnr.next().toLowerCase();
           
           switch (reply) {
               case "y" -> {
                   scnr.nextLine();
                   System.out.println("Enter the ingredient name: ");
                   String name = scnr.nextLine();
                   System.out.println("Enter the quantity (as a float): ");
                   String userInputQuantity = scnr.next();
                   Float quantity = verifyQuantity(scnr, userInputQuantity);
                   scnr.nextLine();
                   System.out.println("Enter the unit of measurement (OPTIONS: 'cup', 'tbsp', 'tsp', 'pcs', 'g', 'kg', 'l', and 'ml'): ");
                   String userInputUnit = scnr.next();
                   Ingredient.Unit unit = verifyUnitOfMeasurement(scnr, userInputUnit);
                   scnr.nextLine();
                   System.out.println("Enter the preparation method (or leave blank): ");
                   String userInputPreparation = scnr.nextLine();
                   String preparation = verifyPreparation(scnr, userInputPreparation);
                   scnr.nextLine();
                   System.out.println("Enter the calories per unit (as a double): ");
                   String userInputCalories = scnr.next();
                   double caloriesPerUnit = verifyCalories(scnr, userInputCalories);
                   double caloriesPerCup = caloriesPerUnit;
                   // Create a new Ingredient object and add it to the list
                   Ingredient ingredient = new Ingredient(name, quantity, unit, preparation, caloriesPerUnit, caloriesPerCup);
                   ingredientList.add(ingredient);
                   scnr.nextLine();
               }
               case "n" -> addMoreIngredients = false; // Stop the loop
               default -> System.out.println("Please enter 'y' or 'n'.");
           }
            
       } while (addMoreIngredients);
       int lenIngredients = ingredientList.size();
       List<String> instructions = getInstructionListFromUser(scnr);
       Recipe recipe = new Recipe(recipeName, ingredientList, instructions, servings, prepTime, cookTime);
       
       System.out.println("You have entered "+ lenIngredients +" Ingredients for "+ recipeName + ".\n Please see the recipe you have entered.");
       printRecipe(recipe);
       
    }
  
}



