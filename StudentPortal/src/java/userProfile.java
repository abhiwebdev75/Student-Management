import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/userProfile")
public class userProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the session and check if the user is logged in
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn")) {
            // User is logged in, retrieve their information from the session
            int userId = (int) session.getAttribute("userId");
            String name = (String) session.getAttribute("name");
            String fatherName = (String) session.getAttribute("fatherName");
            String motherName = (String) session.getAttribute("motherName");
            String email = (String) session.getAttribute("email");
            String phone = (String) session.getAttribute("phone");
            String grade = (String) session.getAttribute("grade");
            String city = (String) session.getAttribute("city");
            String state = (String) session.getAttribute("state");
            String religion = (String) session.getAttribute("religion");
            String gender = (String) session.getAttribute("gender");
            String profileImage = (String) session.getAttribute("profileImage");

            // Set the response content type
            response.setContentType("text/html");

            // Get the PrintWriter object to write HTML to the response
            PrintWriter out = response.getWriter();

            // Generate the HTML content dynamically
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>User Profile</title>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap' rel='stylesheet'>");
            out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css' rel='stylesheet'>");
            out.println("<style>");
            out.println("body { font-family: 'Poppins', sans-serif; margin: 0; padding: 0; background: linear-gradient(135deg, #667eea, #764ba2); color: #333; }");
            out.println(".container { width: 90%; max-width: 800px; margin: 50px auto; padding: 30px; background: white; border-radius: 15px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); }");
            out.println("h1 { text-align: center; color: #444; margin-bottom: 30px; font-size: 2.5em; }");
            out.println(".profile-image { display: block; margin: 0 auto; width: 150px; height: 150px; border-radius: 50%; border: 5px solid #fff; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2); }");
            out.println(".user-detail { margin: 20px 0; font-size: 1.1em; padding: 15px; background: #f9f9f9; border-radius: 10px; display: flex; align-items: center; box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1); }");
            out.println(".user-detail i { margin-right: 15px; color: #667eea; font-size: 1.2em; }");
            out.println(".user-detail strong { color: #555; margin-right: 10px; }");
            out.println(".btn { padding: 12px 25px; font-size: 1em; background: #667eea; color: white; border: none; border-radius: 25px; cursor: button; text-align: center; display: block; margin: 30px auto; transition: background 0.3s ease; }");
            out.println(".btn:hover { background: #764ba2; }");
            out.println(".footer { text-align: center; margin-top: 50px; padding: 20px; background: rgba(255, 255, 255, 0.8); border-radius: 10px; }");
            out.println(".footer p { margin: 0; color: #555; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");
            out.println("<h1>User Profile</h1>");

            // Profile image centered at the top
            out.println("<img class='profile-image' src='" + profileImage + "' alt='Profile Image'>");

            // User details with icons
            out.println("<div class='user-detail'><i class='fas fa-user'></i><strong>Name:</strong> " + name + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-male'></i><strong>Father's Name:</strong> " + fatherName + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-female'></i><strong>Mother's Name:</strong> " + motherName + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-phone'></i><strong>Phone:</strong> " + phone + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-graduation-cap'></i><strong>Grade:</strong> " + grade + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-city'></i><strong>City:</strong> " + city + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-map-marker'></i><strong>State:</strong> " + state + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-pray'></i><strong>Religion:</strong> " + religion + "</div>");
            out.println("<div class='user-detail'><i class='fas fa-venus-mars'></i><strong>Gender:</strong> " + gender + "</div>");
            out.println("<div class='table-container'>");
            out.println("<h2>Subjects & Grades</h2>");
            out.println("<table border='1' style='width:100%; border-collapse: collapse;'>");
            out.println("<tr><th>Subject</th><th>Grade</th></tr>");

            // Establish database connection
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", ""); // Connect to DB

                // Debugging: Print userId to check if session is passing the correct value
                System.out.println("Fetching grades for userId: " + userId);

                String query = "SELECT subject_name, grade FROM student_subjects_grades WHERE userId = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, userId);
                rs = ps.executeQuery();

                // Check if we got results from the database
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    String subject = rs.getString("subject_name");
                    String studentGrade = rs.getString("grade");
                    out.println("<tr><td>" + subject + "</td><td>" + studentGrade + "</td></tr>");
                }

                // If no data found, show a message
                if (!hasData) {
                    out.println("<tr><td colspan='2' style='color:red;'>No grades found for this user.</td></tr>");
                }

            } catch (Exception e) {
                out.println("<tr><td colspan='2' style='color:red;'>Error fetching grades</td></tr>");
                e.printStackTrace(); // Print error to server logs
            } finally {
                // Close resources in proper order
                try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
                try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
                try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
            }

            out.println("</table>");
            out.println("</div>");

            // Logout button
            out.println("<form action='logout' method='POST'>");
            out.println("<button type='submit' class='btn'><i class='fas fa-sign-out-alt'></i> Logout</button>");
            out.println("</form>");

            out.println("</div>");

            // Footer
            out.println("<div class='footer'>");
            out.println("<p>&copy; 2025 User Profile Page</p>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        } else {
            // If the user is not logged in, redirect to the login page
            response.sendRedirect("index.html");
        }
    }
}