import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class BookSearchServlet extends HttpServlet {
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

            String sql = "SELECT * FROM books WHERE ";

            switch (searchType) {
                case "ACCNO":
                    sql += "acc_no LIKE '%" + searchTerm + "%'";
                    break;
                case "TITLE":
                    sql += "title LIKE '%" + searchTerm + "%'";
                    break;
                case "AUTHOR":
                    sql += "author LIKE '%" + searchTerm + "%'";
                    break;
                case "COURSE":
                    sql += "course LIKE '%" + searchTerm + "%'";
                    break;
                case "DEPARTMENT":
                    sql += "department LIKE '%" + searchTerm + "%'";
                    break;
                case "NEWSPAPER":
                    sql += "newspaper LIKE '%" + searchTerm + "%'";
                    break;
                default:
                    break;
            }

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<table>");
            out.println("<tr><th>Title</th><th>Author</th><th>Acc No</th><th>Department</th><th>Course</th><th>Newspaper</th></tr>");

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String accNo = rs.getString("acc_no");
                String department = rs.getString("department");
                String course = rs.getString("course");
                String newspaper = rs.getString("newspaper");
                String newspaperUrl = rs.getString("newspaper_url");

                out.println("<tr>");
                out.println("<td>" + title + "</td>");
                out.println("<td>" + author + "</td>");
                out.println("<td>" + accNo + "</td>");
                out.println("<td>" + department + "</td>");
                out.println("<td>" + course + "</td>");

                if (newspaperUrl != null && !newspaperUrl.isEmpty()) {
                    out.println("<td><a href=\"" + newspaperUrl + "\" target=\"_blank\">" + newspaper + "</a></td>");
                } else {
                    out.println("<td>" + newspaper + "</td>");
                }

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
