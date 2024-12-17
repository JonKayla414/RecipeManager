/**
 * Package: com.jkp.recipe_manager
 * 
 * The RecipeManagerGUI class provides the graphical user interface (GUI) for the Recipe Manager 
 * application. It enables users to interact with recipes by viewing, adding, editing, and deleting 
 * them. The GUI displays a list of recipes and their details, while providing options for managing 
 * recipes and their ingredients.
 * 
 * <p>Features:</p>
 * - Displays a list of available recipes.
 * - Allows users to view detailed information about a selected recipe.
 * - Enables the addition and editing of recipes and their ingredients.
 * - Provides a responsive layout that adapts to different screen sizes.
 * - Manages the loading and saving of recipes using file-based storage.
 * 
 * <p>Usage:</p>
 * The RecipeManagerGUI class is responsible for launching the main interface of the Recipe Manager 
 * application. It initializes all required components and listeners for user interaction:
 * <pre>{@code
 * RecipeManagerGUI gui = new RecipeManagerGUI();
 * gui.setVisible(true);
 * }</pre>
 * Users can interact with the main screen, which includes:
 * - Viewing recipes
 * - Adding new recipes
 * - Editing existing recipes
 * - Navigating through the recipe list
 * 
 * <p>Dependencies:</p>
 * - Recipe.java
 * - RecipeCollection.java
 * - RecipeStorage.java
 * - Ingredient.java
 * 
 * <p>Notes:</p>
 * - The GUI supports actions for adding, editing, and viewing recipes through buttons and dialogs.
 * - The RecipeManagerGUI class interacts with the RecipeCollection and RecipeStorage classes to manage 
 *   the data for recipes.
 * - The layout uses a split-pane to display both the recipe list and the recipe details side by side.
 * 
 * @author  Jon-Kayla Pointer
 * @version 1.0.0
 */





package com.jkp.recipe_manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class RecipeManagerGUI extends JFrame {
    private String RecipePaths = "./src/main/java/recipes/";
    private DefaultListModel<String> listModel;
    private JList<String> recipeList;
    private RecipeCollection recipeCollection;
    private JTextArea recipeDetailsArea; // Area to display selected recipe details
    private JPanel homePanel; // To hold the home screen panel
    private JSplitPane splitPane; // Split pane for displaying recipes and details

    public RecipeManagerGUI() {
        // Initialize the GUI components
        setTitle("Recipe Manager");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int usableWidth = screenSize.width - screenInsets.left - screenInsets.right;
        int usableHeight = screenSize.height - screenInsets.top - screenInsets.bottom;
        setSize(usableWidth, usableHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        recipeDetailsArea = new JTextArea();
        recipeDetailsArea.setEditable(false); // Make the details area read-only

        // Create a split pane for displaying recipe list and details
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(recipeList), new JScrollPane(recipeDetailsArea));
        splitPane.setDividerLocation(300); // Set initial size for the recipe list
        add(splitPane, BorderLayout.CENTER);

        // Top panel for buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Pink button for loading recipes
        JButton loadButton = new JButton("Load All Recipes");
        loadButton.setBackground(new Color(226, 117, 137)); // Pink
        loadButton.setForeground(Color.BLACK);
        loadButton.addActionListener(new LoadRecipesAction());
        topPanel.add(loadButton);

        // Pink button for home
        JButton homeButton = new JButton("Home");
        homeButton.setBackground(new Color(226, 117, 137)); // Pink
        homeButton.setForeground(Color.BLACK);
        homeButton.addActionListener(e -> showHomeScreen());
        topPanel.add(homeButton);

        // Pink button for adding a recipe
        JButton addRecipeButton = new JButton("Add Recipe");
        addRecipeButton.setBackground(new Color(226, 117, 137)); // Pink
        addRecipeButton.setForeground(Color.BLACK);
        addRecipeButton.addActionListener(e -> showAddRecipeDialog());
        topPanel.add(addRecipeButton);

        // Pink button for viewing selected recipe
        JButton viewRecipeButton = new JButton("View Recipe");
        viewRecipeButton.setBackground(new Color(226, 117, 137)); // Pink
        viewRecipeButton.setForeground(Color.BLACK);
        viewRecipeButton.addActionListener(new ViewRecipeAction());
        topPanel.add(viewRecipeButton);
        JButton editRecipeButton = new JButton("Edit Recipe");
        editRecipeButton.setBackground(new Color(226, 117, 137)); // Pink
        editRecipeButton.setForeground(Color.BLACK);
        editRecipeButton.addActionListener(new EditRecipeAction());
        topPanel.add(editRecipeButton);

        add(topPanel, BorderLayout.NORTH);

        // Initially show home screen
        showHomeScreen();
    }

    /**
     * Displays the home screen of the application by creating a new {@link Home} panel
     * and adding it to the current frame. It clears any existing components, removes
     * the previous {@link splitPane} and {@link homePanel} if present, and then
     * displays the new home screen layout.
     *
     * <p>Usage:</p>
     * This method is typically called when the user navigates back to the home screen
     * or when initializing the main view of the application:
     * <pre>{@code
     * showHomeScreen();
     * }</pre>
     *
     * <p>Side Effects:</p>
     * - Clears previous UI components.
     * - Updates the main content panel to show the home screen.
     * 
     * @see Home
     */


    private void showHomeScreen() {
        // Clear previous components
        remove(splitPane);
        if (homePanel != null) {
            remove(homePanel);
        }

        // Create and display home panel
        homePanel = new Home().getPanel();
        add(homePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    /**
     * This class handles the action of editing a recipe when triggered by a user event, such as a button click.
     * It listens for the action event and performs the following tasks:
     * - Retrieves the selected recipe from the list of recipes.
     * - Displays the {@link EditRecipe} dialog, passing the selected recipe for editing.
     * - Sets a listener to reload the recipes once an edit is completed.
     * 
     * <p>Usage:</p>
     * This class is typically used in conjunction with UI elements like buttons or menu items for editing recipes:
     * <pre>{@code
     * EditRecipeAction editRecipeAction = new EditRecipeAction();
     * someButton.addActionListener(editRecipeAction);
     * }</pre>
     * 
     * <p>Key Features:</p>
     * - Validates the selection of a recipe.
     * - Opens the edit dialog for modifying the selected recipe.
     * - Refreshes the recipe list after successful editing.
     * 
     * @see EditRecipe
     * @see LoadRecipesAction
     */


    private class EditRecipeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get the selected recipe name
            int selectedIndex = recipeList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Fetch the selected recipe
                Recipe selectedRecipe = recipeCollection.getRecipes().get(selectedIndex);
                // Create an instance of EditRecipe and show the dialog
                EditRecipe editRecipe = new EditRecipe(selectedRecipe);
                editRecipe.addListener(() -> new LoadRecipesAction().actionPerformed(null));
            } else {
                JOptionPane.showMessageDialog(null, "Please select a recipe to edit.");
            }
        }
    }
    /**
     * This class handles the action of viewing a recipe's details when triggered by a user event, such as a button click.
     * It listens for the action event and performs the following tasks:
     * - Retrieves the selected recipe name from the recipe list.
     * - Attempts to load the corresponding recipe file (in JSON format) based on the selected recipe name.
     * - Displays the recipe details in the UI.
     * 
     * <p>Usage:</p>
     * This class is typically used in conjunction with UI elements like buttons or menu items for viewing recipes:
     * <pre>{@code
     * ViewRecipeAction viewRecipeAction = new ViewRecipeAction();
     * someButton.addActionListener(viewRecipeAction);
     * }</pre>
     * 
     * <p>Key Features:</p>
     * - Validates the selection of a recipe.
     * - Loads the selected recipe from a JSON file.
     * - Displays the recipe details in a readable format.
     * - Handles errors gracefully by showing error messages if the recipe cannot be loaded.
     * 
     * @see displayRecipeDetails
     */


    private class ViewRecipeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedRecipeName = recipeList.getSelectedValue();
            if (selectedRecipeName != null) {
                // Fetch and display the selected recipe details
                try {
                    Path filePath = Paths.get(RecipePaths + selectedRecipeName.replaceAll("[^a-zA-Z0-9]", "_") + ".json");
                    displayRecipeDetails(filePath);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error loading recipe: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a recipe.");
            }
        }
    }

    /**
     * Displays the details of a selected recipe in the user interface. It retrieves the recipe from 
     * the file specified by the provided path, then formats and displays its name, preparation time, 
     * cook time, servings, ingredients, and instructions in a readable format.
     * 
     * <p>Steps:</p>
     * - Loads the recipe from a JSON file located at the specified file path.
     * - Extracts the recipe details, including the name, prep time, cook time, servings, ingredients, 
     *   and instructions.
     * - Formats and displays the recipe details in a readable format.
     * - Updates the {@code recipeDetailsArea} with the formatted details.
     * 
     * <p>Usage:</p>
     * This method is typically invoked when a recipe is selected from the recipe list:
     * <pre>{@code
     * displayRecipeDetails(filePath);
     * }</pre>
     * 
     * @param filePath The path to the recipe JSON file to load.
     * @throws IOException If an error occurs while reading the file or loading the recipe.
     * 
     * @see RecipeStorage#loadRecipe(Path)
     * @see Ingredient#toString()
     * @see Recipe#getInstructions()
     */



    private void displayRecipeDetails(Path filePath) throws IOException {
        StringBuilder details = new StringBuilder();
        Recipe recipe = RecipeStorage.loadRecipe(filePath);
        details.append("Name: ").append(recipe.getName()).append("\n");
        details.append("Prep Time: ").append(recipe.getPrepTime()).append("\n");
        details.append("Cook Time: ").append(recipe.getCookTime()).append("\n");
        details.append("Servings: ").append(recipe.getServings()).append("\n");
        details.append("Ingredients:\n");

        for (Ingredient ingredient : recipe.getIngredients()) {
            details.append("- ").append(ingredient.getQuantity()).append(" ").append(ingredient.getUnit())
                    .append(" of ").append(ingredient.getName()).append("\n");
        }

        details.append("Instructions:\n");
        int stepNumber = 1;
        for (String step : recipe.getInstructions()) {
            details.append("Step ").append(stepNumber++).append(": ").append(step).append("\n");
        }

        recipeDetailsArea.setText(details.toString());
    }

    /**
     * Displays the "Add Recipe" dialog for adding a new recipe. After the user completes the dialog, 
     * it reloads the list of recipes and updates the user interface to reflect the newly added recipe.
     * 
     * <p>Steps:</p>
     * - Displays the "Add Recipe" dialog to allow the user to input details for a new recipe.
     * - After the recipe is added, it reloads all recipes from disk and updates the UI to display the 
     *   current list of recipes.
     * - If recipes are loaded successfully, the first recipe is automatically selected, and its details are displayed.
     * - Provides feedback to the user by showing a message dialog if an error occurs while loading recipes.
     * 
     * <p>Usage:</p>
     * This method is typically used when the user triggers an action to add a new recipe:
     * <pre>{@code
     * showAddRecipeDialog();
     * }</pre>
     * 
     * @see AddRecipe#showAddRecipeDialog()
     * @see RecipeStorage#loadAllRecipes(String)
     * @see LoadRecipesAction#actionPerformed(ActionEvent)
     */

    private void showAddRecipeDialog() {
        AddRecipe addRecipe = new AddRecipe();
        addRecipe.showAddRecipeDialog(); 
        try {
            recipeCollection = RecipeStorage.loadAllRecipes(RecipePaths);
            listModel.clear();
            for (Recipe recipe : recipeCollection.getRecipes()) {
                listModel.addElement(recipe.getName());
            }
            recipeList.revalidate();
            recipeList.repaint();   recipeList.revalidate();
            recipeList.repaint();
            JOptionPane.showMessageDialog(null, "Loaded all Recipes!");
             // Check if an item is selected
            if (!listModel.isEmpty()) {
                recipeList.setSelectedIndex(0); // Optionally select the first recipe
                new ViewRecipeAction().actionPerformed(null); // Call the view action
            }
            showRecipeScreen();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading recipes: " + ex.getMessage());
        }
    
        // Add listener to reload recipes after adding


        addRecipe.addListener(new Runnable() {
            @Override
            public void run() {
                // Reload the recipes by calling LoadRecipesAction directly
                System.out.println("Running");
                new LoadRecipesAction().actionPerformed(null);
            }
        });


    }
    
    /**
     * Action listener responsible for loading all recipes from the disk and updating the recipe list.
     * This action is triggered when the user performs an action to load all recipes.
     * It fetches the recipes from the specified directory, populates the recipe list with their names, 
     * and displays a message indicating whether the loading process was successful.
     * 
     * <p>Key Features:</p>
     * - Loads all recipes from the disk by calling {@link RecipeStorage#loadAllRecipes(String)}.
     * - Clears and updates the list of recipes in the UI, ensuring it reflects the current data.
     * - Automatically selects the first recipe from the list and displays its details.
     * - Provides feedback to the user, notifying them if the recipe load operation was successful or if an error occurred.
     * 
     * <p>Usage:</p>
     * This class is typically used in the context of a button or menu item action that triggers the loading of recipes:
     * <pre>{@code
     * LoadRecipesAction loadRecipesAction = new LoadRecipesAction();
     * loadRecipesAction.actionPerformed(event);
     * }</pre>
     * 
     * @see RecipeStorage#loadAllRecipes(String)
     */


    private class LoadRecipesAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Load recipes from disk and populate listModel
            try {
                recipeCollection = RecipeStorage.loadAllRecipes(RecipePaths);
                listModel.clear();
                for (Recipe recipe : recipeCollection.getRecipes()) {
                    listModel.addElement(recipe.getName());
                }
                recipeList.revalidate();
                recipeList.repaint();   recipeList.revalidate();
                recipeList.repaint();
                JOptionPane.showMessageDialog(null, "Loaded all Recipes!");
                 // Check if an item is selected
                if (!listModel.isEmpty()) {
                    recipeList.setSelectedIndex(0); // Optionally select the first recipe
                    new ViewRecipeAction().actionPerformed(null); // Call the view action
                }
                showRecipeScreen();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error loading recipes: " + ex.getMessage());
            }
        }
    }
    /**
     * Displays the recipe screen by clearing the current home screen and adding the 
     * split pane layout to the main frame. This method is typically used to transition 
     * from the home screen to the recipe-related content.
     * 
     * <p>Steps:</p>
     * - Removes the existing home screen panel from the frame.
     * - Adds the split pane to the frame's center, which may contain recipe list and details.
     * - Revalidates and repaints the layout to ensure the changes take effect.
     * 
     * <p>Usage:</p>
     * This method is typically called when transitioning from the home screen to the recipe list 
     * or viewing a specific recipe.
     * 
     * @see splitPane
     * @see homePanel
     */


    private void showRecipeScreen() {
        // Clear home screen
        remove(homePanel);

        // Add the split pane to the main frame
        add(splitPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    /**
     * The entry point for the Recipe Manager application. This method initializes 
     * the `RecipeManagerGUI` and makes the graphical user interface visible to the user.
     * It is called when the application is launched.
     * 
     * <p>Details:</p>
     * - Uses `SwingUtilities.invokeLater` to ensure the GUI is created and updated on the Event Dispatch Thread (EDT).
     * - Creates an instance of `RecipeManagerGUI` and sets its visibility to true.
     * 
     * <p>Usage:</p>
     * This method is executed when the application is run, and it launches the GUI for the user.
     * 
     * @param args Command-line arguments passed to the program (not used in this implementation).
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecipeManagerGUI gui = new RecipeManagerGUI();
            gui.setVisible(true);
        });
    }
}
