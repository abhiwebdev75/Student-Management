import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirm_password");

        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect("reset_password.html?error=mismatch");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("index.html?message=reset_success");
            } else {
                response.sendRedirect("reset_password.html?error=not_found");
            }

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("reset_password.html?error=server");
        }
    }
}
