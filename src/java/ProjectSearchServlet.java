import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class ProjectSearchServlet extends HttpServlet {
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

            String sql = "SELECT * FROM projects WHERE ";

            switch (searchType) {
                case "TITLE":
                    sql += "title LIKE '%" + searchTerm + "%'";
                    break;
                case "LANGUAGE":
                    sql += "language LIKE '%" + searchTerm + "%'";
                    break;
                case "FRONTEND":
                    sql += "frontend LIKE '%" + searchTerm + "%'";
                    break;
                case "BACKEND":
                    sql += "backend LIKE '%" + searchTerm + "%'";
                    break;
                default:
                    break;
            }

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<table>");
            out.println("<tr><th>Title</th><th>Language/Subject</th><th>Frontend</th><th>Backend</th></tr>");

            while (rs.next()) {
                String title = rs.getString("title");
                String language = rs.getString("language");
                String frontend = rs.getString("frontend");
                String backend = rs.getString("backend");

                out.println("<tr>");
                out.println("<td>" + title + "</td>");
                out.println("<td>" + language + "</td>");
                out.println("<td>" + frontend + "</td>");
                out.println("<td>" + backend + "</td>");
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
