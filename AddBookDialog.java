import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class AddBookDialog extends JDialog {
    private JTextField librarianIdField;
    private JTextField bookIdField;
    private JTextField bookNameField;
    private JTextField isbnField;
    private JTextField bookEditionField;
    private JTextField genreIdField;
    private JButton submitButton;
    private Connection connection;

    public AddBookDialog(JFrame parent, Connection connection) {
        super(parent, "Add Book", true);
       // this.connection = connection;

        // Form UI and other code...
        // Rest of the code...
        //super(parent, "Add Book", true);
        this.connection = connection;

        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Create labels and fields
        librarianIdField = new JTextField(20);
        bookIdField = new JTextField(20);
        bookNameField = new JTextField(20);
        isbnField = new JTextField(20);
        bookEditionField = new JTextField(20);
        genreIdField = new JTextField(20);

        JLabel librarianIdLabel = new JLabel("Librarian ID:");
        JLabel bookIdLabel = new JLabel("Book ID:");
        JLabel bookNameLabel = new JLabel("Book Name:");
        JLabel isbnLabel = new JLabel("ISBN:");
        JLabel bookEditionLabel = new JLabel("Book Edition:");
        JLabel genreIdLabel = new JLabel("Genre ID:");

        // Create submit button
        submitButton = new JButton("Submit");

        // Create a panel for form inputs
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(librarianIdLabel);
        panel.add(librarianIdField);
        panel.add(bookIdLabel);
        panel.add(bookIdField);
        panel.add(bookNameLabel);
        panel.add(bookNameField);
        panel.add(isbnLabel);
        panel.add(isbnField);
        panel.add(bookEditionLabel);
        panel.add(bookEditionField);
        panel.add(genreIdLabel);
        panel.add(genreIdField);

        // Add panel and submit button to dialog
        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        // Add action listener to the submit button
        submitButton.addActionListener(e -> handleSubmit());

        setVisible(true);
    }

    // Method to handle the submit action
    private void handleSubmit() {
        // Collect data and insert into the tables
        // Use the connection object
        try {
            int librarianId = Integer.parseInt(librarianIdField.getText());
            int bookId = Integer.parseInt(bookIdField.getText());
            String bookName = bookNameField.getText();
            String isbn = isbnField.getText();
            int bookEdition = Integer.parseInt(bookEditionField.getText());
            int genreId = Integer.parseInt(genreIdField.getText());

            // Insert data into `book` table
            PreparedStatement bookStatement = connection.prepareStatement(
                    "INSERT INTO book (book_id, book_name, isbn, book_edition, genre_id) VALUES (?, ?, ?, ?, ?)"
            );
            bookStatement.setInt(1, bookId);
            bookStatement.setString(2, bookName);
            bookStatement.setString(3, isbn);
            bookStatement.setInt(4, bookEdition);
            bookStatement.setInt(5, genreId);
            bookStatement.executeUpdate();
            bookStatement.close();

            // Insert data into `plan_audit` table
            PreparedStatement planAuditStatement = connection.prepareStatement(
                    "INSERT INTO plan_audit (user_id, plan_id, start_date, end_date) VALUES (?, ?, SYSDATE, SYSDATE + INTERVAL '1' MONTH)"
            );
            planAuditStatement.setInt(1, librarianId);
            planAuditStatement.setInt(2, bookId);
            planAuditStatement.executeUpdate();
            planAuditStatement.close();

            // Show success message
            JOptionPane.showMessageDialog(this, "Book added successfully!");

            // Close the dialog
            dispose();
        } catch (SQLException | NumberFormatException ex) {
            // Show error message
            JOptionPane.showMessageDialog(this, "Error adding book: " + ex.getMessage());
        }
    }
    }

