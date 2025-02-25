package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/mysqldata";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("ошибка подключения к бд");
        }
    }
    public static void close() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
