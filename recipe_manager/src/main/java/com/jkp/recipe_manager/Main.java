/**
 * Package: com.jkp.recipe_manager
 * 
 * The Main class serves as the entry point for the Recipe Manager application.
 * It initializes and displays the graphical user interface (GUI) for managing recipes.
 * 
 * <p>Usage:</p>
 * Run this class to start the Recipe Manager application.
 * 
 * @author Jon-Kayla Pointer
 * @version 1.0.0
 */




package com.jkp.recipe_manager;


/**
 * The Main class launches the RecipeManagerGUI to start the application.
 */
public class Main {
    /**
     * The main method initializes the Recipe Manager GUI and makes it visible.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        RecipeManagerGUI gui = new RecipeManagerGUI();
        gui.setVisible(true);
    }
}
