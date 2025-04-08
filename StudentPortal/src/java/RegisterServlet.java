import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/School";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String fatherName = request.getParameter("fatherName");
        String motherName = request.getParameter("motherName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String grade = request.getParameter("grade");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String religion = request.getParameter("religion");
        String gender = request.getParameter("gender");
        String profileImage = request.getParameter("imglink");
        String password = request.getParameter("password");

        // Validate the fields
        if (userId.isEmpty() || name.isEmpty() || fatherName.isEmpty() || motherName.isEmpty() ||
            email.isEmpty() || phone.isEmpty() || grade.isEmpty() || city.isEmpty() || state.isEmpty() ||
            religion.isEmpty() || gender.isEmpty() || profileImage.isEmpty() || password.isEmpty()) {
            response.getWriter().println("All fields are required.");
            return;
        }

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Corrected SQL query for inserting user data (no need for created_at field since MySQL uses CURRENT_TIMESTAMP)
            String query = "INSERT INTO users (userId, name, fatherName, motherName, email, phone, grade, city, state, religion, gender, profileImage, password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, name);
            pstmt.setString(3, fatherName);
            pstmt.setString(4, motherName);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.setString(7, grade);
            pstmt.setString(8, city);
            pstmt.setString(9, state);
            pstmt.setString(10, religion);
            pstmt.setString(11, gender);
            pstmt.setString(12, profileImage);
            pstmt.setString(13, password); // Store the password as entered

            // Execute the insert
            int result = pstmt.executeUpdate();

            // Handle the result
            if (result > 0) {
                response.sendRedirect("Dashboard");
            } else {
                response.getWriter().println("Error adding user.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
