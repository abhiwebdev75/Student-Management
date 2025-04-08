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

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/School";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Display the HTML form for adding a user
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Add User</title>");
        out.println("<link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css' rel='stylesheet' />");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
        out.println(".container { width: 50%; margin: auto; padding: 30px; background-color: white; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        out.println("h1 { text-align: center; color: #333; margin-bottom: 30px; }");
        out.println("label { font-weight: bold; margin-top: 10px; display: block; }");
        out.println("input, select { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 5px; }");
        out.println(".btn { padding: 10px 20px; font-size: 16px; background-color: #5bc0de; color: white; border: none; border-radius: 5px; cursor: pointer; }");
        out.println(".btn:hover { background-color: #31b0d5; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>Add New User</h1>");
        out.println("<form action='AddUserServlet' method='POST'>");

        // Form Fields
        out.println("<label for='userId'>User ID</label>");
        out.println("<input type='text' id='userId' name='userId' required />");

        out.println("<label for='name'>Name</label>");
        out.println("<input type='text' id='name' name='name' required />");

        out.println("<label for='fatherName'>Father's Name</label>");
        out.println("<input type='text' id='fatherName' name='fatherName' required />");

        out.println("<label for='motherName'>Mother's Name</label>");
        out.println("<input type='text' id='motherName' name='motherName' required />");

        out.println("<label for='email'>Email</label>");
        out.println("<input type='email' id='email' name='email' required />");

        out.println("<label for='phone'>Phone</label>");
        out.println("<input type='text' id='phone' name='phone' required />");

        out.println("<label for='grade'>Grade</label>");
        out.println("<input type='text' id='grade' name='grade' required />");

        out.println("<label for='city'>City</label>");
        out.println("<input type='text' id='city' name='city' required />");

        out.println("<label for='state'>State</label>");
        out.println("<select id='state' name='state' required>");
        out.println("<option value='' disabled selected>Select State</option>");out.println("<option value='Andhra Pradesh'>Andhra Pradesh</option>");
        out.println("<option value='Arunachal Pradesh'>Arunachal Pradesh</option>");
        out.println("<option value='Assam'>Assam</option>");
        out.println("<option value='Bihar'>Bihar</option>");
        out.println("<option value='Chhattisgarh'>Chhattisgarh</option>");
        out.println("<option value='Goa'>Goa</option>");
        out.println("<option value='Gujarat'>Gujarat</option>");
        out.println("<option value='Haryana'>Haryana</option>");
        out.println("<option value='Himachal Pradesh'>Himachal Pradesh</option>");
        out.println("<option value='Jharkhand'>Jharkhand</option>");
        out.println("<option value='Karnataka'>Karnataka</option>");
        out.println("<option value='Kerala'>Kerala</option>");
        out.println("<option value='Madhya Pradesh'>Madhya Pradesh</option>");
        out.println("<option value='Maharashtra'>Maharashtra</option>");
        out.println("<option value='Manipur'>Manipur</option>");
        out.println("<option value='Meghalaya'>Meghalaya</option>");
        out.println("<option value='Mizoram'>Mizoram</option>");
        out.println("<option value='Nagaland'>Nagaland</option>");
        out.println("<option value='Odisha'>Odisha</option>");
        out.println("<option value='Punjab'>Punjab</option>");
        out.println("<option value='Rajasthan'>Rajasthan</option>");
        out.println("<option value='Sikkim'>Sikkim</option>");
        out.println("<option value='Tamil Nadu'>Tamil Nadu</option>");
        out.println("<option value='Telangana'>Telangana</option>");
        out.println("<option value='Tripura'>Tripura</option>");
        out.println("<option value='Uttar Pradesh'>Uttar Pradesh</option>");
        out.println("<option value='Uttarakhand'>Uttarakhand</option>");
        out.println("<option value='West Bengal'>West Bengal</option>");

        out.println("<option value='Andaman and Nicobar Islands'>Andaman and Nicobar Islands</option>");
        out.println("<option value='Chandigarh'>Chandigarh</option>");
        out.println("<option value='Dadra and Nagar Haveli and Daman and Diu'>Dadra and Nagar Haveli and Daman and Diu</option>");
        out.println("<option value='Lakshadweep'>Lakshadweep</option>");
        out.println("<option value='Delhi'>Delhi</option>");
        out.println("<option value='Puducherry'>Puducherry</option>");
        out.println("<option value='Ladakh'>Ladakh</option>");
        out.println("<option value='Jammu and Kashmir'>Jammu and Kashmir</option>");

        out.println("</select>");

        out.println("<label for='religion'>Religion</label>");
        out.println("<select id='religion' name='religion' required>");
        out.println("<option value='' disabled selected>Select Religion</option>");
        out.println("<option value='Hindu'>Hindu</option>");
        out.println("<option value='Muslim'>Muslim</option>");
        out.println("<option value='Christian'>Christian</option>");
        out.println("<option value='Sikh'>Sikh</option>");
        out.println("<option value='Buddhist'>Buddhist</option>");
        out.println("<option value='Jain'>Jain</option>");
        out.println("<option value='Other'>Other</option>");
        out.println("</select>");

        out.println("<label for='gender'>Gender</label>");
        out.println("<input type='radio' id='male' name='gender' value='Male' required /> Male");
        out.println("<input type='radio' id='female' name='gender' value='Female' required /> Female");
        out.println("<input type='radio' id='other' name='gender' value='Other' required /> Other");

        out.println("<label for='imglink'>Profile Image Path</label>");
        out.println("<input type='text' id='imglink' name='imglink' required />");


        out.println("<button type='submit' class='btn'>Add User</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
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
        String profileImage = request.getParameter("imglink");
        String password = name;

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
