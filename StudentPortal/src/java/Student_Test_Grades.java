import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Student_Test_Grades")
public class Student_Test_Grades extends HttpServlet {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Get form parameters
            String userIdParam = request.getParameter("user_id");
            String subjectName = request.getParameter("subject_name");
            String grade = request.getParameter("grade");

            // Validate input
            if (userIdParam == null || subjectName == null || grade == null ||
                userIdParam.trim().isEmpty() || subjectName.trim().isEmpty() || grade.trim().isEmpty()) {
                out.println("<script>alert('All fields required!'); window.history.back();</script>");
                return;
            }

            int userId;
            try {
                userId = Integer.parseInt(userIdParam);
            } catch (NumberFormatException e) {
                out.println("<script>alert('Invalid User ID format!'); window.history.back();</script>");
                return;
            }

            // Insert data into the database
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertQuery = "INSERT INTO student_subjects_grades (userId, subject_name, grade) VALUES (?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                    ps.setInt(1, userId);
                    ps.setString(2, subjectName);
                    ps.setString(3, grade);

                    int result = ps.executeUpdate();
                    if (result > 0) {
                        out.println("<script>alert('Grade Entered Successfully !'); window.history.back();</script>");
                    } else {
                        out.println("<script>alert('Error!'); window.history.back();</script>");
                    }
                }
            } catch (SQLException e) {
                out.println("<script>alert('Database Error ! or Duplicate Subject'); window.history.back();</script>");
                e.printStackTrace();
            }
        }
    }
}
