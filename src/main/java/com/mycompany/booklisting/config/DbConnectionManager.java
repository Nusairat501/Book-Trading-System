package com.mycompany.booklisting.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost/book_listing";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";

  public  static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
          e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                 e.printStackTrace();
            }
        }
    }
}
