/**
 * Package: com.jkp.recipe_manager
 * 
 * The Home class represents the main screen for the Recipe Manager application.
 * It displays a title and a central image, serving as the welcoming screen for users.
 * 
 * <p>Features:</p>
 * - Displays the application title prominently.
 * - Loads and displays an image (e.g., a decorative graphic or logo).
 * 
 * <p>Usage:</p>
 * This class can be used to retrieve the main panel for the home screen,
 * which can then be integrated into the application's GUI.
 * 
 * @author  Jon-Kayla Pointer
 * @version 1.0.0
 */

 package com.jkp.recipe_manager;

 import java.awt.BorderLayout;
 import java.awt.Font;
 import java.awt.Image;
 
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.SwingConstants;
 
 public class Home {
     private JPanel panel;
 
     public Home() {
         panel = new JPanel();
         panel.setLayout(new BorderLayout());
 
         // Create a title label
         JLabel titleLabel = new JLabel("Welcome To The Recipe Manager!", SwingConstants.CENTER);
         titleLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Adjust font and size as needed
         panel.add(titleLabel, BorderLayout.NORTH);
 
         // Load the kitty icon
         ImageIcon kittyIcon = new ImageIcon("./src/main/java/resources/kitty.png"); // Replace with the actual path to the image
         Image kittyImage = kittyIcon.getImage(); // Get the Image from the ImageIcon
 
         // Get original dimensions
         int originalWidth = kittyImage.getWidth(null);
         int originalHeight = kittyImage.getHeight(null);
 
         int targetWidth = 500;
 
         // Calculate the scaling factor to maintain the aspect ratio
         double scaleFactor = (double) targetWidth / originalWidth;
 
         // Calculate the new height based on the scaling factor
         int targetHeight = (int) (originalHeight * scaleFactor);
 
         // Scale the image uniformly
         Image scaledKittyImage = kittyImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
 
         // Create a new ImageIcon with the scaled image
         ImageIcon scaledKittyIcon = new ImageIcon(scaledKittyImage);
 
         // Create the label with the scaled image
         JLabel kittyLabel = new JLabel(scaledKittyIcon);
         panel.add(kittyLabel, BorderLayout.CENTER);
     }
 
     public JPanel getPanel() {
         return panel;
     }
 }
 
 