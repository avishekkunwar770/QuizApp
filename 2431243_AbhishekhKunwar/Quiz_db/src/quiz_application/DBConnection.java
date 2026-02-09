package quiz_application;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quizDB"; // Update with your DB URL
    private static final String USER = "root"; // Update with your DB username
    private static final String PASSWORD = "Password1#"; // Update with your DB password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet getLeaderboard() throws SQLException {
        Connection conn = getConnection();
        String query = "SELECT name, score, level AS difficulty FROM users ORDER BY score DESC";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
}