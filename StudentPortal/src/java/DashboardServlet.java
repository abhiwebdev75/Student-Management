import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/School";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String userId = request.getParameter("id");
            deleteStudent(userId, response);
            return;
        }

        String userId = request.getParameter("userId");
        if (userId != null) {
            showStudentDetails(userId, response);
            return;
        }

        // Fetch all users and show in the table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY userId ASC");

            out.println("<html><head><title>Admin Dashboard</title>");
            out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css' rel='stylesheet' />");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; }");
            out.println("h1 { text-align: center; color: #333; margin-top: 20px; }");
            out.println(".container { width: 90%; margin: auto; overflow: hidden; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("th, td { padding: 12px; text-align: left; border: 1px solid #ddd; }");
            out.println("th { background-color: #4CAF50; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println(".btn { padding: 10px 20px; font-size: 16px; cursor: pointer; color: white; border: none; border-radius: 5px; }");
            out.println(".btn-add { background-color: #5bc0de; }");
            out.println(".btn-edit { background-color: #f0ad4e; }");
            out.println(".btn-delete { background-color: #d9534f; }");
            out.println(".btn-view { background-color: #4CAF50; }");
            out.println(".btn-logout { background-color: #333; position: absolute; top: 20px; right: 20px; }");
            out.println("</style>");
            out.println("</head><body>");

            // Logout Button
            out.println("<a href='logout'><button class='btn btn-logout'><i class='fas fa-sign-out-alt'></i> Logout</button></a>");

            out.println("<div class='container'>");
            out.println("<h1>Student Information</h1>");
            out.println("<div><a href='AddUserServlet'><button class='btn btn-add'><i class='fas fa-plus'></i> Add New User</button></a></div><br>");
            out.println("<div><a href='Student_Test_Grades.html'><button class='btn btn-add'><i class='fas fa-plus'></i> Add User Grades</button></a></div>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Grade</th><th>City</th><th>Gender</th><th>Phone</th><th>Actions</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("userId") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("grade") + "</td>");
                out.println("<td>" + rs.getString("city") + "</td>");
                out.println("<td>" + rs.getString("gender") + "</td>");
                out.println("<td>" + rs.getString("phone") + "</td>");
                out.println("<td>");
                out.println("<a href='Dashboard?userId=" + rs.getInt("userId") + "'><button class='btn btn-view'>View</button></a>");
                out.println("<a href='EditUser?userId=" + rs.getInt("userId") + "'><button class='btn btn-edit'>Edit</button></a>");
                out.println("<a href='Dashboard?action=delete&id=" + rs.getInt("userId") + "'><button class='btn btn-delete' onclick='return confirm(\"Are you sure?\");'><i class='fas fa-trash'></i> Delete</button></a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</div></body></html>");

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error loading data.</p>");
        }
    }

    private void showStudentDetails(String userId, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE userId = ?");
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                out.println("<html><head><title>Student Details</title>");
                out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css' rel='stylesheet' />");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; }");
                out.println(".modal { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0, 0, 0, 0.7); display: flex; justify-content: center; align-items: center; }");
                out.println(".modal-content { background-color: white; padding: 20px; border-radius: 10px; width: 80%; position: relative; }");
                out.println(".close { position: absolute; top: 10px; right: 10px; font-size: 30px; cursor: pointer; }");
                out.println(".profile-image { position: absolute; top: 20px; right: 120px; width: 250px; height: 250px; border-radius: 50%; border: 3px solid #ddd; }");
                out.println("</style>");
                out.println("</head><body>");
                out.println("<div class='modal'>");
                out.println("<div class='modal-content'>");

                // Profile Image Section
                String profileImage = rs.getString("profileImage");
                if (profileImage != null && !profileImage.isEmpty()) {
                    out.println("<img class='profile-image' src='" + profileImage + "' alt='Profile Image' />");
                }

                out.println("<span class='close' onclick='window.location.href=\"Dashboard\"'>&times;</span>");
                out.println("<h2>Student Details</h2>");
                out.println("<p><strong>Name:</strong> " + rs.getString("name") + "</p>");
                out.println("<p><strong>Father's Name:</strong> " + rs.getString("fatherName") + "</p>");
                out.println("<p><strong>Mother's Name:</strong> " + rs.getString("motherName") + "</p>");
                out.println("<p><strong>Email:</strong> " + rs.getString("email") + "</p>");
                out.println("<p><strong>Phone:</strong> " + rs.getString("phone") + "</p>");
                out.println("<p><strong>Grade:</strong> " + rs.getString("grade") + "</p>");
                out.println("<p><strong>City:</strong> " + rs.getString("city") + "</p>");
                out.println("<p><strong>State:</strong> " + rs.getString("state") + "</p>");
                out.println("<p><strong>Religion:</strong> " + rs.getString("Religion") + "</p>");
                out.println("<p><strong>Gender:</strong> " + rs.getString("gender") + "</p>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body></html>");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching student details.</p>");
        }
    }

    private void deleteStudent(String userId, HttpServletResponse response) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "DELETE FROM users WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            response.sendRedirect("Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}