/**
 * Package: com.jkp.recipe_manager
 * 
 * The AddIngredientDialog class provides a user interface for adding a new 
 * ingredient to the recipe in the Recipe Manager application. This code is used from the Edit Recipe option. It allows 
 * users to input ingredient details such as name, quantity, unit, preparation 
 * instructions, calories per cup, and calculates total calories based on the 
 * provided data.
 * 
 * <p>Features:</p>
 * - Input fields for ingredient name, quantity, unit, preparation, and calories per cup.
 * - Automatically calculates the total calories for the ingredient based on calories per cup.
 * - Validation for numeric input values to ensure correct data entry.
 * - Notifies the listener when a new ingredient is successfully added.
 * 
 * <p>Usage:</p>
 * This class should be instantiated and the dialog displayed as follows:
 * <pre>{@code
 * AddIngredientDialog dialog = new AddIngredientDialog(listener);
 * dialog.setVisible(true);
 * }</pre>
 * The listener can be implemented to handle updates when an ingredient is added:
 * <pre>{@code
 * dialog.setListener(updatedIngredient -> {
 *     // Actions after ingredient is added
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

public class AddIngredientDialog extends JDialog {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private JTextField nameField;
    private JTextField quantityField;
    private JComboBox<String> unitComboBox;
    private JTextField quantityToCupField , preparationField, caloriesPerUnitField, caloriesPerCupField, totalCaloriesField; 
    private JButton addButton;

    public AddIngredientDialog(IngredientUpdateListener listener) {
        setTitle("Add New Ingredient");
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
        nameField = new JTextField();
        gbc.gridx = 1;
        add(nameField, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Quantity:"), gbc);
        quantityField = new JTextField();
        gbc.gridx = 1;
        add(quantityField, gbc);

        // Unit
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Unit:"), gbc);
        String[] units = {"g", "kg", "ml", "l", "cup", "tbsp", "tsp", "pcs"};
        unitComboBox = new JComboBox<>(units);
        gbc.gridx = 1;
        add(unitComboBox, gbc);
        unitComboBox.setBackground(new Color(255, 182, 193)); // Ligh Pink

        // Preparation
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Preparation:"), gbc);
        preparationField = new JTextField();
        gbc.gridx = 1;
        add(preparationField, gbc);
   

        // Calories Per Unit
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Calories per Unit:"), gbc);
        caloriesPerUnitField = new JTextField();
        gbc.gridx = 1;
        add(caloriesPerUnitField, gbc);

        //Equivalent Cup
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Equivalent Cup:"), gbc);
        quantityToCupField = new JTextField();
        quantityToCupField.setEditable(false);
        gbc.gridx = 1;
        add(quantityToCupField, gbc);


        // Calories Per Cup
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Calories per Cup:"), gbc);
        caloriesPerCupField = new JTextField();
        caloriesPerCupField.setEditable(false);
        gbc.gridx = 1;
        add(caloriesPerCupField, gbc);
        // Total Calories (read-only)
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Total Calories"), gbc);
        totalCaloriesField = new JTextField();
        totalCaloriesField.setEditable(false);
        gbc.gridx = 1;
        add(totalCaloriesField, gbc);

        
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


        // Add Button
        addButton = new JButton("Add");
        addButton.setBackground(new Color(226, 117, 137)); // Pink 
        addButton.setForeground(Color.BLACK);
        addButton.addActionListener((ActionEvent e) -> {
            addIngredient(listener);
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(addButton, gbc);
    }

    private void addIngredient(IngredientUpdateListener listener) {
        try {
            String name = nameField.getText();
            Float quantity = Float.valueOf(quantityField.getText());
            String unitString  = (String) unitComboBox.getSelectedItem();
            Ingredient.Unit unit = Ingredient.fromString(unitString);
            String preparation = preparationField.getText();
            double caloriesPerUnit = Double.parseDouble(caloriesPerUnitField.getText());
            Ingredient newIngredient = new Ingredient(name, quantity, unit, preparation, caloriesPerUnit, caloriesPerUnit);
            listener.onIngredientUpdated(newIngredient);
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

  
    public interface IngredientUpdateListener {
        void onIngredientUpdated(Ingredient updatedIngredient);
    }
}
