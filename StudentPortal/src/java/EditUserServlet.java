import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/EditUser")
public class EditUserServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/School";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userId = request.getParameter("userId");

        if (userId == null || userId.isEmpty()) {
            out.println("User ID is required.");
            return;
        }

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Fetch the user data for the given ID
            String query = "SELECT * FROM users WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(userId));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Display the form with the existing user data pre-filled
                out.println("<html><head><title>Edit User</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }");
                out.println("h1 { text-align: center; color: #333; margin-top: 20px; }");
                out.println("form { width: 40%; margin: auto; padding-top: 20px; }");
                out.println("input { width: 100%; padding: 10px; margin: 5px 0; }");
                out.println("input[type='submit'] { background-color: #4CAF50; color: white; border: none; cursor: pointer; }");
                out.println("input[type='submit']:hover { background-color: #45a049; }");
                out.println("</style>");
                out.println("</head><body>");
                out.println("<h1>Edit User</h1>");
                out.println("<form action='EditUser' method='POST'>");
                out.println("<input type='hidden' name='userId' value='" + rs.getInt("userId") + "'>");
                out.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "' required><br>");
                out.println("Father's Name: <input type='text' name='fatherName' value='" + rs.getString("fatherName") + "' required><br>");
                out.println("Mother's Name: <input type='text' name='motherName' value='" + rs.getString("motherName") + "' required><br>");
                out.println("Email: <input type='email' name='email' value='" + rs.getString("email") + "' required><br>");
                out.println("Phone: <input type='text' name='phone' value='" + rs.getString("phone") + "' required><br>");
                out.println("Grade: <input type='text' name='grade' value='" + rs.getString("grade") + "' required><br>");
                out.println("City: <input type='text' name='city' value='" + rs.getString("city") + "' required><br>");
                out.println("State: <input type='text' name='state' value='" + rs.getString("state") + "' required><br>");
                out.println("Religion: <input type='text' name='religion' value='" + rs.getString("religion") + "' required><br>");
                out.println("Gender: <br>");
                out.println("<input type='radio' name='gender' value='Male' " + (rs.getString("gender").equals("Male") ? "checked" : "") + "> Male");
                out.println("<input type='radio' name='gender' value='Female' " + (rs.getString("gender").equals("Female") ? "checked" : "") + "> Female<br>");
                out.println("Profile Image URL: <input type='text' name='profileImage' value='" + rs.getString("profileImage") + "' required><br>");
                out.println("<input type='submit' value='Update User'>");
                out.println("</form>");
                out.println("</body></html>");
            } else {
                out.println("User not found!");
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("An error occurred while fetching the user.");
        }
    }

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
        String profileImage = request.getParameter("profileImage");

        HttpSession session = request.getSession();

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Hash the password if provided
            

            // Update the user details
            String query;
            PreparedStatement pstmt;
            
            query = "UPDATE users SET name = ?, fatherName = ?, motherName = ?, email = ?, phone = ?, grade = ?, city = ?, state = ?, religion = ?, gender = ?, profileImage = ? WHERE userId = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(12, Integer.parseInt(userId));
            

            pstmt.setString(1, name);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, motherName);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, grade);
            pstmt.setString(7, city);
            pstmt.setString(8, state);
            pstmt.setString(9, religion);
            pstmt.setString(10, gender);
            pstmt.setString(11, profileImage);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                session.setAttribute("message", "User updated successfully!");
            } else {
                session.setAttribute("message", "Failed to update user.");
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "An error occurred while updating the user.");
        }

        // Redirect back to the dashboard
        response.sendRedirect("Dashboard");
    }
}