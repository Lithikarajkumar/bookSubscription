package com.example.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.bookclass.BookClass;
import com.example.database.DatabaseConnection;
import com.example.subsplan.SubscriptionPlanPage;
public class BookDetails {

    public BookClass book;

    public com.example.bookclass.BookClass.Book fetchBookDetails(String bookTitle) {
        Connection conn = null;

        try {
            // Get a database connection
            conn = DatabaseConnection.getConnection();

            // Check if the connection was established
            if (conn == null) {
                System.err.println("Failed to establish a connection to the database.");
                return null;
            }

            // Define the SQL query to fetch book details by book title
            // Includes data from the author table
            String query = "SELECT "
                    + "b.book_id, b.book_name, b.genre_id, b.book_edition, b.isbn, "
                    + "a.author_name "
                    + "FROM book b "
                    + "JOIN author_book ab ON b.book_id = ab.book_id "
                    + "JOIN author a ON ab.author_id = a.author_id "
                    + "WHERE b.book_name = ?";

            // Prepare the query and execute it
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Set the query parameter for book title
                pstmt.setString(1, bookTitle);

                // Execute the query and get the result set
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Retrieve data from the ResultSet
                    int bookId = rs.getInt("book_id");
                    String bookName = rs.getString("book_name");
                    int genreId = rs.getInt("genre_id");
                    int bookEdition = rs.getInt("book_edition");
                    String isbn = rs.getString("isbn");
                    String authorName = rs.getString("author_name");

                    // Create a Book object using the retrieved data
                    com.example.bookclass.BookClass.Book book = new com.example.bookclass.BookClass.Book(
                            bookId, bookName, genreId, bookEdition, isbn, authorName
                    );

                    return book; // Return the Book object with additional details
                } else {
                    System.out.println("No book found with the title: " + bookTitle);
                    return null; // Return null if no book was found
                }
            }

        } catch (SQLException e) {
            // Log the exception message for debugging purposes
            System.err.println("Error fetching book details: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            // Always close the connection if it was opened
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing database connection: " + e.getMessage());
                }
            }
        }
    }
}
