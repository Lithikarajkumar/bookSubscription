package com.example.subsplan;

import com.example.bg.BackgroundPanel;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.example.subsform.SubscriptionFormPage;
import com.example.database.DatabaseConnection;
import com.example.BookDialog.BookDetailsDialog;

public class SubscriptionPlanPage {
    static Connection conn;

    public SubscriptionPlanPage() {
        createAndShowGUI();
    }

    public static void main(String[] args) throws SQLException {
        // Establish a database connection
        conn = DatabaseConnection.getConnection();

        // Check if the connection was established
        if (conn == null) {
            System.err.println("Failed to establish a connection to the database.");
            return;
        }

        // Create and show the main GUI
        SwingUtilities.invokeLater(SubscriptionPlanPage::createAndShowGUI);
    }

    // Method to create and show the main GUI for the subscription plans
    public static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Subscription Plans");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create the background panel with the background image
        BackgroundPanel backgroundPanel = new BackgroundPanel("bg1.jpg");
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Create and add subscription boxes for each plan
        backgroundPanel.add(createSubscriptionBox("Basic Plan", "1 month", "Rs 250"));
        backgroundPanel.add(createSubscriptionBox("Standard Plan", "6 months", "Rs 500"));
        backgroundPanel.add(createSubscriptionBox("Premium Plan", "1 year", "Rs 1500"));

        // Add the background panel to the frame
        frame.add(backgroundPanel);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to create a subscription box panel for a specific plan
    private static JPanel createSubscriptionBox(String planName, String duration, String cost) {
        // Create a new panel for the subscription box
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBackground(Color.LIGHT_GRAY);
        boxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Font settings for the labels
        Font labelFont = new Font("Arial", Font.BOLD, 20);

        // Create labels for the plan name, duration, and cost
        JLabel planNameLabel = new JLabel(planName);
        planNameLabel.setFont(labelFont);
        planNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel durationLabel = new JLabel(duration);
        durationLabel.setFont(labelFont);
        durationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel costLabel = new JLabel(cost);
        costLabel.setFont(labelFont);
        costLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Determine the plan ID based on the plan name
        int planId = getPlanId(planName);

        // Create the "Choose" button
        JButton selectButton = new JButton("Choose");
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.setPreferredSize(new Dimension(300, 50));

        // Add an action listener to the select button
        selectButton.addActionListener(e -> {
            try {
                new SubscriptionFormPage(planId);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Add components to the panel with spacing
        boxPanel.add(Box.createVerticalStrut(10));
        boxPanel.add(planNameLabel);
        boxPanel.add(Box.createVerticalStrut(5));
        boxPanel.add(durationLabel);
        boxPanel.add(Box.createVerticalStrut(5));
        boxPanel.add(costLabel);
        boxPanel.add(Box.createVerticalStrut(10));
        boxPanel.add(selectButton);
        boxPanel.add(Box.createVerticalStrut(10));

        return boxPanel;
    }

    // Method to determine the plan ID based on the plan name
    private static int getPlanId(String planName) {
        switch (planName) {
            case "Basic Plan":
                return 1;
            case "Standard Plan":
                return 2;
            case "Premium Plan":
                return 3;
            default:
                return -1; // Invalid plan name
        }
    }
}