

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String URL = "jdbc:sqlite:C:\\Users\\Asus\\IdeaProjects\\Athletics_Competitions\\ccccccr.db";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(URL);
    }
}