<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Profile</title>
    <style>
       body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #ffffff; 
            overflow-y: hidden; /* Prevent vertical scrolling */
        }

        .rect {
            padding: 10px;
            background: #ffffff;
            color: #000000;
            margin-bottom: 10px;
            overflow-x: hidden; /* Prevent horizontal scrolling */
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
            width: calc(100% - 20px); /* Adjusted width to account for padding */
            border-collapse: collapse;
            margin-top: 10px;
            margin-bottom: 10px;
            background-color: #ffffff; /* Set table background color to white */
            table-layout: fixed; /* Fixed table layout */
        }

        .rect th, .rect td {
        border: 1px solid #ccc;
        padding: 8px;
        text-align: left;
        white-space: nowrap;
        font-size: 10px; /* Adjust font size here */
    }

        .rect th {
            background-color: #f2f2f2;
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
</head>
<body>
    <h3 style="color: rgb(7, 48, 100);">ATTENDENCE</h3>
    <h2>---------------------------------------------------------------------------------------------------------------------------------------------------------</h2>
    
    <% 
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rsAttendance = null;
        
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");
            
            // Retrieve username from session
            String username = (String) session.getAttribute("username");

            // Fetch attendance details
            String attendanceQuery = "SELECT * FROM attendance WHERE username = ?";
            pstmt = conn.prepareStatement(attendanceQuery);
            pstmt.setString(1, username);
            rsAttendance = pstmt.executeQuery();
            
            // Display attendance details
            %>
            <div class="rect">
                
                <table>
                    <tr>
                        <th>Sl.No.</th>
                        <th>Subject</th>
                        <th>Held</th>
                        <th>Attend</th>
                        <th>%</th>
                    </tr>
                    <%
                    int count = 1;
                    while (rsAttendance.next()) {
                        %>
                        <tr>
                            <td><%= count++ %></td>
                            <td><%= rsAttendance.getString("subject") %></td>
                            <td><%= rsAttendance.getInt("held") %></td>
                            <td><%= rsAttendance.getInt("attend") %></td>
                            <td><%= rsAttendance.getFloat("percentage") %></td>
                        </tr>
                        <%
                    }
                    %>
                </table>
            </div>
            <%
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error retrieving data: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rsAttendance != null) rsAttendance.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                out.println("Error closing resources: " + e.getMessage());
            }
        }
    %>


</body>
</html>
