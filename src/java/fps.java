import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/fps")
public class fps extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form data
        String facultyCounsellor = request.getParameter("facultyCounsellor");
        String reason = request.getParameter("reason");
        String departureTime = request.getParameter("departureTime");

        // Database connection parameters
        String dbURL = "jdbc:mysql://localhost:3306/225d5";
        String dbUsername = "root";
        String dbPassword = "root123";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Register JDBC driver (MySQL Connector/J 8.x)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // SQL query to insert data into facultypermissionslip table
            String sql = "INSERT INTO facultypermissionslip (facultyCounsellor, reason, departureTime, requestTime) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setString(1, facultyCounsellor);
            stmt.setString(2, reason);
            stmt.setString(3, departureTime);

            // Get current date/time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String requestTime = now.format(formatter);
            stmt.setString(4, requestTime);

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Data inserted successfully
                // Send a response with JavaScript alert
                String alertScript = "<script>alert('Request Pending'); window.location.href='permissionDetails.jsp?facultyCounsellor="
                        + facultyCounsellor + "&reason=" + reason + "&departureTime=" + departureTime + "';</script>";
                response.getWriter().println(alertScript);
            } else {
                // Failed to insert data
                response.getWriter().println("<h3>Failed to submit request. Please try again.</h3>");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle errors
            ex.printStackTrace(); // Log the exception for debugging
            response.getWriter().println("<h3>Error: " + ex.getMessage() + "</h3>");
        } finally {
            // Clean-up resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Log the exception for debugging
            }
        }
    }
}
