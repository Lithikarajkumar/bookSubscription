package com.example.homepage2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;
import com.example.newc.NewClass2;
import com.example.bg.BackgroundPanel;
import com.example.util.BookDetails;
import com.example.Book.Book;
import com.example.BookDialog.BookDetailsDialog;
import com.example.database.DatabaseConnection;


public class HomePage2 extends JFrame {

    private JPanel mainPanel;

    public HomePage2() {
        setTitle("Book Description");
        setSize(4000, 10000);
        setResizable(true); // Enable resizing
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Create top panel and main content panel
        createTopPanel();
        createMainContentPanel();

        setVisible(true);
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add login button and category panel

// Add view profile button, login button, and category panel
        createSearchButton(topPanel);
        createViewProfileButton(topPanel);
        createLoginButton(topPanel);

        //createCategoryPanel(topPanel);


        // Add top panel to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }





    private void createSearchButton(JPanel topPanel) {
        JButton searchButton = new JButton("Search");

        // Styling and event handling for the search button
        searchButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setBackground(Color.decode("#115DEE"));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setBorder(BorderFactory.createEmptyBorder());
        searchButton.setOpaque(true);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Create a popup menu for genres
        JPopupMenu genreMenu = new JPopupMenu();
        JMenuItem genre1 = new JMenuItem("Genre 1 - ID = 1");
        JMenuItem genre2 = new JMenuItem("Genre 2 - ID = 2");
        JMenuItem genre3 = new JMenuItem("Genre 3 - ID = 3");

        // Add action listeners to genre items
        genre1.addActionListener(e -> {
            displayBooks(1);
        });

        genre2.addActionListener(e -> {
            displayBooks(2);
        });

        genre3.addActionListener(e -> {
            displayBooks(3);
        });

        // Add genre items to the popup menu
        genreMenu.add(genre1);
        genreMenu.add(genre2);
        genreMenu.add(genre3);

        // Add action listener to show the popup menu when search button is clicked
        searchButton.addActionListener(e -> {
            genreMenu.show(searchButton, 0, searchButton.getHeight());
        });

        // Add search button to the top panel
        topPanel.add(searchButton, BorderLayout.WEST);
    }

    private void displayBooks(int genre) {
        // Display books under the selected genre
        System.out.println("Books under " + genre);
        List<Book> books = Book.getBooksByGenre(genre);

        // Create a new table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Book Name");
        model.addColumn("ISBN");
        model.addColumn("Edition");

        // Add data to the model
        for (Book book : books) {
            model.addRow(new Object[]{book.getBookName(), book.getIsbn(), book.getBookEdition()});
        }

        // Create a table with the model
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(400, 200));
        table.setFillsViewportHeight(true);

        // Add a list selection listener to the table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedBookName = (String) table.getValueAt(selectedRow, 0);
                    Book selectedBook = books.stream()
                            .filter(book -> book.getBookName().equals(selectedBookName))
                            .findFirst()
                            .orElse(null);
                    if (selectedBook != null) {
                        displayBookDetails(selectedBook);
                    }
                }
            }
        });

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Display the scroll pane in a new frame
        JFrame frame = new JFrame("Books under " + genre);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }


    private void displayBookDetails(Book book) {
        // Display book details in a dialog
        BackgroundPanel backgroundPanel = new BackgroundPanel("bg1.jpg");
        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JOptionPane.showMessageDialog(null,
                "Book Name: " + book.getBookName() + "\n" +
                        "ISBN: " + book.getIsbn() + "\n" +
                        "Edition: " + book.getBookEdition(),
                "Book Details",
                JOptionPane.INFORMATION_MESSAGE);
    }













    private void createViewProfileButton(JPanel topPanel) {
        JButton viewProfileButton = new JButton("View Profile");

        // Styling and event handling for the view profile button
        viewProfileButton.setPreferredSize(new Dimension(120, 40));
        viewProfileButton.setBackground(Color.decode("#115DEE"));
        viewProfileButton.setForeground(Color.WHITE);
        viewProfileButton.setFont(new Font("Arial", Font.PLAIN, 16));
        viewProfileButton.setBorder(BorderFactory.createEmptyBorder());
        viewProfileButton.setOpaque(true);
        viewProfileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        viewProfileButton.addActionListener(e -> {
            // Handle view profile button action, open profile page
            // For example, fetch user details and subscription plan details and display them in a dialog
            try {
                Connection conn = DatabaseConnection.getConnection();
                String sql = "SELECT u.username, u.email, u.age, s.plan_id, s.end_date " +
                        "FROM users u " +
                        "JOIN plan_audit s ON u.user_id = s.user_id " +
                        "WHERE u.user_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, getLoggedInUserId());
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String username = rs.getString("username");
                            String email = rs.getString("email");
                            int age = rs.getInt("age");
                            String planName = rs.getString("plan_id");
                            Date expiryDate = rs.getDate("end_date");

                            // Display user details in a dialog
                            String message = "Username: " + username + "\nEmail: " + email + "\nAge: " + age +
                                    "\n\nSubscription Plan: " + planName + "\nExpiry Date: " + expiryDate;
                            JOptionPane.showMessageDialog(this, message, "User Profile", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Add view profile button to the top panel
        topPanel.add(viewProfileButton, BorderLayout.WEST);
    }


    private int getLoggedInUserId() {
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





    private void createLoginButton(JPanel topPanel) {
        JButton loginButton = new JButton("Login");
        JButton bookshelfButton = new JButton("Bookshelf"); // Add Bookshelf button

        // Styling and event handling for the login button
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.setBackground(Color.decode("#115DEE"));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setOpaque(true);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(e -> {
            // Handle login button action, open login page
            new NewClass2(); // Replace with your login page class
        });

        // Styling and event handling for the bookshelf button
        bookshelfButton.setPreferredSize(new Dimension(100, 40));
        bookshelfButton.setBackground(Color.decode("#115DEE"));
        bookshelfButton.setForeground(Color.WHITE);
        bookshelfButton.setFont(new Font("Arial", Font.PLAIN, 16));
        bookshelfButton.setBorder(BorderFactory.createEmptyBorder());
        bookshelfButton.setOpaque(true);
        bookshelfButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        bookshelfButton.addActionListener(e -> {
            // Handle bookshelf button action
            displayBookshelfDetails(); // Call method to display bookshelf details
        });


        // Add buttons to the top panel
        topPanel.add(bookshelfButton, BorderLayout.WEST); // Add Bookshelf button
        topPanel.add(loginButton, BorderLayout.EAST);




    }
    private void displayBookshelfDetails() {
        int user_id = getUserId(); // Replace this with the actual user ID retrieval logic
        if (user_id != -1) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM bookshelf WHERE user_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, user_id);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        StringBuilder userDetails = new StringBuilder();
                        userDetails.append("YOUR BOOKSHELF:\n");

                        // Iterate through each row in the result set
                        while (rs.next()) {
                            userDetails.append("User ID: ").append(rs.getInt("user_id")).append("\n");
                            userDetails.append("Book ID ").append(rs.getInt("book_id")).append("\n");
                            userDetails.append("Added Date: ").append(rs.getDate("added_date")).append("\n");
                            userDetails.append("\n"); // Add newline between each user's details
                        }

                        if (userDetails.length() > 0) {
                            // Remove the last newline character
                            userDetails.deleteCharAt(userDetails.length() - 1);
                            JOptionPane.showMessageDialog(this, userDetails.toString(), "User Details", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "User's bookshelf is empty.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error retrieving bookshelf details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "User ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayUserDetails(ResultSet rs) {
        try {
            StringBuilder userDetails = new StringBuilder();
            userDetails.append("YOUR BOOKSHELF:\n");
            userDetails.append("User ID: ").append(rs.getInt("user_id")).append("\n");
            userDetails.append("Book ID ").append(rs.getInt("book_id")).append("\n");
            userDetails.append("Added Date: ").append(rs.getDate("added_date")).append("\n");

            // Add more details as needed

            JOptionPane.showMessageDialog(this, userDetails.toString(), "User Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error displaying user details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Replace this method with the actual user ID retrieval logic
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




    private void createCategoryPanel(JPanel topPanel) {
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        String[] categories = {
                "Bookshelf", "Romance", "Thriller & Crime", "Young Adult",
                "Kids", "Sci-fi & Fantasy", "Nonfiction", "Poetry & Drama",
                "Business", "Lifestyle & Sports", "Classics", "Self-Help",
                "Performing Arts", "Fiction"
        };

        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setForeground(new Color(100, 96, 96));
            categoryButton.setBackground(Color.WHITE);
            categoryButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            categoryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            categoryButton.setFocusPainted(false);
            categoryButton.setFont(new Font("Arial", Font.BOLD, 14));

            // Add hover effects
            categoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    categoryButton.setForeground(Color.BLUE);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    categoryButton.setForeground(new Color(100, 96, 96));
                }
            });

            // Add action listener for category button
            categoryButton.addActionListener(e -> {
                System.out.println("Category: " + category + " button clicked");
                // Call a method to handle category selection
            });

            // Add button to the category panel
            categoryPanel.add(categoryButton);
        }

        // Add the category panel to the top panel
        topPanel.add(categoryPanel, BorderLayout.CENTER);
    }

    private void createMainContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add image sliders for each category
        contentPanel.add(createImageSliderPanel1("Top Pics of the Week"));
        contentPanel.add(createImageSliderPanel2("Recommendations"));
        contentPanel.add(createImageSliderPanel3("Recently Added"));

        // Wrap the content panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scrollPane to the main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createImageSliderPanel1(String title) {
        JPanel imageSliderPanel = new JPanel();
        imageSliderPanel.setLayout(new BoxLayout(imageSliderPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        imageSliderPanel.add(titleLabel);

        // Create a panel to hold the images in a grid layout with 1 row and 5 columns
        // Modify the horizontal and vertical gaps to adjust the spacing
        JPanel imagesPanel = new JPanel(new GridLayout(1, 5, 5, 0));  // Set grid layout to 1 row, 5 columns

        // Define image width and height
        int imageWidth = 200;  // Adjust the width as desired
        int imageHeight = 300; // Adjust the height as desired

        // Get book titles for the given category
        final String[] bookTitles = getBookTitlesForCategory(title);

        for (int i = 0; i < Math.min(5, bookTitles.length); i++) {
            JPanel imageContainer = new JPanel();
            imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS));
            imageContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Load image
            ImageIcon imageIcon = new ImageIcon("b" + (i + 1) + ".jpg");
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledImageIcon);
            imageContainer.add(imageLabel);

            // Display book title below the image
            JLabel titleLabelImage = new JLabel(bookTitles[i]);
            titleLabelImage.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabelImage.setFont(new Font("Arial", Font.PLAIN, 16));
            titleLabelImage.setForeground(Color.BLACK);
            imageContainer.add(titleLabelImage);

            // Add action listener for book title click
            final int index = i;
            titleLabelImage.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    handleBookTitleClick(bookTitles[index]);
                }
            });

            // Add the image container to the images panel
            imagesPanel.add(imageContainer);
        }

        // Wrap the images panel in a JScrollPane
        JScrollPane imagesScrollPane = new JScrollPane(imagesPanel);
        imagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar
        imagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scrollbar
        imagesScrollPane.setBorder(BorderFactory.createEmptyBorder());

        imageSliderPanel.add(imagesScrollPane);
        return imageSliderPanel;
    }
    private JPanel createImageSliderPanel2(String title) {
        // Create the main panel with a vertical box layout
        JPanel imageSliderPanel = new JPanel();
        imageSliderPanel.setLayout(new BoxLayout(imageSliderPanel, BoxLayout.Y_AXIS));

        // Add the title label to the panel
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        imageSliderPanel.add(titleLabel);

        // Create an images panel with a grid layout (1 row and 5 columns)
        JPanel imagesPanel = new JPanel(new GridLayout(1, 5, 5, 0)); // 5 columns

        // Define image width and height
        int imageWidth = 200; // Adjust as needed
        int imageHeight = 280; // Adjust as needed

        // Get book titles for the given category
        final String[] bookTitles = getBookTitlesForCategory(title);

        // Loop through indices 5 to 9 to display images from b6 to b10
        // Loop through indices 5 to 9 to display images from b6 to b10
        for (int i = 5; i <= 9; i++) {
            // Check if the index is within the bounds of the bookTitles array
            if (i >= bookTitles.length) {
                break; // Stop the loop if the index is out of bounds
            }

            // Declare a final variable for the index
            final int index = i;

            // Create an image container with a vertical box layout
            JPanel imageContainer = new JPanel();
            imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS));
            imageContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Load the image for the current book
            ImageIcon imageIcon = new ImageIcon("b" + (index + 1) + ".jpg"); // b6 to b10
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledImageIcon);
            imageContainer.add(imageLabel);

            // Display book title below the image
            JLabel titleLabelImage = new JLabel(bookTitles[index]);
            titleLabelImage.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabelImage.setFont(new Font("Arial", Font.PLAIN, 16));
            titleLabelImage.setForeground(Color.BLACK);
            imageContainer.add(titleLabelImage);

            // Add action listener for book title click
            titleLabelImage.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    handleBookTitleClick(bookTitles[index]);
                }
            });

            // Add the image container to the images panel
            imagesPanel.add(imageContainer);
        }

        // Wrap the images panel in a JScrollP11111111ww222wwwwweane
        JScrollPane imagesScrollPane = new JScrollPane(imagesPanel);
        imagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar
        imagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scrollbar
        imagesScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add the scroll pane to the main panel and return it
        imageSliderPanel.add(imagesScrollPane);
        return imageSliderPanel;
    }

    ////    private JPanel createImageSliderPanel3(String title) {
//         Create the main panel with a vertical box layout
//        JPanel imageSliderPanel = new JPanel();
//        imageSliderPanel.setLayout(new BoxLayout(imageSliderPanel, BoxLayout.Y_AXIS));
//
//        // Add the title label to the panel
//        JLabel titleLabel = new JLabel(title);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titleLabel.setForeground(Color.BLACK);
//        imageSliderPanel.add(titleLabel);
//
//        // Create an images panel with a grid layout (1 row and 5 columns)
//        JPanel imagesPanel = new JPanel(new GridLayout(1, 5, 5, 0)); // 5 columns
//
//        // Define image width and height
//        int imageWidth = 200; // Adjust as needed
//        int imageHeight = 300; // Adjust as needed
//
//        // Get book titles for the given category
//        final String[] bookTitles = getBookTitlesForCategory(title);
//
//        // Loop through indices 10 to 14 to display images from b11 to b15
//        for (int i = 10; i <= 14 && i < bookTitles.length; i++) {
//            // Create an image container with a vertical box layout
//            JPanel imageContainer = new JPanel();
//            imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS));
//            imageContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//            // Load the image for the current book
//            ImageIcon imageIcon = new ImageIcon("b" + (i + 1) + ".jpg"); // b11 to b15
//            Image image = imageIcon.getImage();
//            Image scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
//            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
//            JLabel imageLabel = new JLabel(scaledImageIcon);
//            imageContainer.add(imageLabel);
//
//            // Display book title below the image
//            JLabel titleLabelImage = new JLabel(bookTitles[i]);
//            titleLabelImage.setHorizontalAlignment(SwingConstants.CENTER);
//            titleLabelImage.setFont(new Font("Arial", Font.PLAIN, 16));
//            titleLabelImage.setForeground(Color.BLACK);
//            imageContainer.add(titleLabelImage);
//
//            // Add action listener for book title click
//            int index = i; // Make the index effectively final
//            titleLabelImage.addMouseListener(new java.awt.event.MouseAdapter() {
//                @Override
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    handleBookTitleClick(bookTitles[index]);
//                }
//            });
//
//            // Add the image container to the images panel
//            imagesPanel.add(imageContainer);
//        }
//
//        // Add the images panel to the main panel
//        JScrollPane imagesScrollPane = new JScrollPane(imagesPanel);
//        imagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar
//        imagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scrollbar
//        imagesScrollPane.setBorder(BorderFactory.createEmptyBorder());
//
//        // Add the scroll pane to the main panel and return it
//        imageSliderPanel.add(imagesScrollPane);
//
//        // Return the image slider panel
//        return imageSliderPanel;
//    }
    private JPanel createImageSliderPanel3(String title) {
        // Create the main panel with a vertical box layout
        JPanel imageSliderPanel = new JPanel();
        imageSliderPanel.setLayout(new BoxLayout(imageSliderPanel, BoxLayout.Y_AXIS));

        // Add the title label to the panel
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        imageSliderPanel.add(titleLabel);

        // Create an images panel with a grid layout (1 row and 5 columns)
        JPanel imagesPanel = new JPanel(new GridLayout(1, 5, 5, 0)); // 5 columns

        // Define image width and height
        int imageWidth = 200; // Adjust as needed
        int imageHeight = 280; // Adjust as needed

        // Get book titles for the given category
        final String[] bookTitles = getBookTitlesForCategory(title);

        // Loop through indices 5 to 9 to display images from b6 to b10
        // Loop through indices 5 to 9 to display images from b6 to b10
        for (int i = 10; i <= 14; i++) {
            // Check if the index is within the bounds of the bookTitles array
            if (i >= bookTitles.length) {
                break; // Stop the loop if the index is out of bounds
            }

            // Declare a final variable for the index
            final int index = i;

            // Create an image container with a vertical box layout
            JPanel imageContainer = new JPanel();
            imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS));
            imageContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Load the image for the current book
            ImageIcon imageIcon = new ImageIcon("b" + (index + 1) + ".jpg"); // b6 to b10
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledImageIcon);
            imageContainer.add(imageLabel);

            // Display book title below the image
            JLabel titleLabelImage = new JLabel(bookTitles[index]);
            titleLabelImage.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabelImage.setFont(new Font("Arial", Font.PLAIN, 16));
            titleLabelImage.setForeground(Color.BLACK);
            imageContainer.add(titleLabelImage);

            // Add action listener for book title click
            titleLabelImage.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    handleBookTitleClick(bookTitles[index]);
                }
            });

            // Add the image container to the images panel
            imagesPanel.add(imageContainer);
        }

        // Wrap the images panel in a JScrollPane
        JScrollPane imagesScrollPane = new JScrollPane(imagesPanel);
        imagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar
        imagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scrollbar
        imagesScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Add the scroll pane to the main panel and return it
        imageSliderPanel.add(imagesScrollPane);
        return imageSliderPanel;
    }

    //fine
    //fine method to handle book title clicks
    private void handleBookTitleClick(String bookTitle) {
        System.out.println("Book title clicked: " + bookTitle);
        try {
            BookDetails bookDetails = new BookDetails();
            com.example.bookclass.BookClass.Book book = bookDetails.fetchBookDetails(bookTitle);

            System.out.println("Fetched book details: " + book);
            if (book != null) {
                new BookDetailsDialog(this, book);
            } else {
                JOptionPane.showMessageDialog(this, "Book details not found for the selected title.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {  // Catch more general Exception if needed
            System.err.println("Error fetching book details: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error fetching book details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }


    // Sample method for fetching book titles for a category
    // Replace this method with actual database query or data source lookup
    private String[] getBookTitlesForCategory(String category) {
        // This is just a sample implementation; replace with actual database queries
        switch (category) {
            case "Top Pics of the Week":
                return new String[] {
                        "The Great Gatsby", "Soul", "1984", "Love",
                        "Unspeakable Beauty", "The Great Gatsby", "1984", "Soul",
                        "Unspeakable Beauty", "Love"
                };
            case "Recommendations":
                return new String[] {
                        "Stephen King", "Rosy", "Story Book", "Sleeping Magic",
                        "Children Book", "Stephen King", "Rosy", "Story Book",
                        "Sleeping Magic", "Children Book"
                };
            case "Recently Added":
                return new String[] {
                        "New Book 1", "New Book 2", "New Book 3", "New Book 4",
                        "New Book 5", "New Book 6", "New Book 7", "New Book 8",
                        "New Book 9", "New Book 10",
                        "New Book 9", "New Book 10",
                        "New Book 9", "New Book 10","New book11"
                };
            default:
                return new String[0];
        }
    }

    // Inner classes for database connection and book details dialog remain as they were

    public static void main(String[] args) {
        // Create and display the HomePage2 frame
        new HomePage2();
    }
}