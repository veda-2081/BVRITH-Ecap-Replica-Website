<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Faculty Profile</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #ffffff; 
        }

        .rect {
            padding: 10px;
            background: #e6f2ff;
            color: #000000;
            margin-bottom: 10px;
        }

        h2, h3 {
            font-weight: normal;
        }

        .rect h3 {
            margin: 0;
            font-size: 18px;
            font-weight: normal;
        }

        .rect h2 {
            margin: 0;
            font-size: 16px;
            font-weight: normal;
            color: rgb(7, 48, 100);
        }

        .rect table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .rect th, .rect td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }

        .rect th {
            background-color: #f2f2f2;
        }

        .rect .toggle-link {
            color: rgb(7, 48, 100);
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
        }

        .rect .toggle-link:hover {
            text-decoration: underline;
        }

        .rect .toggle-content {
            background-color: white;
            display: none;
        }

        .button {
            width: 150px;
            height: 30px;
            background-color: #70a9df;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        .button1 {
            width: 150px;
            height: 30px;
            background-color: #70a9df;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
            margin-left: 10px;
        }
    </style>
    <script>
        function toggleTable(tableId) {
            var table = document.getElementById(tableId);
            var currentDisplay = table.style.display;
            table.style.display = (currentDisplay === 'none') ? 'table' : 'none';
        }

        function displayForm() {
            alert("Functionality to print or export data goes here.");
        }
    </script>
</head>
<body>
    <h3 style="color: rgb(7, 48, 100);">FACULTY PROFILE</h3>
    <h2>---------------------------------------------------------------------------------------------------------------------------------------------------------</h2>
    
    <% 
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");
            
            // Retrieve faculty ID from session
            String facultyId = (String) session.getAttribute("facultyId");

            // Prepare SQL query to fetch faculty details from faculty table
            String query = "SELECT * FROM faculty WHERE faculty_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, facultyId);
            rs = pstmt.executeQuery();
            
            // Display faculty details
            if (rs.next()) {
                %>
                <div class="rect">
                    <h3><span class="toggle-link" onclick="toggleTable('personalInformation')">Personal Information</span></h3>
                    <table id="personalInformation" class="toggle-content">
                        <tr>
                            <th>Name</th>
                            <td><%= rs.getString("name") %></td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <td><%= rs.getString("email") %></td>
                        </tr>
                        <tr>
                            <th>Phone</th>
                            <td><%= rs.getString("phone") %></td>
                        </tr>
                        <!-- Add more personal information fields as needed -->
                    </table>
                </div>

                <div class="rect">
                    <h3><span class="toggle-link" onclick="toggleTable('educationalQualifications')">Educational Qualifications</span></h3>
                    <table id="educationalQualifications" class="toggle-content">
                        <tr>
                            <th>Qualification</th>
                            <td><%= rs.getString("qualification") %></td>
                        </tr>
                        <tr>
                            <th>Institution</th>
                            <td><%= rs.getString("institution") %></td>
                        </tr>
                        <!-- Add more educational qualifications fields as needed -->
                    </table>
                </div>

                <div class="rect">
                    <h3><span class="toggle-link" onclick="toggleTable('papersPublished')">Papers Published</span></h3>
                    <table id="papersPublished" class="toggle-content">
                        <tr>
                            <th>Title</th>
                            <td><%= rs.getString("papers_published") %></td>
                        </tr>
                        <!-- Add more papers published fields as needed -->
                    </table>
                </div>

                <div class="rect">
                    <h3><span class="toggle-link" onclick="toggleTable('fdpsAttended')">FDP's Attended</span></h3>
                    <table id="fdpsAttended" class="toggle-content">
                        <tr>
                            <th>FDP</th>
                            <td><%= rs.getString("fdps_attended") %></td>
                        </tr>
                        <!-- Add more FDP fields as needed -->
                    </table>
                </div>
                <%
            } else {
                out.println("<p>No records found.</p>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error retrieving data: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                out.println("Error closing resources: " + e.getMessage());
            }
        }
    %>

    <button type="button" class="button" onclick="displayForm()">Print</button>
    <button type="button" class="button1" onclick="displayForm()">Export</button>
</body>
</html>