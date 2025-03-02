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

@WebServlet("/AdmissionServlet1")
public class AdmissionServlet1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String studentName = request.getParameter("studentName");
        String intermediateRegNo = request.getParameter("intermediateRegNo");
        String eamcetrank = request.getParameter("eamcetrank");
        String entranceType = request.getParameter("entranceType");
        String seatType = request.getParameter("seatType");
        String course = request.getParameter("course");
        String branch = request.getParameter("branch");
        String mobileNo = request.getParameter("mobileNo");
        String email = request.getParameter("email");
        String parentName = request.getParameter("parentName");
        String parentMobileNo = request.getParameter("parentMobileNo");
        String doorNo = request.getParameter("doorNo");
        String street = request.getParameter("street");
        String state = request.getParameter("state");
        String district = request.getParameter("district");
        String mandal = request.getParameter("mandal");
        String village = request.getParameter("village");
        String pin = request.getParameter("pin");
        String villageNotInList = request.getParameter("villageNotInList");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");

            // Prepare SQL query to insert data into database
            String sql = "INSERT INTO admission (username, password, studentName, intermediateRegNo, eamcetrank, entranceType, seatType, " +
                         "course, branch, mobileNo, email, parentName, parentMobileNo, doorNo, street, state, " +
                         "district, mandal, village, pin, villageNotInList) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, studentName);
            pstmt.setString(4, intermediateRegNo);
            pstmt.setString(5, eamcetrank);
            pstmt.setString(6, entranceType);
            pstmt.setString(7, seatType);
            pstmt.setString(8, course);
            pstmt.setString(9, branch);
            pstmt.setString(10, mobileNo);
            pstmt.setString(11, email);
            pstmt.setString(12, parentName);
            pstmt.setString(13, parentMobileNo);
            pstmt.setString(14, doorNo);
            pstmt.setString(15, street);
            pstmt.setString(16, state);
            pstmt.setString(17, district);
            pstmt.setString(18, mandal);
            pstmt.setString(19, village);
            pstmt.setString(20, pin);
            pstmt.setString(21, villageNotInList);

            // Execute the query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                // Redirect to home1.html after successful insertion
                response.sendRedirect("index.html");
            } else {
                // Handle failure to insert data
                response.getWriter().println("Failed to insert data into database.");
                response.sendRedirect("admission.html");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle errors
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
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
