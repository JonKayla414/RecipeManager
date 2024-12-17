/**
 * SteppingStone4_Loops is a console-based Java application that builds upon
 * the previous recipe manager program by incorporating loop structures and
 * validation mechanisms to enhance the user experience.
 *
 * In this lab, we focus on iterating over user inputs using loop constructs
 * like `do-while` and `for` loops. The program prompts the user to enter
 * ingredient details and uses loops to gather multiple inputs until the
 * user indicates they are finished. User input is validated for correct
 * data types, unit measurements, and preparation methods.
 *
 * This module introduces robust looping logic, ensuring that the user can
 * continuously add ingredients to a recipe list until they choose to stop,
 * and handles invalid inputs with feedback prompts.
 *
 * Code Reflection:
 * - The use of the `do-while` loop ensures that the program continuously
 *   asks for input until the user confirms they are done. This has helped
 *   me better understand loop control in Java.
 * - Validation methods, such as `verifyQuantity()` and `verifyUnitOfMeasurement()`,
 *   ensure that user inputs conform to expected formats and prevent errors.
 * - Integrating enums for ingredient units has streamlined the validation
 *   process and reduced hard-coding of string values, improving the program's
 *   flexibility and readability.
 * - This exercise helped solidify my understanding of loops and how to manage
 *   user input in a dynamic way, ensuring a smoother user experience.
 *
 * @author Jon-Kayla Pointer
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class SteppingStone4_Loops {
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

    
    public static void main(String[] args) {
       Scanner scnr = new Scanner(System.in);
       String recipeName = "";
       ArrayList<Ingredient> ingredientList = new ArrayList(); // Use ArrayList to store Ingredient objects
       String newIngredient = "";
       boolean addMoreIngredients = true;
       
       System.out.println("Please enter the recipe name: ");
       recipeName = scnr.nextLine();
       
        
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
       
       System.out.println("Done adding Ingredients.\n"+"\nYou have entered "+ lenIngredients +" Ingredients for "+ recipeName + ".\n Please see the table of the ingredients you have entered.");
       printIngredients(ingredientList);
       
    }
  
}



