import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet {

    // Admin credentials
    private static final String ADMIN_EMAIL = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school"; // Your database URL
    private static final String DB_USER = "root"; // Your database username
    private static final String DB_PASSWORD = ""; // Your database password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the email and password from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check if it's admin login
        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            // Create a session for the admin user
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", true); // Set session attribute
            session.setAttribute("role", "admin");

            // Redirect to the admin dashboard
            response.sendRedirect("Dashboard");

        } else {
            // If it's not admin, check the user credentials in the database
            try {
                // Load JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Connect to the database
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Query to check if the email and password match in the users table
                String query = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                // Execute the query
                ResultSet rs = pstmt.executeQuery();

                // Check if a user exists with the provided email and password
                if (rs.next()) {
                    // User found, create a session
                    HttpSession session = request.getSession();
                    session.setAttribute("loggedIn", true); // Set session attribute
                    session.setAttribute("role", "user");

                    // Set user data in the session
                    session.setAttribute("userId",rs.getInt("userId"));
                    session.setAttribute("name", rs.getString("name"));
                    session.setAttribute("fatherName", rs.getString("fatherName"));
                    session.setAttribute("motherName", rs.getString("motherName"));
                    session.setAttribute("phone", rs.getString("phone"));
                    session.setAttribute("grade", rs.getString("grade"));
                    session.setAttribute("city", rs.getString("city"));
                    session.setAttribute("state", rs.getString("state"));
                    session.setAttribute("religion", rs.getString("religion"));
                    session.setAttribute("gender", rs.getString("gender"));
                    session.setAttribute("profileImage", rs.getString("profileImage"));

                    // Redirect to the user profile page
                    response.sendRedirect("userProfile");

                } else {
                    // If no user found, redirect with an error message
                    response.sendRedirect("index.html?error=invalid");
                }

                // Close resources
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("Error: " + e.getMessage());
            }
        }
    }
}