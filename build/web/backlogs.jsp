<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Backlogs</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #ffffff;
        }

        .container {
            padding: 20px;
            background: #ffffff;
            color: #000000;
            margin: 20px;
        }

        h3,h2 {
            font-weight:normal;
            color: rgb(7, 48, 100);
        }

        .table-container {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background-color: #ffffff; /* Set table background color to white */
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
            font-size: 14px;
        }

        th {
            background-color: #f2f2f2;
        }

        .no-backlogs {
            font-size: 18px;
            color: #3498db; /* Light blue color */
            margin-top: 20px;
            text-align:center;
        }
    </style>
</head>
<body>
    <h3>Student Backlogs</h3>
    <h2>---------------------------------------------------------------------------------------------------------------------------------------------------------</h2>

    <div class="container">
        <% 
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rsBacklogs = null;

            try {
                // Establish database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");

                // Retrieve username from session
                String username = (String) session.getAttribute("username");

                // Fetch backlogs details
                String backlogsQuery = "SELECT * FROM backlogs WHERE username = ?";
                pstmt = conn.prepareStatement(backlogsQuery);
                pstmt.setString(1, username);
                rsBacklogs = pstmt.executeQuery();
                
                if (!rsBacklogs.isBeforeFirst()) {
                    // No backlogs found for the user
                    %>
                    <div class="no-backlogs">Student has no backlogs.</div>
                    <%
                } else {
                    // Display backlogs details
                    %>
                    <div class="table-container">
                        <table>
                            <tr>
                                <th>Subject</th>
                                <th>Semester</th>
                                <th>Year</th>
                                <th>Reason</th>
                                <th>Status</th>
                            </tr>
                            <%
                            while (rsBacklogs.next()) {
                                %>
                                <tr>
                                    <td><%= rsBacklogs.getString("subject") %></td>
                                    <td><%= rsBacklogs.getString("semester") %></td>
                                    <td><%= rsBacklogs.getInt("year") %></td>
                                    <td><%= rsBacklogs.getString("reason") %></td>
                                    <td><%= rsBacklogs.getString("status") %></td>
                                </tr>
                                <%
                            }
                            %>
                        </table>
                    </div>
                    <%
                }
            } catch (ClassNotFoundException | SQLException e) {
                out.println("Error retrieving data: " + e.getMessage());
            } finally {
                // Close resources
                try {
                    if (rsBacklogs != null) rsBacklogs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    out.println("Error closing resources: " + e.getMessage());
                }
            }
        %>
    </div>
</body>
</html>
