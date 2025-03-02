import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Complaints")
public class Complaints extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/225d5";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root123";
    
    // SQL query to insert complaint or suggestion into database
    private static final String SQL_INSERT_COMPLAINT = "INSERT INTO complaints (type, date, text) VALUES (?, ?, ?)";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String type = request.getParameter("type");
        String date = request.getParameter("date");
        String text = request.getParameter("text");
        
        // Validate form data (basic validation)
        if (type == null || date == null || text == null || type.isEmpty() || date.isEmpty() || text.isEmpty()) {
            // Handle invalid data (optional)
            response.sendRedirect("complaint.html");
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // Connect to MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            
            // Create prepared statement for insertion
            pstmt = conn.prepareStatement(SQL_INSERT_COMPLAINT);
            pstmt.setString(1, type);  // 'type' will be either "complaint" or "suggestion"
            pstmt.setString(2, date);
            pstmt.setString(3, text);
            
            // Execute the statement
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Successful insertion
                response.sendRedirect("complaint.html"); // Redirect to complaint.html after successful submission
            } else {
                // Handle failure to insert (optional)
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to submit complaint/suggestion");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle database errors
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
