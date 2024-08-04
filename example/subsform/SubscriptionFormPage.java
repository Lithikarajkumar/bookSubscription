package com.example.subsform;

import com.example.database.DatabaseConnection;
import com.example.payment.PaymentFormPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SubscriptionFormPage {
    private int planId;
    private Connection conn;

    public SubscriptionFormPage(int planId) throws SQLException {
        // Establish the database connection
        this.conn = DatabaseConnection.getConnection();

        // Check if the connection was established
        if (this.conn == null) {
            System.err.println("Failed to establish a connection to the database.");
            return;
        }

        this.planId = planId;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Create the frame for the form
        JFrame frame = new JFrame("Subscription Form");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        // Create a form panel with a grid layout
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add a label and text field for user ID
        //JLabel userIdLabel = new JLabel("User ID:");
        //JTextField userIdField = new JTextField();

        // Create the "Subscribe" button and add an action listener
        JButton subscribeButton = new JButton("Subscribe");
        subscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int userId = getUserId();
                    boolean success = saveSubscription(userId);

                    if (success) {
                        //System.out.println( + "Subscribed")
                        System.out.println("User ID " + userId + " has subscribed to Plan ID " + planId);
                        frame.dispose();

                        // Pass the planId to the PaymentFormPage constructor
                        new PaymentFormPage(planId);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to subscribe. Please try again.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid user ID. Please enter a valid number.");
                }
            }
        });

        // Add components to the form panel
       // formPanel.add(userIdLabel);
       // formPanel.add(userIdField);
        formPanel.add(new JLabel()); // Empty space for alignment
        formPanel.add(subscribeButton);

        // Add the form panel to the frame
        frame.add(formPanel, BorderLayout.CENTER);

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public int getUserId() {
        int user_id = -1;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT user_id FROM logged_in_user";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        user_id = rs.getInt("user_id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return user_id;
    }

    // Method to handle the subscription process
    private boolean saveSubscription(int userId) {
        // Check if the user_id exists in the users table
        String checkQuery = "SELECT 1 FROM users WHERE user_id = ?";
        try (PreparedStatement checkStatement = conn.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, userId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (!resultSet.next()) {
                    // User does not exist in the users table
                    System.err.println("User with ID " + userId + " does not exist.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Define the SQL insert query
        String query = "INSERT INTO plan_audit (user_id, plan_id, start_date, end_date) " +
                "SELECT user_id, ?, ?, ? FROM logged_in_user l " +
                "WHERE EXISTS (SELECT 1 FROM users u WHERE u.user_id = l.user_id)";

        try {
            // Prepare the statement and set parameters
            PreparedStatement statement = conn.prepareStatement(query);
            LocalDate startDate = LocalDate.now(); // Current date
            statement.setInt(1, planId);
            statement.setDate(2, Date.valueOf(startDate));

            // Determine the end date based on the plan duration
            LocalDate endDate = calculateEndDate(startDate);
            statement.setDate(3, Date.valueOf(endDate));

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }



    // Method to calculate the end date based on the start date and plan duration
    private LocalDate calculateEndDate(LocalDate startDate) {
        switch (planId) {
            case 1:
                return startDate.plus(1, ChronoUnit.MONTHS); // 1 month for basic plan
            case 2:
                return startDate.plus(6, ChronoUnit.MONTHS); // 6 months for standard plan
            case 3:
                return startDate.plus(1, ChronoUnit.YEARS); // 1 year for premium plan
            default:
                return startDate;
        }
    }
}