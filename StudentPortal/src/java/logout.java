import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the current session
        HttpSession session = request.getSession(false); // `false` means don't create a new session if it doesn't exist

        if (session != null) {
            // Invalidate the session (clears all session attributes)
            session.invalidate();
        }

        // Redirect to the login page
        response.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Call the doGet method to handle POST requests
        doGet(request, response);
    }
}