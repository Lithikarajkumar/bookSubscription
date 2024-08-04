package com.example.bookclass;
import com.example.util.BookDetails;

public class BookClass {

    public BookDetails details;

    // Define the Book class as a separate public class
    public static class Book {
        private int bookId;
        private String bookName;
        private String isbn;
        private int bookEdition;
        private int genreId;

        private String authorname;

        // Constructor
        public Book(int bookId, String bookName, int genreId, int bookEdition, String isbn,String authorname) {
            this.bookId = bookId;
            this.bookName = bookName;
            this.genreId = genreId;
            this.bookEdition = bookEdition;
            this.isbn = isbn;
            this.authorname=authorname;

            // Print the details of the book object
            System.out.println("Book Details:");
            System.out.println("Book ID: " + bookId);
            System.out.println("Book Name: " + bookName);
            System.out.println("Genre ID: " + genreId);
            System.out.println("Book Edition: " + bookEdition);
            System.out.println("ISBN: " + isbn);
            System.out.println("Authorname: " + authorname);
        }

        // Getters (not used in this specific example, but kept for potential future use)
        public int getBookId() {
            return bookId;
        }
        public String getAuthorname() {
            return authorname;
        }

        public String getBookName() {
            return bookName;
        }

        public String getIsbn() {
            return isbn;
        }

        public int getBookEdition() {
            return bookEdition;
        }

        public int getGenreId() {
            return genreId;
        }
    }








    public static void main(String[] args) {
        // Create an instance of the Book class with provided parameters
      //  Book book = new Book(
        //        1, // bookId
          //      "The Great Gatsby", // bookName
            //    2, // genreId
              //  1, // bookEdition
                //"9780743273565" // ISBN
        //);

        // Since the Book class already prints the details in its constructor,
        // you don't need to print them again here.
    }
}
