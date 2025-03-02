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

@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");

            String sql = "UPDATE admission SET studentName=?, intermediateRegNo=?, eamcetrank=?, entranceType=?, seatType=?, "
                         + "course=?, branch=?, mobileNo=?, email=?, parentName=?, parentMobileNo=?, doorNo=?, street=?, state=?, "
                         + "district=?, mandal=?, village=?, pin=?, villageNotInList=? WHERE username=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentName);
            pstmt.setString(2, intermediateRegNo);
            pstmt.setString(3, eamcetrank);
            pstmt.setString(4, entranceType);
            pstmt.setString(5, seatType);
            pstmt.setString(6, course);
            pstmt.setString(7, branch);
            pstmt.setString(8, mobileNo);
            pstmt.setString(9, email);
            pstmt.setString(10, parentName);
            pstmt.setString(11, parentMobileNo);
            pstmt.setString(12, doorNo);
            pstmt.setString(13, street);
            pstmt.setString(14, state);
            pstmt.setString(15, district);
            pstmt.setString(16, mandal);
            pstmt.setString(17, village);
            pstmt.setString(18, pin);
            pstmt.setString(19, villageNotInList);
            pstmt.setString(20, username); // Where clause

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.getWriter().println("User profile updated successfully");
                response.sendRedirect("studentprofile1.jsp");
            } else {
                response.getWriter().println("Failed to update user profile");
                response.sendRedirect("edit_profile.html");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Failed to update user profile");
            response.sendRedirect("edit_profile.html"); // Redirect to error page or form
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
