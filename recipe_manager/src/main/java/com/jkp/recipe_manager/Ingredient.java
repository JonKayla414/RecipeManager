/**
 * The Ingredient class represents a food ingredient with properties such as name, 
 * quantity, unit of measurement, preparation method, and calorie information.
 * It provides methods to calculate total calories based on the ingredient's 
 * quantity and the corresponding calories per unit or per cup. This class 
 * is essential for managing recipe ingredients in the Recipe Manager application.
 * 
 * Properties:
 * - name: The name of the ingredient.
 * - quantity: The amount of the ingredient in a specified unit.
 * - unit: The unit of measurement for the ingredient (e.g., cups, grams). Now uses Enumerations for custom Data Type.
 * - preparation: Any preparation method for the ingredient (optional).
 * - caloriesPerCup: The calorie content of the ingredient per cup.
 * - totalCalories: The total calories calculated based on the current quantity.
 * - caloriesPerUnit: The calorie content of the ingredient per specified unit.
 * 
 * Methods:
 * - calculateTotalCalories: Computes total calories based on quantity and unit.
 * - convertToCups: Converts different units to cups for consistent calorie calculations.
 * - fromJson: Creates an Ingredient instance from a JSON object.
 * 
 * This class aids in facilitating accurate calorie tracking and nutritional 
 * information for recipes, enhancing the overall functionality of the Recipe 
 * Manager application.
 * 
 * 
 * @author JonKayla Pointer
 */



package com.jkp.recipe_manager;

import org.json.JSONException;
import org.json.JSONObject;


public class Ingredient {
    // Set Private Variables
    private String name;
    private float quantity;
    private Unit unit;
    private String preparation;
    private double caloriesPerCup; 
    private double totalCalories; 
    private double caloriesPerUnit; // New field for calories per unit
    private double quantityToCup;
    public enum Unit {
        CUP, TBSP, TSP, PCS, G, KG, L, ML;
        
   
    }
    /**
     * Constructs an Ingredient object with the specified attributes.
     * This constructor initializes the ingredient's name, quantity, unit, preparation method,
     * calories per unit, and calculates the calories per cup based on the unit.
     * 
     * <p>If the unit is not {@link Unit#CUP}, the method converts the calories per unit to
     * calories per cup. If the unit is {@link Unit#CUP}, the calories per cup value is directly 
     * set to the provided value. Additionally, the total calories for the ingredient are calculated 
     * based on the initial quantity and calories per unit.</p>
     *
     * @param name The name of the ingredient (e.g., "Sugar").
     * @param quantity The quantity of the ingredient (e.g., 4.0).
     * @param unit The unit of measurement for the ingredient (e.g., {@link Unit#GRAM}, {@link Unit#CUP}).
     * @param preparation The preparation method for the ingredient (e.g., "Chopped").
     * @param caloriesPerUnit The number of calories per unit of the ingredient (e.g., 23 for sugar).
     * @param caloriesPerCup The number of calories per cup of the ingredient (only used if the unit is {@link Unit#CUP}).
     */

    public Ingredient(String name, float quantity, Unit unit, String preparation, double caloriesPerUnit, double caloriesPerCup) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.preparation = preparation;
        this.caloriesPerUnit = caloriesPerUnit; // Initialize calories per unit
        this.quantityToCup = convertQuantityToCups(quantity, unit);
        if (unit != Unit.CUP){
            
            this.caloriesPerCup = convertCaloriesPerUnitToCup(quantity, unit, caloriesPerUnit );
        } else{
            this.caloriesPerCup = caloriesPerCup;
        }
         // Calculate calories per cup based on unit
        this.totalCalories = calculateTotalCalories(); // Calculate total calories based on initial values
    }
    // Getters
    /**
     * Returns the name of the ingredient.
     * 
     * This method retrieves the name of the ingredient, which is typically the name
     * of the food item (e.g., "Sugar", "Flour", etc.). The name is stored as a string
     * in the `name` attribute of the `Ingredient` class.
     *
     * @return The name of the ingredient.
     */

    public String getName() {
        return name;
    }
    /**
     * Returns the quantity of the ingredient.
     * 
     * This method retrieves the quantity of the ingredient, typically representing
     * the amount of the ingredient in a specific unit (e.g., grams, cups, tablespoons).
     * The quantity is stored as a `float` in the `quantity` attribute of the `Ingredient` class.
     *
     * @return The quantity of the ingredient.
     */

    public float  getQuantity() {
        return quantity;
    }
    /**
     * Returns the unit of measurement for the ingredient.
     * 
     * This method retrieves the unit of measurement used for the ingredient, such as grams, cups, etc.
     * The unit is stored as an enumeration (`Unit`) and is returned as a string representation of its name.
     *
     * @return The name of the unit of measurement for the ingredient as a string.
     */

    public String getUnit() {
        return   unit.name().toLowerCase();
    }

    /**
     * Returns the preparation method for the ingredient.
     * 
     * This method retrieves the preparation instructions or details associated with the ingredient.
     * The preparation typically includes how the ingredient should be prepared before being added to a recipe (e.g., chopped, sliced, cooked).
     *
     * @return A string representing the preparation method for the ingredient.
     */

    public String getPreparation() {
        return preparation;
    }

    /**
     * Returns the number of calories per cup for the ingredient.
     * 
     * This method retrieves the calculated or provided calories per cup for the ingredient, 
     * based on the quantity and unit of measurement. If the unit is not in cups, the calories 
     * per unit are converted to calories per cup.
     *
     * @return A double representing the calories per cup for the ingredient.
     */
    public double getCaloriesPerCup() {
        return caloriesPerCup;
    }

    /**
     * Returns the total calories for the ingredient.
     * 
     * This method calculates and retrieves the total calories for the ingredient, 
     * which is determined by multiplying the quantity by the calories per cup. 
     * The total calories are calculated based on the ingredient's quantity and its 
     * associated calories per cup value.
     *
     * @return A double representing the total calories for the ingredient.
     */

    public double getTotalCalories() {
        return totalCalories;
    }
    //Setters 

    /**
     * Retrieves the calories per unit of the ingredient.
     * 
     * This method returns the number of calories for each unit of the ingredient, based on
     * the ingredient's specific unit (e.g., grams, tablespoons, cups). This value is used 
     * to calculate the total calories for the ingredient.
     *
     * @return A double representing the calories per unit for the ingredient.
     */

    public double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    /**
     * Retrieves the calories per unit of the ingredient.
     * 
     * This method returns the number of calories for each unit of the ingredient, based on
     * the ingredient's specific unit (e.g., grams, tablespoons, cups). This value is used 
     * to calculate the total calories for the ingredient.
     *
     * @return A double representing the calories per unit for the ingredient.
     */

     public double getQuantityEquivalent() {
        return quantityToCup;
    }

    /**
     * Sets the total calories for the ingredient.
     * 
     * This method directly sets the total calories for the ingredient. It doesn't recalculate the value 
     * based on other properties, so it should be used carefully.
     *
     * @param totalCalories The total calories for the ingredient.
     */

    public void setCaloriesPerUnit(double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
        this.caloriesPerCup = convertCaloriesPerUnitToCup( quantity, unit, caloriesPerUnit); 
        this.totalCalories = calculateTotalCalories(); 
    }

        
    /**
     * Sets the calories per cup for the ingredient and updates the total calories.
     * 
     * This method sets the calories per cup and recalculates the total calories based on the new value.
     *
     * @param caloriesPerCup The number of calories per cup for the ingredient.
     */

    
    public void setCaloriesPerCup(int caloriesPerCup) {
        this.caloriesPerCup = caloriesPerCup;
        this.totalCalories = calculateTotalCalories();
    }

    /**
     * Sets the total calories for the ingredient.
     * 
     * This method directly sets the total calories for the ingredient. It doesn't recalculate the value 
     * based on other properties, so it should be used carefully.
     *
     * @param totalCalories The total calories for the ingredient.
     */
   
    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    
    /**
     * Sets the unit of measurement for the ingredient.
     * 
     * This method sets the unit (e.g., grams, cups, tablespoons) for the ingredient. 
     * It can be used to update the unit if needed.
     *
     * @param unit The unit of measurement for the ingredient.
     */

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Sets the preparation details for the ingredient.
     * 
     * This method sets the preparation steps for the ingredient (e.g., chopped, diced).
     *
     * @param preparation The preparation details for the ingredient.
     */
    public void setPreperation(String preperation) {
        this.preparation = preperation;
    }
    /**
     * Sets the name of the ingredient.
     * 
     * This method sets the name of the ingredient, which represents its identity in the recipe.
     *
     * @param name The name of the ingredient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the quantity of the ingredient.
     * 
     * This method sets the quantity of the ingredient in its specified unit of measurement.
     *
     * @param quantity The quantity of the ingredient.
     */
    
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates the total calories for the ingredient based on its quantity, unit, and calories per cup/unit.
     * 
     * This method calculates the total calories by either multiplying the calories per cup by the quantity
     * (if the unit is CUP) or by multiplying calories per unit with the quantity for other units.
     * 
     * @return The total calories for the ingredient.
     */
    public double calculateTotalCalories() {
        
        if ( unit != Unit.CUP){
            return caloriesPerUnit * quantity; // Total calories = calories per cup * quantity in cups
        } 
        
        return caloriesPerCup * quantity;
        
    }

    /**
     * Calculates the total calories for a given quantity, unit, and calories per unit.
     * 
     * This method calculates the total calories using the specified quantity, unit, and calories per unit. 
     * It is a static method used for calculations without needing an instance of the Ingredient class.
     * 
     * @param quantity The quantity of the ingredient.
     * @param unit The unit of measurement for the ingredient.
     * @param caloriesPerUnit The number of calories per unit of the ingredient.
     * 
     * @return The total calories for the ingredient.
     */
    public static double calculateTotalCalories(float quantity, Unit unit, double caloriesPerUnit ) {
     
        return caloriesPerUnit * quantity;
        
    }

    /**
     * Converts calories per unit to calories per cup for a given quantity and unit.
     * 
     * This method converts the calories per unit into calories per cup by first calculating 
     * the total calories for the quantity and then converting that into cups. 
     * It is used to handle different units like grams, tablespoons, etc.
     *
     * @param quantity The quantity of the ingredient.
     * @param unit The unit of measurement for the ingredient.
     * @param caloriesPerUnit The number of calories per unit for the ingredient.
     * 
     * @return The number of calories per cup for the ingredient.
     */
    public static double  convertCaloriesPerUnitToCup(float  quantity, Unit unit, double caloriesPerUnit) {
        if ( unit != Unit.CUP){
           
                
            double tempCups = convertQuantityToCups(quantity, unit);
            double totalCalories = caloriesPerUnit * quantity;
            double caloriesPerCup = totalCalories / tempCups;
       
            return tempCups;
        } 
    
        return caloriesPerUnit;
        
    }

    /**
     * Converts the  unit to  cup cup based on the unit (1cup to unit ratio approximation).
     * <p>
     * If the unit is not CUP, the method calculates the total calories by multiplying
     * the quantity.
     * returns the calories per unit.
     *
     * @param quantity         The quantity of the ingredient (e.g., 16 for tablespoons, 48 for teaspoons).
     * @param unit             The unit of the ingredient (e.g., TBSP, TSP, ML, CUP).
    
     * @return                 The Coversion to cups
     */
    public static double  convertQuantityPerUnitToCup(float  quantity, Unit unit) {
        if ( unit != Unit.CUP){
           
            double tempCups = convertQuantityToCups(quantity, unit);
       
            return tempCups;
        } 
    
        return quantity;
        
    }


    /**
     * Converts the quantity of an ingredient from a given unit to cups.
     * 
     * This method converts various units (e.g., grams, tablespoons, liters) to cups. 
     * It handles different units based on predefined conversion factors.
     * 
     * @param quantity The quantity of the ingredient.
     * @param unit The unit of measurement for the ingredient.
     * 
     * @return The equivalent quantity in cups.
     */
    private static double convertQuantityToCups(Float quantity, Unit unit) {
        Float quantityValue = quantity;
     
        return switch (unit) {
            case TBSP -> quantityValue / 16;
            case TSP -> quantityValue / 48;
            case G -> quantityValue / 236.58823648491;
            case KG -> quantityValue * 4.22675;
            case L -> quantityValue * 4.22675;
            case ML -> quantityValue / 240;
            case CUP -> quantityValue;
            case PCS -> quantityValue;
            default -> quantityValue;
        };
    }

    /**
     * Converts calories per unit to calories per cup.
     *
     * @param caloriesPerUnit The calories per given unit.
     * @param unit The unit of measurement.
     * @return The equivalent calories per cup.
     */
    private static double convertToCups(Float quantity, Unit unit) {
        Float quantityValue = quantity;
     
        return switch (unit) {
            case TBSP -> quantityValue * 16;
            case TSP -> quantityValue * 48;
            case G -> quantityValue * 240;
            case KG -> quantityValue / 4.22675;
            case L -> quantityValue  / 4.22675;
            case ML -> quantityValue  * 240;
            case CUP -> quantityValue; // Return Cup
            case PCS -> quantityValue; // No conversion to Pieces
            default -> quantityValue; // Assumes Pieces or Cup
        };
    }


    /**
     * Converts a unit string to a corresponding Unit enum value.
     * 
     * This method converts a string representing a unit (e.g., "cup", "tbsp") 
     * into the corresponding `Unit` enum value, ensuring case insensitivity.
     * 
     * @param unitString The string representing the unit (e.g., "cup", "tbsp").
     * 
     * @return The corresponding `Unit` enum value, or null if the string is invalid.
     */

    public static Unit fromString(String unitString) {
        try {
            return Unit.valueOf(unitString.toUpperCase()); // Convert to uppercase to handle case insensitivity
        } catch (IllegalArgumentException e) {
            return null; // Or throw an exception if you prefer
        }
    }


    /**
     * Converts a JSON object to an `Ingredient` instance.
     * 
     * This method takes a `JSONObject` containing ingredient details, such as name, quantity, unit, 
     * preparation method, and calories per unit. It extracts these values from the JSON object and 
     * creates a new `Ingredient` object using the extracted data. If certain fields are missing in 
     * the JSON object, default values are used.
     * 
     * @param jsonObject The JSON object containing the ingredient details.
     * 
     * @return A new `Ingredient` object initialized with the values from the JSON object.
     * 
     * @throws JSONException if the JSON object does not contain the required fields or is invalid.
     */
    public static Ingredient fromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Float quantity = jsonObject.optFloat("quantity");
        String unitString = jsonObject.getString("unit");
        Unit unit = getUnitFromString(unitString);
        String preparation = jsonObject.optString("preparation", ""); 
        double caloriesPerUnit = jsonObject.optDouble("calories_per_unit", 0.0); 
        return new Ingredient(name, quantity, unit, preparation, caloriesPerUnit,caloriesPerUnit);
    }

    /**
     * Converts a unit string to a corresponding Unit enum value, handling various common units.
     * 
     * This method converts a string representing a unit (e.g., "cup", "tbsp") 
     * into the corresponding `Unit` enum value. It allows for different case variations in the input string.
     * 
     * @param unitString The string representing the unit (e.g., "cup", "tbsp").
     * 
     * @return The corresponding `Unit` enum value.
     * 
     * @throws IllegalArgumentException if the string does not match a valid unit.
     */
    public static Unit getUnitFromString(String unitString) {
        switch (unitString.toLowerCase()) {
            case "cup" -> {
                return Unit.CUP;
            }
            case "tbsp" -> {
                return Unit.TBSP;
            }
            case "tsp" -> {
                return Unit.TSP;
            }
            case "pcs" -> {
                return Unit.PCS;
            }
            case "g" -> {
                return Unit.G;
            }
            case "kg" -> {
                return Unit.KG;
            }
            case "l" -> {
                return Unit.L;
            }
            case "ml" -> {
                return Unit.ML;
            }
            default -> throw new IllegalArgumentException("Invalid unit: " + unitString);
        }
        
    }
}
