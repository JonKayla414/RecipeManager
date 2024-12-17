
# Recipe Manager

## Overview
The Recipe Manager is a Java application designed to help users manage their recipes efficiently. It allows users to add, edit recipes, as well as manage ingredients and cooking instructions. The application is built using Java with Maven for dependency management and project building.

### Class Descriptions
- **AddIngredientDialog**: A dialog interface for adding new ingredients to a recipe, allowing users to input ingredient details.
- **AddIngredients**: Handles the logic for adding ingredients to a recipe, ensuring the ingredient data is correctly captured and validated.
- **AddInstructions**: Facilitates the addition of cooking instructions, enabling users to specify step-by-step guidance for each recipe.
- **AddRecipe**: Manages the process of creating new recipes, gathering all necessary information from the user and saving it to the recipe collection.
- **EditIngredientsDialog**: A dialog interface that allows users to edit existing ingredients within a recipe, providing access to modify details.
- **EditRecipe**: Manages the editing of existing recipes, allowing users to update all relevant recipe information and ingredients.
- **Home**: The main interface of the application, displaying the home page and providing navigation options for users.
- **Ingredient**: Represents an ingredient with details such as name, quantity, unit, preparation, and calorie information.
- **Main**: The entry point of the application, responsible for initializing and launching the Recipe Manager.
- **Recipe**: Represents a recipe, encapsulating details such as name, list of ingredients, instructions, servings, preparation time, and cooking time.
- **RecipeCollection**: Manages a collection of recipes, providing methods for adding, removing, and retrieving recipes.
- **RecipeManagerGUI**: The graphical user interface for the Recipe Manager, facilitating user interactions with the application.
- **RecipeStorage**: Handles the persistence of recipe data, managing saving and loading of recipes from storage.


## File Hierarchy
Below is the directory structure of the `recipe_manager` project:

```
recipe_manager
│
├── src
│   ├── main
│   │   └── java
│   │       ├─ com
│   │       |   └── jkp
│   │       |        └── recipe_manager
│   │       |            ├── AddIngredientDialog.java
│   │       |            ├── AddIngredients.java
│   │       |            ├── AddInstructions.java
│   │       |            ├── AddRecipe.java
│   │       |            ├── EditIngredientsDialog.java
│   │       |            ├── EditRecipe.java
│   │       |            ├── Home.java
│   │       |            ├── Ingredient.java
│   │       |            ├── Main.java
│   │       |            ├── Recipe.java
│   │       |            ├── RecipeCollection.java
│   │       |            ├── RecipeManagerGUI.java
│   │       |            └── RecipeStorage.java
│   │       ├─  recipes
│   └──test └── resources
│
├── target
├── pom.xml
├── build_script.sh
├── readMe.md
└── version_history.txt
```

## Getting Started
To get started with the Recipe Manager, follow these steps:

1. **Copy the Project File**: 

2. **Navigate to the Project Directory**:
   ```bash
   cd recipe_manager
   ```

3. **Build the Project**:
   - For Linux:
     ```bash
     ./build_script.sh
     ```
   - For Windows PowerShell:
     ```powershell
     .\build_script.ps1
     ```

4. **Run the Application**: After building the project successfully, run the application using your preferred method (IDE or command line).

## Configuration
### Setting Up JAVA_HOME
Ensure that the `JAVA_HOME` environment variable is set correctly. Update the path in the `build_script.sh` or PowerShell script to point to your JDK installation:
- For Linux:
  ```bash
  export JAVA_HOME=/path/to/your/java/home
  ```
- For Windows:
  ```powershell
  $env:JAVA_HOME = "C:\path\to\your\java\home"
  ```


## Acknowledgements
- Java and Maven for the development tools.
- Any libraries or frameworks used in the project (e.g., GUI frameworks, testing libraries).
- Inspiration from various recipe management applications and resources.


---

You should be able to copy all of this without any issues now! Let me know if you need any more help.