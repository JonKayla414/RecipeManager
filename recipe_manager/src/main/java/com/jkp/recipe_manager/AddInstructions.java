/**
 * Package: com.jkp.recipe_manager
 * 
 * The AddInstructions class provides a graphical user interface (GUI) for adding step-by-step instructions 
 * to a recipe. It allows users to input preparation details and cooking steps, then saves the recipe 
 * to storage using the RecipeStorage utility.
 * 
 * <p>Features:</p>
 * <ul>
 *     <li>Dynamic GUI for adding multiple recipe steps</li>
 *     <li>Validation to ensure non-empty instructions</li>
 *     <li>Recipe saving functionality to a specified path</li>
 * </ul>
 * 
 * @author Jon-Kayla Pointer
 * @version 1.0.0
 */


 package com.jkp.recipe_manager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * The AddInstructions class manages the user interface for adding recipe instructions.
 */
public class AddInstructions {

    /**
     * The file path where recipe data will be saved.
     */
    private String RecipePaths = "./src/main/java/recipes/";

    /**
     * Displays a dialog to add instructions to a recipe.
     *
     * @param recipeName  The name of the recipe.
     * @param prepTime    The preparation time for the recipe.
     * @param cookTime    The cooking time for the recipe.
     * @param servings    The number of servings the recipe yields.
     * @param ingredients A list of ingredients required for the recipe.
     */
    void showAddInstructionsDialog(String recipeName, String prepTime, String cookTime, int servings, ArrayList<Ingredient> ingredients) {
        // Main dialog setup
        JDialog instructionsDialog = new JDialog((Frame) null, "Add New Instructions", true);
        instructionsDialog.setSize(1000, 800);
        instructionsDialog.setLayout(new BoxLayout(instructionsDialog.getContentPane(), BoxLayout.Y_AXIS));
        instructionsDialog.setLocationRelativeTo(null);

        // Step Table with Step Number and Details
        String[] columnNames = {"Step Number", "Step Details"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Step Details column is editable
            }
        };
        JTable stepTable = new JTable(tableModel);
        stepTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(stepTable);
        stepTable.setSelectionBackground(new Color(255, 182, 193)); // Light pink
        stepTable.setSelectionForeground(Color.BLACK); // Optional: Change text color
        scrollPane.setPreferredSize(new Dimension(580, 300));
        scrollPane.getVerticalScrollBar().setBackground(new Color(226, 117, 137)); //  pink
        scrollPane.getHorizontalScrollBar().setBackground(new Color(226, 117, 137));
        instructionsDialog.add(scrollPane);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();

        JButton addStepButton = new JButton("Add Step");
        JButton deleteStepButton = new JButton("Delete Step");
        JButton saveStepButton = new JButton("Save Step");
        JButton saveRecipeButton = new JButton("Save Recipe");
        addStepButton.setBackground(new Color(249, 205, 212));  // Light Pink
        deleteStepButton.setBackground(new Color(249, 205, 212));  // Light Pink
        saveStepButton.setBackground(new Color(249, 205, 212));  // Light Pink

        saveRecipeButton.setBackground(new Color(226, 117, 137)); //  Pink

        addStepButton.setPreferredSize(new Dimension(120, 30));
        deleteStepButton.setPreferredSize(new Dimension(120, 30));
        saveStepButton.setPreferredSize(new Dimension(120, 30));
        saveRecipeButton.setPreferredSize(new Dimension(120, 30));

        deleteStepButton.setEnabled(false);
        saveStepButton.setEnabled(false);

        buttonPanel.add(addStepButton);
        buttonPanel.add(deleteStepButton);
        buttonPanel.add(saveStepButton);
        buttonPanel.add(saveRecipeButton);
        instructionsDialog.add(buttonPanel);

        // Add Step Functionality
        addStepButton.addActionListener(e -> {
            int stepNumber = tableModel.getRowCount() + 1;
            tableModel.addRow(new Object[]{stepNumber, ""});
        });

        // Delete Step Functionality
        deleteStepButton.addActionListener(e -> {
            int selectedRow = stepTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                renumberSteps(tableModel);
            }
        });

        // Enable Delete Step when a row is selected
        stepTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean rowSelected = stepTable.getSelectedRow() != -1;
                deleteStepButton.setEnabled(rowSelected);
                saveStepButton.setEnabled(rowSelected && 
                    !tableModel.getValueAt(stepTable.getSelectedRow(), 1).toString().trim().isEmpty());
            }
        });

        // Save Step Functionality
        saveStepButton.addActionListener(e -> {
            int selectedRow = stepTable.getSelectedRow();
            if (selectedRow != -1) {
                String stepDetails = tableModel.getValueAt(selectedRow, 1).toString().trim();
                if (!stepDetails.isEmpty()) {
                    JOptionPane.showMessageDialog(instructionsDialog, "Step " + (selectedRow + 1) + " saved!");
                }
            }
        });

        // Save Recipe Functionality
        saveRecipeButton.addActionListener(e -> {
            try {
                List<String> instructions = new ArrayList<>();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String stepDetail = tableModel.getValueAt(i, 1).toString().trim();
                    if (!stepDetail.isEmpty()) {
                        instructions.add(stepDetail);
                    }
                }

                // Save recipe to storage
                Recipe newRecipe = new Recipe(recipeName, ingredients, instructions, servings, prepTime, cookTime);
                RecipeStorage.saveRecipe(newRecipe, RecipePaths);
                JOptionPane.showMessageDialog(instructionsDialog, "Recipe saved successfully!");
                instructionsDialog.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(instructionsDialog, "Error saving recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show dialog
        instructionsDialog.setVisible(true);
    }

    // Renumber steps after deletion
    private void renumberSteps(DefaultTableModel tableModel) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(i + 1, i, 0);
        }
    }
}
