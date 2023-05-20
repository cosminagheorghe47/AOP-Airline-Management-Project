package config;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getInstance() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://127.0.0.1:8111/proiect2";
            String username = "root";
            String password = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);

            } catch (SQLException e) {
                throw new IllegalStateException("Cannot connect to the database!", e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

}
