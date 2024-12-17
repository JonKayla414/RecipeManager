/**
 * Package: com.jkp.recipe_manager
 * 
 * The AddIngredients class provides a graphical user interface (GUI) for adding ingredients to a recipe.
 * It enables users to input ingredient details such as name, quantity, unit, preparation method, 
 * and calorie information, and calculates the total calories for each ingredient.
 * 
 * <p>Features:</p>
 * <ul>
 *     <li>Dynamic GUI for adding multiple ingredients</li>
 *     <li>Automatic calculation of total calories based on quantity and calories per unit</li>
 *     <li>Validation for numerical inputs</li>
 *     <li>Integration with AddInstructions to proceed with adding recipe instructions</li>
 * </ul>
 * 
 * @author Jon-Kayla Pointer
 * @version 1.0.0
 */
/**
 * Package: com.jkp.recipe_manager
 * 
 * The AddIngredients class provides a graphical user interface (GUI) for adding ingredients to a recipe.
 * It enables users to input ingredient details such as name, quantity, unit, preparation method, 
 * and calorie information, and calculates the total calories for each ingredient.
 * 
 * <p>Features:</p>
 * <ul>
 *     <li>Dynamic GUI for adding multiple ingredients</li>
 *     <li>Automatic calculation of total calories based on quantity and calories per unit</li>
 *     <li>Validation for numerical inputs</li>
 *     <li>Integration with AddInstructions to proceed with adding recipe instructions</li>
 * </ul>
 * 
 * @author Jon-Kayla Pointer
 * @version 1.0.0
 */

 package com.jkp.recipe_manager;

 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.FlowLayout;
 import java.awt.Frame;
 import java.awt.event.KeyAdapter;
 import java.awt.event.KeyEvent;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 
 import javax.swing.BoxLayout;
 import javax.swing.JButton;
 import javax.swing.JComboBox;
 import javax.swing.JDialog;
 import javax.swing.JLabel;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JScrollPane;
 import javax.swing.JTable;
 import javax.swing.JTextField;
 import javax.swing.table.DefaultTableModel;
 
 /**
  * The AddIngredients class manages the user interface for adding ingredients to a recipe.
  */
 public class AddIngredients {
     private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
 
     private final AddInstructions addInstructions = new AddInstructions();
 
     /**
      * Displays a dialog to add ingredients to a recipe.
      * 
      * @param recipeName The name of the recipe.
      * @param prepTime The preparation time for the recipe.
      * @param cookTime The cooking time for the recipe.
      * @param servings The number of servings the recipe yields.
      */
     public void showAddIngredientsDialog(String recipeName, String prepTime, String cookTime, int servings) {
         JDialog ingredientsDialog = new JDialog((Frame) null, "Add New Ingredients", true);
         ingredientsDialog.setSize(1000, 800);
         ingredientsDialog.setLocationRelativeTo(null);  // Center window on the screen
         ingredientsDialog.setLayout(new BoxLayout(ingredientsDialog.getContentPane(), BoxLayout.Y_AXIS));
 
         // Panel for the ingredient input fields
         JPanel inputPanel = new JPanel(new FlowLayout());
         JTextField nameField = new JTextField(10);
         JTextField quantityField = new JTextField(5);
         JComboBox<String> unitComboBox = new JComboBox<>(new String[]{"g", "kg", "ml", "l", "cup", "tbsp", "tsp", "pcs"});
         JTextField quantityToCupField = new JTextField(5);
         JTextField prepField = new JTextField(10);
         JTextField caloriesPerUnitField = new JTextField(5);
         JTextField caloriesPerCupField = new JTextField(5);
         JTextField totalCaloriesField = new JTextField(5);
         totalCaloriesField.setEditable(false); // Make this field read-only
         caloriesPerCupField.setEditable(false);
         quantityToCupField.setEditable(false);
         // Set preferred sizes for components
         nameField.setPreferredSize(new java.awt.Dimension(200, 30));
         quantityField.setPreferredSize(new java.awt.Dimension(100, 30));
         caloriesPerUnitField.setPreferredSize(new java.awt.Dimension(100, 30));
         caloriesPerCupField.setPreferredSize(new java.awt.Dimension(100, 30));
         totalCaloriesField.setPreferredSize(new java.awt.Dimension(100, 30));

         unitComboBox.setBackground(new Color(249, 205, 212)); 
 
         inputPanel.add(new JLabel("Name:"));
         inputPanel.add(nameField);
         inputPanel.add(new JLabel("Quantity:"));
         inputPanel.add(quantityField);
         inputPanel.add(new JLabel("Unit:"));
         inputPanel.add(unitComboBox);
         inputPanel.add(new JLabel("Preparation:"));
         inputPanel.add(prepField);
         inputPanel.add(new JLabel("Equivalent Cup:"));
         inputPanel.add(quantityToCupField);
         inputPanel.add(new JLabel("Calories per Unit:"));
         inputPanel.add(caloriesPerUnitField);
         inputPanel.add(new JLabel("Calories per Cup:"));
         inputPanel.add(caloriesPerCupField);
         inputPanel.add(new JLabel("Total Calories:"));
         inputPanel.add(totalCaloriesField);
 
         // Update totalCaloriesField when inputs change
         quantityField.addKeyListener(new KeyAdapter() {
             @Override
             public void keyReleased(KeyEvent e) {
                 updateTotalCaloriesField(caloriesPerUnitField, quantityField, totalCaloriesField, (String) unitComboBox.getSelectedItem());
                 updateQuantityToCupField(quantityField, quantityToCupField, (String) unitComboBox.getSelectedItem());
                 updateCaloriesPerCupField(caloriesPerUnitField, quantityField, caloriesPerCupField, (String) unitComboBox.getSelectedItem());
             }
         });
 
         caloriesPerUnitField.addKeyListener(new KeyAdapter() {
             @Override
             public void keyReleased(KeyEvent e) {
                 updateTotalCaloriesField(caloriesPerUnitField, quantityField, totalCaloriesField, (String) unitComboBox.getSelectedItem());
                 updateQuantityToCupField( quantityField, quantityToCupField, (String) unitComboBox.getSelectedItem());
                 updateCaloriesPerCupField(caloriesPerUnitField, quantityField, caloriesPerCupField, (String) unitComboBox.getSelectedItem());
             }
         });
 
         // Table to display ingredients
         String[] columnNames = {"Name", "Quantity", "Unit", "Preparation", "Equivalent Cup", "Calories/Unit", "Calories/Cup", "Total Calories"};
         DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
         JTable ingredientTable = new JTable(tableModel);
         ingredientTable.setFillsViewportHeight(true);
         ingredientTable.setSelectionBackground(new Color(255, 182, 193)); // Light pink
         ingredientTable.setSelectionForeground(Color.BLACK); // Optional: Change text color
 
         // ArrayList to hold ingredients
         ArrayList<Ingredient> ingredients = new ArrayList<>();
 
         // Add and Delete buttons
         JButton addIngredientButton = new JButton("Add Ingredient");
         addIngredientButton.setBackground(new Color(249, 205, 212)); // Light pink
         addIngredientButton.addActionListener(e -> {
             String name = nameField.getText();
             String quantityString = quantityField.getText();
             Float quantity = null;
              
             try {
                 quantity = Float.valueOf(quantityString);
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(ingredientsDialog, "Please enter a valid number for quantity (e.g., 1.5).");
                 return;
             }
 
             String preparation = prepField.getText();
             String unitString = (String) unitComboBox.getSelectedItem();
             Ingredient.Unit unit = Ingredient.fromString(unitString);
 
             double caloriesPerUnit;
             double quantityToCupNum;
             try {
                caloriesPerUnit = Double.parseDouble(caloriesPerUnitField.getText());
               
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(ingredientsDialog, "Please enter valid numbers for calories. Example: 23");
                 return;
             }
 
             if (!name.isEmpty() && quantity != null) {
                 // Add ingredient to ArrayList and table
                 Ingredient ingredient = new Ingredient(name, quantity, unit, preparation, caloriesPerUnit, caloriesPerUnit);
                 ingredients.add(ingredient);
                 quantityToCupNum = Double.parseDouble(quantityToCupField.getText());
                 double caloriesPerCup = ingredient.getCaloriesPerCup();
                 double totalCalories = ingredient.getTotalCalories();
                 tableModel.addRow(new Object[]{
                     name, quantity, unitString, preparation, decimalFormat.format(quantityToCupNum), decimalFormat.format(caloriesPerUnit), decimalFormat.format(caloriesPerCup), decimalFormat.format(totalCalories)
                 });
 
                 // Clear input fields for next input
                 nameField.setText("");
                 quantityField.setText("");
                 prepField.setText("");
                 caloriesPerCupField.setText("");
                 totalCaloriesField.setText("");
             } else {
                 JOptionPane.showMessageDialog(ingredientsDialog, "Please fill out the name and quantity.");
             }
         });
 
         JButton deleteIngredientButton = new JButton("Delete Ingredient");
         deleteIngredientButton.setBackground(new Color(249, 205, 212)); // Light pink
         deleteIngredientButton.addActionListener(e -> {
             int selectedRow = ingredientTable.getSelectedRow();
             if (selectedRow != -1) {
                 tableModel.removeRow(selectedRow);
                 ingredients.remove(selectedRow); // Remove the ingredient from the ArrayList
             } else {
                 JOptionPane.showMessageDialog(ingredientsDialog, "Please select an ingredient to delete.");
             }
         });
 
         JButton saveSelectedButton = new JButton("Save Selected");
         saveSelectedButton.setEnabled(false); // Initially disabled
         saveSelectedButton.setBackground(new Color(226, 117, 137)); // Pink
         saveSelectedButton.setForeground(Color.BLACK);
 
         saveSelectedButton.addActionListener(e -> {
             int selectedRow = ingredientTable.getSelectedRow();
             if (selectedRow != -1) {
                 // Update the selected ingredient (this is just a placeholder; you'd need to map back to your ingredient list)
                 String updatedQuantity = quantityField.getText();
                 String updatedPreparation = prepField.getText();
                 // Update logic here for the selected ingredient
                 JOptionPane.showMessageDialog(ingredientsDialog, "Selected ingredient updated!");
                 saveSelectedButton.setEnabled(false); // Disable the button after saving
             }
         });
 
         ingredientTable.getSelectionModel().addListSelectionListener(e -> {
             if (ingredientTable.getSelectedRow() != -1) {
                 saveSelectedButton.setEnabled(true); // Enable the button when a row is selected
             }
         });
 
         JButton nextButton = new JButton("Next: Add Instructions");
         nextButton.setBackground(new Color(226, 117, 137)); // Pink
         nextButton.setForeground(Color.BLACK);
         nextButton.addActionListener(e -> {
             ingredientsDialog.dispose(); // Close the ingredients dialog
             // Pass the ingredients ArrayList to the next dialog
             addInstructions.showAddInstructionsDialog(recipeName, prepTime, cookTime, servings, ingredients);
         });
 
         JPanel buttonPanel = new JPanel();
         buttonPanel.add(addIngredientButton);
         buttonPanel.add(deleteIngredientButton);
         buttonPanel.add(saveSelectedButton);
         buttonPanel.add(nextButton);

         JScrollPane scrollPane = new JScrollPane(ingredientTable);

        // Set the background color for the scroll pane and its viewport
        scrollPane.getViewport().setBackground(new Color(249, 205, 212)); // Light Pink for viewport
        scrollPane.setBackground(new Color(249, 205, 212)); // Light Pink for scroll pane

        // Set the scrollbars' background to a darker pink
        scrollPane.getVerticalScrollBar().setBackground(new Color(226, 117, 137)); // Dark Pink
        scrollPane.getHorizontalScrollBar().setBackground(new Color(226, 117, 137));
        
         ingredientsDialog.add(inputPanel, BorderLayout.WEST);
         ingredientsDialog.add(scrollPane, BorderLayout.CENTER);
         ingredientsDialog.add(buttonPanel, BorderLayout.SOUTH);
 
         ingredientsDialog.setVisible(true);
     }
 
     /**
      * Updates the total calories field based on user input for calories per unit and quantity.
      * 
      * @param caloriesPerUnitField The input field for calories per unit.
      * @param quantityField The input field for quantity.
      * @param totalCaloriesField The field to display the total calculated calories.
      * @param unitString The selected unit of measurement for the ingredient.
      */
     private void updateTotalCaloriesField(JTextField caloriesPerUnitField, JTextField quantityField, JTextField totalCaloriesField, String unitString) {
         try {
             double caloriesPerUnit = Double.parseDouble(caloriesPerUnitField.getText());
             Float quantity = Float.valueOf(quantityField.getText());
             Ingredient.Unit unit = Ingredient.fromString(unitString);
             double totalCalories = Ingredient.calculateTotalCalories(quantity, unit, caloriesPerUnit);
             totalCaloriesField.setText(decimalFormat.format(totalCalories));
         } catch (NumberFormatException ex) {
             totalCaloriesField.setText(""); // Clear the total calories field if invalid input
         }
     }

     private void updateCaloriesPerCupField(JTextField caloriesPerUnitField, JTextField quantityField, JTextField caloriesPerCupField, String unitString) {
        try {
            double caloriesPerUnit = Double.parseDouble(caloriesPerUnitField.getText());
            Float quantity = Float.valueOf(quantityField.getText());
            Ingredient.Unit unit = Ingredient.fromString(unitString);
            double caloriesPerCup = Ingredient.convertCaloriesPerUnitToCup(quantity, unit, caloriesPerUnit);
            caloriesPerCupField.setText(decimalFormat.format(caloriesPerCup));
         
        } catch (NumberFormatException ex) {
            caloriesPerCupField.setText(""); // Clear the total calories field if invalid input
        }
    }

    private void updateQuantityToCupField( JTextField quantityField, JTextField quantityToCupField, String unitString) {
        try {
            Float quantity = Float.valueOf(quantityField.getText());
            Ingredient.Unit unit = Ingredient.fromString(unitString);
            double caloriesPerCup = Ingredient.convertQuantityPerUnitToCup(quantity, unit);
            quantityToCupField.setText(decimalFormat.format(caloriesPerCup));
         
        } catch (NumberFormatException ex) {
            quantityToCupField.setText(""); // Clear the total calories field if invalid input
        }
    }
 }
 