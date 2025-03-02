import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/facultyLogin")
public class FacultyLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");

            // Prepare SQL query to fetch user from database
            pstmt = con.prepareStatement("SELECT * FROM faculty WHERE id = ? AND password = ?");
            pstmt.setString(1, id);
            pstmt.setString(2, password); // In a real application, hash the password before comparing
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // If login successful, store username and id in session
                HttpSession session = request.getSession();
                session.setAttribute("id", id);
                session.setAttribute("username", rs.getString("username"));
                
                // Redirect to faculty profile page using JavaScript to replace current window
                String redirectHtml = "<html><head><script>window.top.location.href='facultyhome.html';</script></head><body></body></html>";
                response.setContentType("text/html");
                response.getWriter().println(redirectHtml);
            } else {
                // If user does not exist or credentials are incorrect, show alert and redirect to login page
                String alertHtml = "<html><head><script>alert('Invalid id or password'); window.top.location.href='facultyLogin.jsp';</script></head><body></body></html>";
                response.setContentType("text/html");
                response.getWriter().println(alertHtml);
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle errors
            response.getWriter().println("ERROR: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                response.getWriter().println("ERROR closing resources: " + e.getMessage());
            }
        }
    }
}
