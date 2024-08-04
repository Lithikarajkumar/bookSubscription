package com.example.debit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DebitCardForm {
    private int planId;
    private int userId;
    private JFrame frame;

    public DebitCardForm(int planId, int userId) {
        this.planId = planId;
        this.userId = userId;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Debit Card Payment"); // Initialize frame in the constructor
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());




        // Create a panel for the form components
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add form components: card number, expiration date, CVV, etc.
        JLabel cardNumberLabel = new JLabel("Card Number:");
        JTextField cardNumberField = new JTextField();
        formPanel.add(cardNumberLabel);
        formPanel.add(cardNumberField);

        JLabel expirationLabel = new JLabel("Expiration Date:");
        JTextField expirationField = new JTextField();
        formPanel.add(expirationLabel);
        formPanel.add(expirationField);

        JLabel cvvLabel = new JLabel("CVV:");
        JTextField cvvField = new JTextField();
        formPanel.add(cvvLabel);
        formPanel.add(cvvField);

        // Create the "Submit" button


        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumber = cardNumberField.getText();
                String expirationDate = expirationField.getText();
                String cvv = cvvField.getText();

                // Process the credit card payment
                if (processCreditCardPayment(cardNumber, expirationDate, cvv)) {
                    // Simulate processing delay
                    try {
                        Thread.sleep(2000); // 2 seconds delay
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    // Display payment processing message
                    // Display payment processing message and close after 3 seconds
                    JOptionPane optionPane = new JOptionPane();
                    final JDialog dialog = optionPane.createDialog(frame, "Processing payment...");
                    dialog.setModal(false); // Set the dialog to be non-modal
                    dialog.setVisible(true);

                    Timer timer = new Timer(20000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.setVisible(false);
                            dialog.dispose(); // Dispose the dialog after closing it
                        }
                    });
                    timer.setRepeats(false); // Set the timer to run only once
                    timer.start();



                    // Start a timer to close the message after 3 seconds (3000 milliseconds)



                    // Simulate payment confirmation
                    boolean paymentConfirmed = confirmPayment();

                    // Display payment result
                    if (paymentConfirmed) {
                        JOptionPane.showMessageDialog(frame, "Payment Successful!");
                        JOptionPane.showMessageDialog(frame,  "You have subscribed to Plan ID " + userId);
                        frame.dispose(); // Close the form after payment
                    } else {
                        JOptionPane.showMessageDialog(frame, "Payment Failed. Please try again.");
                    }
                }
            }
        });
        formPanel.add(new JLabel()); // Empty label for alignment
        formPanel.add(submitButton);

        // Add the form panel to the frame
        frame.add(formPanel, BorderLayout.CENTER);

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean processCreditCardPayment(String cardNumber, String expirationDate, String cvv) {
        // Perform basic validation for card number, expiration date, and CVV
        if (cardNumber.isEmpty() || expirationDate.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.");
            return false;
        }

        // Perform additional validation if needed
        // For demonstration, assume the payment is successful
        return true;
    }

    private boolean confirmPayment() {
        // Simulate payment confirmation
        // For demonstration, assume the payment is confirmed
        return true;
    }
}


