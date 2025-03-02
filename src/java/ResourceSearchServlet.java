import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class ResourceSearchServlet extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/225d5"; // Update with your database name

    private static final String USER = "root";
    private static final String PASS = "root123";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String searchType = request.getParameter("type");
        String searchTerm = request.getParameter("searchTerm");

        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT * FROM resources WHERE ";

            switch (searchType) {
                case "NAME":
                    sql += "name LIKE '%" + searchTerm + "%'";
                    break;
                case "TYPE":
                    sql += "type LIKE '%" + searchTerm + "%'";
                    break;
                case "LOCATION":
                    sql += "location LIKE '%" + searchTerm + "%'";
                    break;
                case "AVAILABILITY":
                    sql += "availability LIKE '%" + searchTerm + "%'";
                    break;
                default:
                    break;
            }

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<table>");
            out.println("<tr><th>Name</th><th>Type</th><th>Location</th><th>Availability</th></tr>");

            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String availability = rs.getString("availability");

                out.println("<tr>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + type + "</td>");
                out.println("<td>" + location + "</td>");
                out.println("<td>" + availability + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        out.close();
    }
}
