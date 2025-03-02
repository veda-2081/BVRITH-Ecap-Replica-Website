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

@WebServlet("/Changepwd")
public class Changepwd extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String oldPassword = request.getParameter("old_pwd");
        String newPassword = request.getParameter("new_pwd");

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");

            // Check if old password matches the stored password for the user
            pstmt = con.prepareStatement("SELECT * FROM admission WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, oldPassword); // In a real application, hash the password before comparing
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                // If old password does not match
                response.getWriter().println("<script>alert('Old password is incorrect.'); window.location.href='change_pwd.html';</script>");
                return;
            }

            // Update the password in the database
            pstmt.close(); // Close previous statement

            pstmt = con.prepareStatement("UPDATE admission SET password = ? WHERE username = ?");
            pstmt.setString(1, newPassword); // In a real application, hash the password before storing
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Password updated successfully
                response.getWriter().println("<script>alert('Password changed successfully!'); window.top.location.href='home1.html';</script>");
            } else {
                // Handle error while updating password
                response.getWriter().println("<script>alert('Error changing password. Please try again later.'); window.top.location.href='change_pwd.html';</script>");
            }
        } catch (ClassNotFoundException | SQLException e) {
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
