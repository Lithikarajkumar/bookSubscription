package com.example.main;

import com.example.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

//import static com.example.Book.Book.conn;

class SubscriptionPlan {
    private int planId;
    private String planName;
    private double price;

    public SubscriptionPlan(int planId, String planName, double price) {
        this.planId = planId;
        this.planName = planName;
        this.price = price;
    }

    public int getPlanId() {
        return planId;
    }

    public String getPlanName() {
        return planName;
    }

    public double getPrice() {
        return price;
    }
}

class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private int age;
    private SubscriptionPlan currentPlan;

    public User(int userId, String username, String email, String password, int age) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.currentPlan = null;
        saveToDatabase();
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public SubscriptionPlan getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(SubscriptionPlan plan) {
        this.currentPlan = plan;
        updateCurrentPlanInDatabase();
    }

    public void myProfile() {
        System.out.println("User Profile:");
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Age: " + age);

        if (currentPlan != null) {
            System.out.println("Subscribed Plan:");
            System.out.println("Plan ID: " + currentPlan.getPlanId());
            System.out.println("Plan Name: " + currentPlan.getPlanName());
            System.out.println("Plan Price: " + currentPlan.getPrice());
        } else {
            System.out.println("No subscribed plan.");
        }
    }

    private void saveToDatabase() {
        try {
            String query = "INSERT INTO users (username, email, password, age) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = Main2.connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentPlanInDatabase() {
        try {
            String query = "UPDATE users SET plan_id = ? WHERE user_id = ?";
            PreparedStatement statement = Main2.connection.prepareStatement(query);
            statement.setInt(1, currentPlan != null ? currentPlan.getPlanId() : 0);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User logIn(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = Main2.connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                return new User(userId, username, email, password, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

public class Main2 extends JFrame {
    private static List<User> registeredUsers = new ArrayList<>();
    private static List<SubscriptionPlan> subscriptionPlans = new ArrayList<>();
    private static int userIdCounter = 1;

    public static Connection connection;

    private JTextField bookNameField;
    private JTextField bookIdField;

    private JTextField   bookIsbnField;
    private JTextField bookGenreField;
    private JTextField bookEditionField;


    public Main2() {
        super("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300); // Increased width
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel bookIdLabel = new JLabel("Book Id:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(bookIdLabel, gbc);

        bookIdField = new JTextField();
        bookIdField.setPreferredSize(new Dimension(200, 25)); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(bookIdField, gbc);

        JLabel bookNameLabel = new JLabel("Book Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(bookNameLabel, gbc);

        bookNameField = new JTextField();
        bookNameField.setPreferredSize(new Dimension(200, 25)); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(bookNameField, gbc);

        JLabel bookIsbnLabel = new JLabel("Book ISBN:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(bookIsbnLabel, gbc);

        bookIsbnField = new JTextField();
        bookIsbnField.setPreferredSize(new Dimension(200, 25)); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(bookIsbnField, gbc);

        JLabel bookEditionLabel = new JLabel("Book Edition:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(bookEditionLabel, gbc);

        bookEditionField = new JTextField();
        bookEditionField.setPreferredSize(new Dimension(200, 25)); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(bookEditionField, gbc);

        JLabel bookGenreLabel = new JLabel("Book Genre Id:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(bookGenreLabel, gbc);

        bookGenreField = new JTextField();
        bookGenreField.setPreferredSize(new Dimension(200, 25)); // Increased width
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(bookGenreField, gbc);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(addButton, gbc);

        add(mainPanel);
        setVisible(true);
    }
    public static void main(String[] args) {
        initializeSubscriptionPlans();
        try {
            connectToDatabase();
            new Main2(); // Initialize and display the GUI
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static void connectToDatabase() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
        String username = "SYSTEM";
        String password = "system";

        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connected to database");
    }


    public void addBook() {


        int bookId = Integer.parseInt(bookIdField.getText());
        String bookName = bookNameField.getText();
        String bookIsbn = bookIsbnField.getText();
        int bookEdition = Integer.parseInt(bookEditionField.getText());
        int bookGenre = Integer.parseInt(bookGenreField.getText());


        //bookid = Integer.parseInt(bookIdField.getText());
        String query = "INSERT INTO book (book_id, book_name, isbn, book_edition, genre_id) VALUES (?, ?, ?,?,?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, bookId);
            stmt.setString(2, bookName);
            stmt.setString(3, bookIsbn);
            stmt.setInt(4, bookEdition);
            stmt.setInt(5, bookGenre);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }





    public static void closeDatabaseConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    public static boolean librarianLogIn(String username, String password) throws SQLException {
        String query = "SELECT * FROM librarians WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    public static boolean userLogin(String username, String password) throws SQLException {
        // Establish database connection
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to database.");
            return false;
        }

        // Prepare SQL statement
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if any rows were returned
            if (resultSet.next()) {
                // If login successful, return true
                return true;
            } else {
                // If login failed, return false
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error during login.");
            ex.printStackTrace();
            return false;
        } finally {
            // Close the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing database connection.");
                    ex.printStackTrace();
                }
            }
        }
    }
    public static void signUpUser(String username, String email, String password, int age) {
        // Validate the inputs
        if (username == null || username.trim().isEmpty()) {
            System.err.println("Error registering user: Username cannot be empty");
            JOptionPane.showMessageDialog(null, "Invalid username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (email == null || !email.contains("@") || !email.contains(".")) {
            System.err.println("Error registering user: Invalid email format");
            JOptionPane.showMessageDialog(null, "Invalid email.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password == null || password.length() < 8) {
            System.err.println("Error registering user: Password must be at least 8 characters long");
            JOptionPane.showMessageDialog(null, "Invalid password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (age <= 8 || age > 150) {
            System.err.println("Error registering user: Invalid age");
            JOptionPane.showMessageDialog(null, "Invalid age", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ensure a database connection is established
        if (connection == null) {
            try {
                connectToDatabase();
            } catch (SQLException e) {
                System.err.println("Error connecting to database: " + e.getMessage());
                return; // Exit the function if the connection cannot be established
            }
        }

        System.out.println("Function called");
        try {
            // Check if the username already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) > 0) {
                System.err.println("Error registering user: Username already exists");
                return;
            }

            // Insert the user if the username is unique
            String query = "INSERT INTO users (username, email, password, age) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, age);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            int userId = -1;
            if (keys.next()) {
                userId = keys.getInt(1);
            }

            // Display a success message with the username and user ID
            //JOptionPane.showMessageDialog(null, "User registered successfully.\nUsername: " + username + "\nUser ID: " + userId, "Success", JOptionPane.INFORMATION_MESSAGE);


            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }

    private static void initializeSubscriptionPlans() {
        subscriptionPlans.add(new SubscriptionPlan(1, "Basic Plan", 250));
        subscriptionPlans.add(new SubscriptionPlan(2, "Standard Plan", 500));
        subscriptionPlans.add(new SubscriptionPlan(3, "Premium Plan", 750));
    }

    private static User findUserById(int userId) {
        for (User user : registeredUsers) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    private static SubscriptionPlan findPlanById(int planId) {
        for (SubscriptionPlan plan : subscriptionPlans) {
            if (plan.getPlanId() == planId) {
                return plan;
            }
        }
        return null;
    }
}