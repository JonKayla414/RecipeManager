/**
 * Package: com.jkp.recipe_manager
 * 
 * The EditIngredientDialog class provides a user interface for editing the details 
 * of an existing ingredient in the Recipe Manager application. It allows users to 
 * modify ingredient information such as name, quantity, unit, preparation, and 
 * calories per unit.
 * 
 * <p>Features:</p>
 * - Edit the ingredient name, quantity, unit, preparation, and calories per unit.
 * - Automatically updates total calories based on calories per unit and quantity.
 * - Validates numeric input to ensure correct data entry.
 * - Notifies the listener when an ingredient is successfully saved.
 * 
 * <p>Usage:</p>
 * This class should be instantiated with an {@code Ingredient} object and a listener
 * for handling updates. The dialog is initialized and displayed automatically:
 * <pre>{@code
 * EditIngredientDialog editor = new EditIngredientDialog(ingredient, listener);
 * editor.setVisible(true);
 * }</pre>
 * The listener can be implemented to handle updates when an ingredient is edited:
 * <pre>{@code
 * editor.setListener(updatedIngredient -> {
 *     // Actions after ingredient is updated
 * });
 * }</pre>
 * 
 * <p>Dependencies:</p>
 * - Ingredient.java
 * 
 * @author  Jon-Kayla Pointer
 * @version 1.0.0
 */

package com.jkp.recipe_manager;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditIngredientDialog extends JDialog {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private Ingredient ingredient;
    private JTextField nameField;
    private JTextField quantityField;
    private JComboBox<String> unitComboBox;
    private JTextField preparationField;
    private JTextField quantityToCupField;
    private JTextField caloriesPerUnitField;
    private JTextField caloriesPerCupField;
    private JTextField totalCaloriesField;
    private JButton saveButton;

    public EditIngredientDialog(Ingredient ingredient, IngredientUpdateListener listener) {
        this.ingredient = ingredient;
        setTitle("Edit Ingredient");
        setSize(1000, 800);
        setModal(true);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(249, 205, 212));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Ingredient Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        nameField = new JTextField(ingredient.getName());
        gbc.gridx = 1;
        add(nameField, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Quantity:"), gbc);
        quantityField = new JTextField(String.valueOf(ingredient.getQuantity()));
        gbc.gridx = 1;
        add(quantityField, gbc);

        // Unit
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Unit:"), gbc);
        String[] units = {"g", "kg", "ml", "l", "cup", "tbsp", "tsp", "pcs"};
        unitComboBox = new JComboBox<>(units);
        unitComboBox.setBackground(new Color(255, 182, 193)); // Light Pink 
        unitComboBox.setSelectedItem(ingredient.getUnit());
        gbc.gridx = 1;
        add(unitComboBox, gbc);

        // Preparation
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Preparation:"), gbc);
        preparationField = new JTextField(ingredient.getPreparation());
        gbc.gridx = 1;
        add(preparationField, gbc);
        // Quantity Per Unit
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Equivalent Cup:"), gbc);
      
        quantityToCupField = new JTextField(String.valueOf(ingredient.getQuantityEquivalent()));
        quantityToCupField.setEditable(false);
        gbc.gridx = 1;
        add(quantityToCupField, gbc);

        // Calories Per Unit
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Calories per Unit:"), gbc);
 
        caloriesPerUnitField = new JTextField(String.valueOf(ingredient.getCaloriesPerUnit()));
        gbc.gridx = 1;
        add(caloriesPerUnitField, gbc);

        // Calories Per Cup
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Calories per Cup:"), gbc);
        caloriesPerCupField = new JTextField(String.valueOf(ingredient.getCaloriesPerCup()));
        caloriesPerCupField.setEditable(false);
        gbc.gridx = 1;
        add(caloriesPerCupField, gbc);

        // Total Calories (read-only)
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Total Calories"), gbc);
        totalCaloriesField = new JTextField();
        totalCaloriesField.setEditable(false);
        totalCaloriesField.setText(String.valueOf(ingredient.getTotalCalories()));
        gbc.gridx = 1;
        add(totalCaloriesField, gbc);

        // Update totalCaloriesField when inputs change
        quantityField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            updateQuantityToCupField(caloriesPerUnitField, quantityField, quantityToCupField, (String) unitComboBox.getSelectedItem());
                updateTotalCaloriesField(caloriesPerUnitField, quantityField, totalCaloriesField, (String) unitComboBox.getSelectedItem());
                updateCaloriesPerCupField(caloriesPerUnitField, quantityField, caloriesPerCupField, (String) unitComboBox.getSelectedItem());
         }
        });

        caloriesPerUnitField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateQuantityToCupField(caloriesPerUnitField, quantityField, quantityToCupField, (String) unitComboBox.getSelectedItem());
                updateTotalCaloriesField(caloriesPerUnitField, quantityField, totalCaloriesField, (String) unitComboBox.getSelectedItem());
                updateCaloriesPerCupField(caloriesPerUnitField, quantityField, caloriesPerCupField, (String) unitComboBox.getSelectedItem());
            }
        });

        // Save Button
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(226, 117, 137)); // Pink
        saveButton.setForeground(Color.BLACK);
        saveButton.addActionListener((ActionEvent e) -> {
            saveIngredient(listener);
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        add(saveButton, gbc);
    }
    
    /**
     * Saves the ingredient with the updated values entered by the user in the dialog.
     * 
     * This method retrieves the ingredient details from the input fields, validates the 
     * entered values (including name, quantity, unit, preparation, and calories per unit), 
     * and then creates a new {@link Ingredient} object. The listener is notified with the 
     * updated ingredient. If any of the values are invalid (e.g., non-numeric input for 
     * quantity or calories), an error message is shown to the user.
     * 
     * <p>Usage:</p>
     * <pre>{@code
     * saveIngredient(listener);
     * }</pre>
     * 
     * @param listener the listener to notify with the updated ingredient
     * @throws NumberFormatException if the quantity or calories per unit fields contain 
     *         invalid numeric values
     */

    private void saveIngredient(IngredientUpdateListener listener) {
        try {
            String name = nameField.getText();
            Float quantity = Float.valueOf(quantityField.getText());
            String unitString = (String) unitComboBox.getSelectedItem();
            Ingredient.Unit unit = Ingredient.fromString(unitString);

            String preparation = preparationField.getText();
            double caloriesPerCup = Double.parseDouble(caloriesPerCupField.getText());

            Ingredient updatedIngredient = new Ingredient(name, quantity, unit, preparation, caloriesPerCup, caloriesPerCup);
            listener.onIngredientUpdated(updatedIngredient);
            dispose(); // Close dialog
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
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

   private void updateQuantityToCupField(JTextField caloriesPerUnitField, JTextField quantityField, JTextField quantityToCupField, String unitString) {
    try {
      
        Float quantity = Float.valueOf(quantityField.getText());
        Ingredient.Unit unit = Ingredient.fromString(unitString);
        double caloriesPerCup = Ingredient.convertQuantityPerUnitToCup(quantity, unit);
        quantityToCupField.setText(decimalFormat.format(caloriesPerCup));
     
    } catch (NumberFormatException ex) {
        quantityToCupField.setText(""); // Clear the total calories field if invalid input
    }
        }

    /**
     * Interface for listening to updates of ingredient data.
     * 
     * This interface is used to define a callback method that will be invoked when 
     * an ingredient is updated. Implementing classes should provide the logic for 
     * handling the updated ingredient, such as updating the ingredient list or saving 
     * the changes to a database.
     * 
     * <p>Usage:</p>
     * <pre>{@code
     * IngredientUpdateListener listener = new IngredientUpdateListener() {
     *     @Override
     *     public void onIngredientUpdated(Ingredient updatedIngredient) {
     *         // Handle the updated ingredient
     *     }
     * };
     * }</pre>
     * 
     * @see Ingredient
     */
    public interface IngredientUpdateListener {
        void onIngredientUpdated(Ingredient updatedIngredient);
    }
}
