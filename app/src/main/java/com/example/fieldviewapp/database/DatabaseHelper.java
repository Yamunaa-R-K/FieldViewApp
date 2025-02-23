package com.example.fieldviewapp.database;
import com.example.fieldviewapp.models.User;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://http/localhost:3306/FieldViewDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // User Table
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSWORD = "password";

    // Inspection Table
    private static final String TABLE_INSPECTION = "inspections";
    private static final String COLUMN_INSPECTION_ID = "inspection_id";
    private static final String COLUMN_INSPECTION_NAME = "inspection_name";
    private static final String COLUMN_INSPECTION_DATE = "inspection_date";
    private static final String COLUMN_INSPECTION_STATUS = "inspection_status";

    // Report Table
    private static final String TABLE_REPORT = "reports";
    private static final String COLUMN_REPORT_ID = "report_id";
    private static final String COLUMN_REPORT_NAME = "report_name";
    private static final String COLUMN_REPORT_DATE = "report_date";
    private static final String COLUMN_REPORT_DETAILS = "report_details";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Register User
    public boolean registerUser(String name, String email, String phone, String password) {
        String sql = "INSERT INTO " + TABLE_USER + " (name, email, phone, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login User
    public boolean checkUser(String email, String password) {
        String sql = "SELECT * FROM " + TABLE_USER + " WHERE email=? AND password=?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Insert Inspection
    public boolean addInspection(String name, String date, String status) {
        String sql = "INSERT INTO " + TABLE_INSPECTION + " (inspection_name, inspection_date, inspection_status) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, status);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Insert Report
    public boolean addReport(String name, String date, String details) {
        String sql = "INSERT INTO " + TABLE_REPORT + " (report_name, report_date, report_details) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, details);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public User getUserDetails(String email) {
        String sql = "SELECT * FROM " + TABLE_USER + " WHERE email=?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt(COLUMN_ID),      // User ID
                        rs.getString(COLUMN_NAME), // Name
                        rs.getString(COLUMN_EMAIL), // Email
                        rs.getString(COLUMN_PHONE)  // Phone
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no user found
    }
}
