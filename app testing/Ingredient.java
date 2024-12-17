
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
public class Ingredient {
    // Set Private Variables
    private String name;
    private Float quantity;
    private Unit unit;
    private String preparation;
    private double caloriesPerCup; 
    private double totalCalories; 
    private double caloriesPerUnit; // New field for calories per unit
    public enum Unit {
        CUP, TBSP, TSP, PCS, G, KG, L, ML
    }

    public Ingredient(String name, Float quantity, Unit unit, String preparation, double caloriesPerUnit, double caloriesPerCup) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.preparation = preparation;
        this.caloriesPerUnit = caloriesPerUnit; // Initialize calories per unit
        if (unit != Unit.CUP){
            
            this.caloriesPerCup = convertCaloriesPerUnitToCup(caloriesPerUnit, quantity, unit);
        } else{
            this.caloriesPerCup = caloriesPerCup;
        }
         // Calculate calories per cup based on unit
        this.totalCalories = calculateTotalCalories(); // Calculate total calories based on initial values
    }
    // Getters
    public String getName() {
        return name;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return   unit.name();
    }

    public String getPreparation() {
        return preparation;
    }
    
    public double getCaloriesPerCup() {
        return caloriesPerCup;
    }
    public double getTotalCalories() {
        return totalCalories;
    }


    public void setCaloriesPerCup(int caloriesPerCup) {
        this.caloriesPerCup = caloriesPerCup;
        this.totalCalories = calculateTotalCalories();
    }

    public double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public void setCaloriesPerUnit(double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
        this.caloriesPerCup = convertCaloriesPerUnitToCup(caloriesPerUnit, quantity, unit); 
        this.totalCalories = calculateTotalCalories(); 
    }

   
    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    // Calculate total calories based on current quantity, unit, and calories per cup
    public double calculateTotalCalories() {
        
        if ( unit != Unit.CUP){
            
            return caloriesPerCup * convertToCups(quantity, unit); // Total calories = calories per cup * quantity in cups
        } 
        return caloriesPerCup * quantity;
        
    }

    // Convert calories per unit to calories per cup
    private double  convertCaloriesPerUnitToCup(double caloriesPerUnit, Float quantity, Unit unit) {
        double quantityInCups = convertToCups(quantity, unit);
        return (caloriesPerUnit / quantityInCups); // Calculate calories per cup
    }

    // Method to convert different units to cups
    private double convertToCups(Float quantity, Unit unit) {
        Float quantityValue = quantity;
        return switch (unit) {
            case TBSP -> quantityValue / 16;
            case TSP -> quantityValue / 48;
            case G -> quantityValue / 240;
            case KG -> quantityValue * 4.22675;
            case L -> quantityValue * 4.22675;
            case ML -> quantityValue / 240;
            case CUP -> quantityValue;
            case PCS -> quantityValue;
            default -> quantityValue;
        };
    }
    public static Unit fromString(String unitString) {
        try {
            return Unit.valueOf(unitString.toUpperCase()); // Convert to uppercase to handle case insensitivity
        } catch (IllegalArgumentException e) {
            return null; // Or throw an exception if you prefer
        }
    }

    // For storage used for later 
    public static Ingredient fromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Float quantity = jsonObject.optFloat("quantity");
        Unit unit = Unit.fromString(jsonObject.getString("unit"));
        String preparation = jsonObject.optString("preparation", ""); 
        int caloriesPerUnit = jsonObject.optInt("calories_per_unit", 0); 
        double totalCalories = jsonObject.optDouble("total_calories", 0.0); 
        return new Ingredient(name, quantity, unit, preparation, caloriesPerUnit,caloriesPerUnit);
    }
}
