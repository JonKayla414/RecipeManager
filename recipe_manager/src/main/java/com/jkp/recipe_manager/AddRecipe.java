/**
 * Package: com.jkp.recipe_manager
 * 
 * The AddRecipe class provides a user interface for creating a new recipe 
 * in the Recipe Manager application. It allows users to input the recipe's 
 * basic details such as name, preparation time, cooking time, and servings, 
 * and then proceed to add ingredients for the recipe.
 * 
 * <p>Features:</p>
 * - Input fields for recipe name, preparation time, cooking time, and servings.
 * - Dynamic input fields for time and servings with combo box selections.
 * - Navigate to the ingredients section after entering recipe details.
 * - Supports listener functionality to trigger actions after a recipe is added.
 * 
 * <p>Usage:</p>
 * This class should be instantiated and the dialog displayed as follows:
 * <pre>{@code
 * AddRecipe addRecipe = new AddRecipe();
 * addRecipe.showAddRecipeDialog();
 * }</pre>
 * To listen for the successful addition of a recipe, a listener can be added:
 * <pre>{@code
 * addRecipe.addListener(() -> {
 *     // Actions after the recipe is successfully added
 * });
 * }</pre>
 * 
 * <p>Dependencies:</p>
 * - AddIngredients.java
 * 
 * @author  Jon-Kayla Pointer
 * @version 1.0.0
 */

 package com.jkp.recipe_manager;

 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.FlowLayout;
 import java.awt.Frame;
 import java.awt.GridBagConstraints;
 import java.awt.GridBagLayout;
 import java.awt.Insets;
 import java.awt.Toolkit;
 
 import javax.swing.JButton;
 import javax.swing.JComboBox;
 import javax.swing.JDialog;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JTextField;
 
 public class AddRecipe {
     private final AddIngredients addIngredients = new AddIngredients();
     private Runnable listener;
 
     public void addListener(Runnable listener) {
         this.listener = listener;
    
     }
 
     private void onRecipeAdded() {
         if (listener != null) {
             listener.run();
         }
     }
 
     public void showAddRecipeDialog() {
         JDialog addRecipeDialog = new JDialog((Frame) null, "Add New Recipe", true);
         
         // Set dialog size and center it
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(addRecipeDialog.getGraphicsConfiguration());
         int usableWidth = screenSize.width - screenInsets.left - screenInsets.right;
         int usableHeight = screenSize.height - screenInsets.top - screenInsets.bottom;
         addRecipeDialog.setSize(usableWidth / 2, usableHeight / 2);
         addRecipeDialog.setLocationRelativeTo(null);
 
         // Set layout
         JPanel mainPanel = new JPanel(new GridBagLayout());
         mainPanel.setBackground(Color.WHITE);
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 10, 10, 10);
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gbc.weightx = 1.0;
 
         // Recipe Name
         gbc.gridx = 0;
         gbc.gridy = 0;
         mainPanel.add(new JLabel("Name:"), gbc);
 
         gbc.gridx = 1;
         JTextField nameField = new JTextField();
         nameField.setPreferredSize(new Dimension(200, 24)); // Set smaller height
         mainPanel.add(nameField, gbc);
 
         // Prep Time
         gbc.gridx = 0;
         gbc.gridy = 1;
         mainPanel.add(new JLabel("Prep Time:"), gbc);
 
         gbc.gridx = 1;
         JPanel prepTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
         prepTimePanel.setBackground(Color.WHITE);
         Integer[] numbers = new Integer[99];
         for (int i = 1; i <= 99; i++) numbers[i - 1] = i;
 
         JComboBox<Integer> prepTimeNumber = new JComboBox<>(numbers);
         prepTimeNumber.setBackground(new Color(249, 205, 212));  // Light Pink
         prepTimeNumber.setForeground(Color.BLACK);
         prepTimePanel.add(prepTimeNumber);
 
         String[] timeUnits = {"minutes", "hours"};
         JComboBox<String> prepTimeUnit = new JComboBox<>(timeUnits);
         prepTimeUnit.setBackground(new Color(249, 205, 212)); // Light Pink
         prepTimeUnit.setForeground(Color.BLACK);
         prepTimePanel.add(prepTimeUnit);
 
         mainPanel.add(prepTimePanel, gbc);
 
         // Cook Time
         gbc.gridx = 0;
         gbc.gridy = 2;
         mainPanel.add(new JLabel("Cook Time:"), gbc);
 
         gbc.gridx = 1;
         JPanel cookTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
         cookTimePanel.setBackground(Color.WHITE);
         JComboBox<Integer> cookTimeNumber = new JComboBox<>(numbers);
         cookTimeNumber.setBackground(new Color(249, 205, 212)); // Light Pink
         cookTimeNumber.setForeground(Color.BLACK);
         cookTimePanel.add(cookTimeNumber);
 
         JComboBox<String> cookTimeUnit = new JComboBox<>(timeUnits);
         cookTimeUnit.setBackground(new Color(249, 205, 212)); // Light Pink
         cookTimeUnit.setForeground(Color.BLACK);
         cookTimePanel.add(cookTimeUnit);
 
         mainPanel.add(cookTimePanel, gbc);
 
         // Servings
         gbc.gridx = 0;
         gbc.gridy = 3;
         mainPanel.add(new JLabel("Servings:"), gbc);
 
         gbc.gridx = 1;
         JComboBox<Integer> servingNumber = new JComboBox<>(numbers);
         servingNumber.setBackground(new Color(249, 205, 212)); // Light Pink
         servingNumber.setForeground(Color.BLACK);
         mainPanel.add(servingNumber, gbc);
 
         // Next Button on Right
         gbc.gridx = 1;
         gbc.gridy = 4;
         gbc.anchor = GridBagConstraints.EAST; // Align to right
         JButton nextButton = new JButton("Next");
         nextButton.setBackground(new Color(226, 117, 137)); // Pink
         nextButton.setForeground(Color.BLACK);
         nextButton.setFocusPainted(false);
         nextButton.setPreferredSize(new Dimension(100, 30));
         mainPanel.add(nextButton, gbc);
 
         // Action Listener
         nextButton.addActionListener(e -> {
             String recipeName = nameField.getText();
             int userPrepNumber = (Integer) prepTimeNumber.getSelectedItem();
             String userPrepTimeUnit = (String) prepTimeUnit.getSelectedItem();
             String userPrepField = userPrepNumber + " " + userPrepTimeUnit;
 
             int userCookNumber = (Integer) cookTimeNumber.getSelectedItem();
             String userCookTimeUnit = (String) cookTimeUnit.getSelectedItem();
             String userCookField = userCookNumber + " " + userCookTimeUnit;
 
             int userServingSize = (Integer) servingNumber.getSelectedItem();
 
             // Go to ingredients panel
             addIngredients.showAddIngredientsDialog(recipeName, userPrepField, userCookField, userServingSize);
             onRecipeAdded();
             addRecipeDialog.dispose();
         });
 
         // Add main panel to dialog
         addRecipeDialog.add(mainPanel);
         addRecipeDialog.setVisible(true);
     }
 }
 