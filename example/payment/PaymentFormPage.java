
package com.example.payment;

import com.example.credit.CreditCardForm;
import com.example.database.DatabaseConnection;
import com.example.debit.DebitCardForm;
import com.example.upi.UpiForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentFormPage {
    private int planId;
    private double amount;

    public PaymentFormPage(int planId) {
        this.planId = planId;
        fetchAmountFromSubscriptionPlan();
        createAndShowGUI();
    }

    private void fetchAmountFromSubscriptionPlan() {
        // Query the subscription_plan table to get the amount based on the planId
        // Assume a method to fetch amount from the database and set it to 'amount'
        amount = 100.0; // Sample amount for demonstration
    }

    private void createAndShowGUI() {
        // Create the frame for the payment form
        JFrame frame = new JFrame("Payment Form");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());

        // Create a form panel with a grid layout
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add payment method selection
        JLabel paymentLabel = new JLabel("Select Payment Method:");
        formPanel.add(paymentLabel);

        JComboBox<String> paymentOptions = new JComboBox<>(new String[]{"Credit", "Debit", "UPI"});
        formPanel.add(paymentOptions);

        // Add amount display
        JLabel amountLabel = new JLabel("Amount: $" + amount);
        formPanel.add(amountLabel);

        // Create the "Pay Now" button and add an action listener
        JButton payButton = new JButton("Pay Now");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userId;
                String paymentMethod = (String) paymentOptions.getSelectedItem();
                // Based on the payment method, open the necessary form to get input
                switch (paymentMethod) {
                    case "Credit":
                        userId = getUserId();
                        new CreditCardForm(userId,planId);
                        break;

                    case "Debit":
                        userId = getUserId();
                        new DebitCardForm(userId,planId);


                        break;
                    case "UPI":
                        userId=getUserId();
                        new UpiForm(userId,planId);
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame, "Invalid Payment Method");
                }
            }
        });
        formPanel.add(new JLabel()); // Empty label for alignment
        formPanel.add(payButton);

        // Add the form panel to the frame
        frame.add(formPanel, BorderLayout.CENTER);

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openCreditCardForm() {
        // Implement the logic to open the credit card form
        JOptionPane.showMessageDialog(null, "Credit Card Form");
    }

    private void openDebitCardForm() {
        // Implement the logic to open the debit card form
        JOptionPane.showMessageDialog(null, "Debit Card Form");
    }

    private void openUPIForm() {
        // Implement the logic to open the UPI form
        JOptionPane.showMessageDialog(null, "UPI Form");
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

}
