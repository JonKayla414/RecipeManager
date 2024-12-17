/**
 * Package: com.jkp.recipe_manager
 * 
 * The EditRecipe class provides a user interface for modifying an existing recipe 
 * in the Recipe Manager application. It allows users to edit recipe details, 
 * manage ingredients, and update step-by-step instructions.
 * 
 * <p>Features:</p>
 * - Edit the recipe name, servings, preparation time, and cooking time.
 * - Add, edit, or delete ingredients in a dynamic table.
 * - Manage step-by-step instructions in a structured table.
 * - Save changes to persist the updated recipe data.
 * 
 * <p>Usage:</p>
 * This class should be instantiated with a {@code Recipe} object. The edit dialog
 * is initialized and displayed automatically:
 * <pre>{@code
 * EditRecipe editor = new EditRecipe(recipe);
 * }</pre>
 * To listen for successful edits, a listener can be added:
 * <pre>{@code
 * editor.addListener(() -> {
 *     // Actions after recipe is edited
 * });
 * }</pre>
 * 
 * <p>Dependencies:</p>
 * - Recipe.java
 * - Ingredient.java
 * - RecipeStorage.java
 * - AddIngredientDialog.java
 * - EditIngredientDialog.java
 * 
 * @author  Jon-Kayla Pointer
 * @version 1.0.0
 */


package com.jkp.recipe_manager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * EditRecipe allows users to edit an existing recipe, including name, servings, ingredients, and instructions.
 * It provides a graphical user interface to modify recipe details and saves changes back to storage.
 */


public class EditRecipe {
    private Recipe recipe;
    private JFrame editRecipeDialog;
    private JTextField nameField;
    private JTable ingredientTable, instructionsTable;
    private DefaultTableModel ingredientTableModel, instructionsTableModel;
    private JComboBox<String> prepTimeUnitComboBox, cookTimeUnitComboBox, prepTimeField, cookTimeField; 
    private JComboBox<Integer> servingsField;
    private String RecipePaths = "./src/main/java/recipes/";
    private Runnable listener;
    /**
     * Sets a listener that will run when recipe editing is successfully completed.
     * @param listener The callback to run after saving changes.
     */
    public void addListener(Runnable listener) {
        this.listener = listener;
    }

    
    /**
     * Notifies the listener that the recipe has been edited.
     */
    private void onRecipeEdited() {
        if (listener != null) {
            listener.run();  // Execute the listener
        }
    }

    /**
     * Creates an EditRecipe instance for a specific recipe.
     * @param recipe The recipe to edit.
     */
    public EditRecipe(Recipe recipe) {
        this.recipe = recipe;
        initialize();
    }
   
    /**
     * Initializes the Edit Recipe dialog with components for editing the recipe's details.
     * 
     * <p>This method sets up the {@code EditRecipeDialog} window, including the layout and 
     * UI components such as text fields, combo boxes, tables for ingredients and instructions, 
     * and buttons for user actions. It pre-populates the components with the current recipe data 
     * and configures actions for adding, editing, and deleting ingredients and instructions. 
     * It also includes a button to save the changes made to the recipe.</p>
     * 
     * <p>Actions performed:</p>
     * - Initializes the Edit Recipe dialog with a title, size, and layout.
     * - Adds UI components for recipe name, servings, prep time, cook time, and ingredient table.
     * - Configures buttons for adding, editing, and deleting ingredients and instructions.
     * - Enables or disables action buttons based on user selection (e.g., edit ingredient).
     * - Displays the dialog window for user interaction.
     */

    private void initialize() {
        editRecipeDialog = new JFrame("Edit Recipe");
        editRecipeDialog.setSize(1000, 800);
        editRecipeDialog.setLayout(new GridBagLayout());
        editRecipeDialog.getContentPane().setBackground(new Color(249, 205, 212)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Recipe Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        editRecipeDialog.add(new JLabel("Recipe Name:"), gbc);
        nameField = new JTextField(recipe.getName());
        gbc.gridx = 1;
        editRecipeDialog.add(nameField, gbc);

        // Servings
        gbc.gridx = 0;
        gbc.gridy = 1;
        editRecipeDialog.add(new JLabel("Servings:"), gbc);
        Integer[] servingsArray = IntStream.rangeClosed(1, 99).boxed().toArray(Integer[]::new);
        servingsField = new JComboBox<>(servingsArray);
        servingsField.setSelectedItem(recipe.getServings());
        servingsField.setBackground(new Color(249, 205, 212));
        gbc.gridx = 1;
        editRecipeDialog.add(servingsField, gbc);

        // Prep Time and Cook Time
        gbc.gridx = 0;
        gbc.gridy = 2;
        editRecipeDialog.add(new JLabel("Prep Time:"), gbc);
        prepTimeField = new JComboBox<>(IntStream.rangeClosed(1, 99).mapToObj(String::valueOf).toArray(String[]::new));
        prepTimeField.setBackground(new Color(249, 205, 212));
        prepTimeUnitComboBox = new JComboBox<>(new String[]{"minutes", "hours"});
        String[] prepItems = recipe.getPrepTime().split(" ");
        prepTimeField.setSelectedItem(prepItems[0]);
        prepTimeUnitComboBox.setSelectedItem(prepItems[1]);
        prepTimeUnitComboBox.setBackground(new Color(249, 205, 212)); 
        gbc.gridx = 1;
        editRecipeDialog.add(prepTimeField, gbc);
        gbc.gridx = 2;
        editRecipeDialog.add(prepTimeUnitComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        editRecipeDialog.add(new JLabel("Cook Time:"), gbc);
        cookTimeField = new JComboBox<>(IntStream.rangeClosed(1, 99).mapToObj(String::valueOf).toArray(String[]::new));
        cookTimeField.setBackground(new Color(249, 205, 212));
        cookTimeUnitComboBox = new JComboBox<>(new String[]{"minutes", "hours"});
        cookTimeUnitComboBox.setBackground(new Color(249, 205, 212)); 
        String[] cookItems = recipe.getCookTime().split(" ");
        cookTimeField.setSelectedItem(cookItems[0]);
        cookTimeUnitComboBox.setSelectedItem(cookItems[1]);
        gbc.gridx = 1;
        editRecipeDialog.add(cookTimeField, gbc);
        gbc.gridx = 2;
        editRecipeDialog.add(cookTimeUnitComboBox, gbc);

        // Ingredient Table (Only Name)
        ingredientTableModel = new DefaultTableModel(new String[]{"Ingredient Name"}, 0);
        ingredientTable = new JTable(ingredientTableModel);
        ingredientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientTable.setSelectionBackground(new Color(249, 205, 212));
        
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientTableModel.addRow(new Object[]{ingredient.getName()});
        }

        JScrollPane ingredientScrollPane = new JScrollPane(ingredientTable);
        ingredientScrollPane.setPreferredSize(new Dimension(500, 150));
        ingredientScrollPane.setBackground(new Color(226, 117, 137)); // Pink);
         JScrollBar verticalScrollBar = ingredientScrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = ingredientScrollPane.getHorizontalScrollBar();
        
        // Set the background color of scrollbars to light pink
        verticalScrollBar.setBackground(new Color(255, 182, 193)); // Light pink
        horizontalScrollBar.setBackground(new Color(255, 182, 193)); // Light pink
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        editRecipeDialog.add(ingredientScrollPane, gbc);

       // Buttons for Ingredient Actions
        JButton editIngredientButton = new JButton("Edit Selected Ingredient");
        JButton addIngredientButton = new JButton("Add Ingredient");
        JButton deleteIngredientButton = new JButton("Delete Selected Ingredient");
        editIngredientButton.setBackground(new Color(249, 205, 212));
        editIngredientButton.setForeground(Color.BLACK);
        addIngredientButton.setBackground(new Color(249, 205, 212));
        addIngredientButton.setForeground(Color.BLACK);
        deleteIngredientButton.setBackground(new Color(249, 205, 212));
        deleteIngredientButton.setForeground(Color.BLACK);

        gbc.gridy = 5; // Set the row position for buttons
        gbc.gridwidth = 1; // Set the grid width for the buttons
        gbc.gridx = 0; // First column for edit button
        editIngredientButton.addActionListener(e -> editSelectedIngredient());
        editIngredientButton.setEnabled(false); // Disable until an ingredient is selected
        ingredientTable.getSelectionModel().addListSelectionListener(e -> {
            editIngredientButton.setEnabled(ingredientTable.getSelectedRow() != -1);
        });
        editRecipeDialog.add(editIngredientButton, gbc); // Add edit button

        gbc.gridx = 1; // Second column for add button
        addIngredientButton.addActionListener(e -> addNewIngredient());
        editRecipeDialog.add(addIngredientButton, gbc); // Add add button

        gbc.gridx = 2; // Third column for delete button
        deleteIngredientButton.addActionListener(e -> deleteIngredientRow());
        editRecipeDialog.add(deleteIngredientButton, gbc); // Add delete button
        // Instructions Table
        instructionsTableModel = new DefaultTableModel(new String[]{"Step Number", "Instruction"}, 0);
        instructionsTable = new JTable(instructionsTableModel);
        instructionsTable.setSelectionBackground(new Color(226, 117, 137)); // Pink);
        updateInstructionsTable();

        JScrollPane instructionsScrollPane = new JScrollPane(instructionsTable);
        instructionsScrollPane.setPreferredSize(new Dimension(500, 150));
        instructionsScrollPane.setBackground(new Color(249, 205, 212));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        editRecipeDialog.add(instructionsScrollPane, gbc);

        // Buttons for Instruction Actions
        JButton addStepButton = new JButton("Add Step");
        JButton deleteStepButton = new JButton("Delete Selected Step");
        addStepButton.setBackground(new Color(249, 205, 212));
        deleteStepButton.setBackground(new Color(249, 205, 212));
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        editRecipeDialog.add(addStepButton, gbc);
        gbc.gridx = 1;
        editRecipeDialog.add(deleteStepButton, gbc);

        // Instruction Button Actions
        addStepButton.addActionListener(e -> addInstructionRow());
        deleteStepButton.addActionListener(e -> deleteInstructionRow());

        // Save Changes Button
        JButton saveButton = new JButton("Save Changes");
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        editRecipeDialog.add(saveButton, gbc);

        // Save Button Action
        saveButton.setBackground(new Color(226, 117, 137));
        saveButton.setForeground(Color.BLACK);
        saveButton.addActionListener(this::saveChanges);

        editRecipeDialog.setVisible(true);
    }

    /**
     * Opens a dialog to edit the selected ingredient in the recipe.
     * 
     * <p>This method checks if a row is selected in the ingredient table. If a row 
     * is selected, it retrieves the selected ingredient from the recipe's ingredient list 
     * and opens an {@code EditIngredientDialog} to allow the user to modify the ingredient's details. 
     * Once the ingredient is updated, the recipe's ingredient list and the ingredient table are both 
     * updated to reflect the changes.</p>
     * 
     * <p>Actions performed:</p>
     * - Retrieves the selected ingredient from the recipe's ingredient list.
     * - Opens the {@code EditIngredientDialog} for the user to edit the ingredient.
     * - Updates the recipe's ingredient list and the ingredient table model with the modified ingredient.
     */
    private void editSelectedIngredient() {
        int selectedRow = ingredientTable.getSelectedRow();
        if (selectedRow != -1) {
            String ingredientName = (String) ingredientTableModel.getValueAt(selectedRow, 0);
            Ingredient selectedIngredient = recipe.getIngredients().get(selectedRow);
            new EditIngredientDialog(selectedIngredient, updatedIngredient -> {
                // Update the ingredient in the recipe
                recipe.getIngredients().set(selectedRow, updatedIngredient);
                ingredientTableModel.setValueAt(updatedIngredient.getName(), selectedRow, 0);
            }).setVisible(true);
        }
    }

    /**
     * Deletes the selected ingredient from the recipe.
     * 
     * <p>This method checks if a row is selected in the ingredient table. If a row 
     * is selected, it removes the corresponding ingredient from both the table model 
     * and the recipe's ingredient list.</p>
     * 
     * <p>Actions performed:</p>
     * - Removes the selected row from the ingredient table model.
     * - Removes the corresponding ingredient from the recipe's ingredient list.
     */
    private void deleteIngredientRow() {
        int selectedRow = ingredientTable.getSelectedRow();
        if (selectedRow != -1) {
            ingredientTableModel.removeRow(selectedRow);
            recipe.getIngredients().remove(selectedRow); // Also remove from recipe
        }
    }

    /**
     * Updates the instructions table to reflect the current list of instructions in the recipe.
     * 
     * <p>This method clears the current rows in the instructions table and then iterates 
     * through the list of instructions from the recipe. For each instruction, it adds 
     * a new row to the table model with a sequential index and the instruction's content.</p>
     * 
     * <p>Actions performed:</p>
     * - Clears all existing rows in the instructions table model.
     * - Iterates over the recipe's instructions list and adds each instruction as a new row to the table.
     * - Updates the instructions table to display the updated list of instructions.
     */
    private void updateInstructionsTable() {
        instructionsTableModel.setRowCount(0); // Clear current rows
        List<String> instructions = recipe.getInstructions();
        for (int i = 0; i < instructions.size(); i++) {
            instructionsTableModel.addRow(new Object[]{i + 1, instructions.get(i)});
    }
    }

    /**
     * Adds a new empty instruction row to the instructions table.
     * 
     * <p>This method adds a new row to the instructions table model. The new row 
     * consists of a sequential index (calculated as the current row count + 1) 
     * and an empty string to represent the new instruction's content.</p>
     * 
     * <p>Actions performed:</p>
     * - Adds a new row to the instructions table model with a sequential index and an empty instruction.
     * - Updates the instructions table to display the new row.
     */
    private void addInstructionRow() {
        instructionsTableModel.addRow(new Object[]{instructionsTableModel.getRowCount() + 1, ""});
    }

    /**
     * Deletes the selected instruction from the recipe.
     * 
     * <p>This method checks if a row is selected in the instructions table. If a row is 
     * selected, it removes the corresponding instruction both from the table model 
     * and the recipe's instructions list. The table is then updated to reflect 
     * the removal of the instruction.</p>
     * 
     * <p>Actions performed:</p>
     * - Removes the selected row from the instructions table model.
     * - Removes the corresponding instruction from the recipe's instructions list.
     * - Updates the instructions table to reflect the changes.
     */
    private void deleteInstructionRow() {
        int selectedRow = instructionsTable.getSelectedRow();
        if (selectedRow != -1) {
            instructionsTableModel.removeRow(selectedRow);
            recipe.getInstructions().remove(selectedRow);
            updateInstructionsTable();
        }
    }
    /**
     * Opens a dialog to add a new ingredient to the recipe.
     * 
     * <p>This method launches the {@code AddIngredientDialog}, which allows the user 
     * to input details for a new ingredient. Once the ingredient is confirmed, 
     * it is added to the recipe's ingredient list and displayed in the ingredient table.</p>
     * 
     * <p>Actions performed:</p>
     * - Displays the {@code AddIngredientDialog} for user input.
     * - Updates the recipe's ingredient list with the new ingredient.
     * - Adds the new ingredient's name to the ingredient table model for display.
     */
    private void addNewIngredient() {
    new AddIngredientDialog(updatedIngredient -> {
        // Add the new ingredient to the recipe
        recipe.getIngredients().add(updatedIngredient);
        ingredientTableModel.addRow(new Object[]{updatedIngredient.getName()});
    }).setVisible(true);
    }

    /**
     * Saves the updated recipe details entered by the user.
     * 
     * <p>This method gathers all modified recipe information, including the name, 
     * servings, preparation time, cooking time, ingredients, and instructions. 
     * The data is validated, updated in the {@code Recipe} object, and persisted 
     * using {@code RecipeStorage.saveRecipe}. If any errors occur during saving, 
     * an error message is displayed to the user.</p>
     * 
     * <p>Actions performed:</p>
     * - Retrieves and validates input from text fields, combo boxes, and tables.
     * - Updates the {@code Recipe} object's attributes.
     * - Saves the updated recipe using {@code RecipeStorage}.
     * - Displays success or error messages to the user.
     * - Notifies any listener about the successful edit and closes the edit dialog.
     * 
     * @param event The {@code ActionEvent} triggered by the save button.
     */

    private void saveChanges(ActionEvent event) {
        try {
            String newName = nameField.getText();
            int newServings = Integer.parseInt(servingsField.getSelectedItem() + "");
            String newPrepTime = prepTimeField.getSelectedItem() + " " + prepTimeUnitComboBox.getSelectedItem();
            String newCookTime = cookTimeField.getSelectedItem() + " " + cookTimeUnitComboBox.getSelectedItem();

            // Update recipe details
            recipe.setName(newName);
            recipe.setServings(newServings);
            recipe.setPrepTime(newPrepTime);
            recipe.setCookTime(newCookTime);
            // Grab the updated instructions from the table and set them in the recipe
            List<String> updatedInstructions = new ArrayList<>();
            for (int i = 0; i < instructionsTableModel.getRowCount(); i++) {
                String instruction = (String) instructionsTableModel.getValueAt(i, 1); // Get instruction text
                updatedInstructions.add(instruction); // Add to the updated instructions list
                if (instructionsTable.isEditing()) {
                instructionsTable.getCellEditor().stopCellEditing(); // Commit the editing
                }
            }
            recipe.setInstructions(updatedInstructions); // Use the setInstructions method

            
            RecipeStorage.saveRecipe(recipe, RecipePaths); // Save the recipe


            JOptionPane.showMessageDialog(editRecipeDialog, "Recipe saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            onRecipeEdited();
            editRecipeDialog.dispose(); // Close the dialog
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(editRecipeDialog, "Error saving recipe: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
