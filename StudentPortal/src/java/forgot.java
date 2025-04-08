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

@WebServlet("/forgotpassword")
public class forgot extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "");
                
                String query = "SELECT * FROM users WHERE name=? AND email=? AND phone=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    response.sendRedirect("reset_password.html?email=" + email);
                } else {
                    out.println("<script>alert('No user found with these details. Try again!'); window.location.href='forgot.html';</script>");
                }
                con.close();
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            }
        }
    }
}
