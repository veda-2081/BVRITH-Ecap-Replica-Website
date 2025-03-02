import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,   // 10MB
                 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String jdbcURL = "jdbc:mysql://localhost:3306/225d5";
    private String dbUser = "root";
    private String dbPassword = "root123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        Part filePart = request.getPart("pdfFile");
        String fileName = extractFileName(filePart);
        String contentType = filePart.getContentType();

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            String sql = "INSERT INTO pdf_files (filename, filedata, username) values (?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, fileName);
            pstmt.setBinaryStream(2, filePart.getInputStream());
            pstmt.setString(3, username);

            int row = pstmt.executeUpdate();
            if (row > 0) {
                request.setAttribute("message", "File uploaded successfully!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Error: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
}
