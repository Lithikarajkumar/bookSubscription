package com.example.helper;


import com.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {


    public static String getPdfPath(int bookId) {
        String pdfPath = null;
        String query = "SELECT pdf_path FROM book_paths WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pdfPath = rs.getString("pdf_path");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pdfPath;
    }
}
