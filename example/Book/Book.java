package com.example.Book;
import com.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private int bookId;
    private static Connection conn;
    private String bookName;
    private String isbn;
    private int bookEdition;
    private String genreName;
    private String authorName;
    private String publisherName;
    private int genreId;  // genreId field
    private String pdfPath;

    // Add a constructor to initialize the genreId field if necessary
    public Book(String bookName, String isbn, int bookEdition, int genre) throws SQLException {
        this.conn = DatabaseConnection.getConnection();

        if (this.conn == null) {
            System.err.println("Failed to establish a connection to the database.");
            return;
        }

        this.bookName = bookName;
        this.isbn = isbn;
        this.bookEdition = bookEdition;
        this.genreId = genre;
    }

    public Book() {

    }

    public static List<Book> getRecommendedBooksFromBookshelf() {
        List<Book> recommendedBooks = new ArrayList<>();

        String query = "SELECT * FROM (SELECT book_id, COUNT(*) as frequency " +
                "FROM bookshelf " +
                "GROUP BY book_id " +
                "ORDER BY frequency DESC) " +
                "WHERE ROWNUM <= 2";


        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                Book book = getBookById(bookId, connection);
                if (book != null) {
                    recommendedBooks.add(book);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return recommendedBooks;
    }

    private static Book getBookById(int bookId, Connection connection) {
        Book book = null;
        String query = "SELECT * FROM book WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("book_name"));
                //book.setAuthor(resultSet.getString("author"));
               // book.setGenre(resultSet.getString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return book;
    }



    private static void displayRecommendedBooks(List<Book> recommendedBooks) {
        if (recommendedBooks.isEmpty()) {
            System.out.println("No recommended books found.");
        } else {
            System.out.println("Recommended Books:");
            for (Book book : recommendedBooks) {
                System.out.println("Title: " + book.getTitle());
                //System.out.println("Author: " + book.getAuthor());
                System.out.println("Genre: " + book.getGenre());
                System.out.println();
            }
        }
    }

    public int getId() {
        return bookId;
    }

    public String getTitle() {
        return bookName;
    }



    public String getGenre() {
        return genreName;
    }


    public void setId(int id) {
        this.bookId = id;
    }

    public void setTitle(String title) {
        this.bookName = title;
    }



    public void setGenre(String genre) {
        this.genreName = genre;
    }




    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }


    public String getPdfPath() {
        return pdfPath;
    }

    // Add a setter for the pdfPath
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    // Getter methods
    public String getBookName() {
        return bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getBookEdition() {
        return bookEdition;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }


    public static List<Book> getBooksByGenre(int genre) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE genre_id = ?");
        ) {
            stmt.setInt(1, genre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bookName = rs.getString("book_name");
                    String isbn = rs.getString("isbn");
                    int bookEdition = rs.getInt("book_edition");
                    //String authorName = rs.getString("author_name");
                   // String publisherName = rs.getString("publisher_name");
                    books.add(new Book(bookName, isbn, bookEdition, genre));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}




