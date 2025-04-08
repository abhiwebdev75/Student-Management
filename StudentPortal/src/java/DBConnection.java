import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver (optional in newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Set up connection parameters
            String url = "jdbc:mysql://localhost:3306/school"; // Change the URL if needed
            String user = "root"; // Replace with your DB username
            String password = ""; // Replace with your DB password

            // Establish connection
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error connecting to database", e);
        }
    }
}
