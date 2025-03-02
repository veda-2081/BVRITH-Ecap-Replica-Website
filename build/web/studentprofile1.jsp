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
            background-color: white; /* Set toggle content background color to white */
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

            // If the table is currently hidden, show it; otherwise, hide it
            table.style.display = (currentDisplay === 'none') ? 'table' : 'none';
        }

        function displayForm() {
            alert("Functionality to print or export data goes here.");
        }
    </script>
</head>
<body>
    <h3 style="color: rgb(7, 48, 100);">STUDENT PROFILE</h3>
    <h2>---------------------------------------------------------------------------------------------------------------------------------------------------------</h2>
    
    <% 
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");
            
            // Retrieve username from session
            String username = (String) session.getAttribute("username");

            // Prepare SQL query to fetch user details from admission table
            String query = "SELECT * FROM admission WHERE username = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username); // Set the username parameter
            rs = pstmt.executeQuery();
            
            // Display user details
            if (rs.next()) {
                %>
                <div class="rect">
                    <h3><span class="toggle-link" onclick="toggleTable('personalDetails')">BIO-DATA</span></h3>
                    <table id="personalDetails" class="toggle-content">
                        
                        <tr>
                            <th>Name</th>
                            <td><%= rs.getString("studentName") %></td>
                        </tr>
                        <tr>
                            <th>Intermediate Register No</th>
                            <td><%= rs.getString("intermediateRegNo") %></td>
                        </tr>
                        <tr>
                            <th>Rank</th>
                            <td><%= rs.getString("eamcetrank") %></td>
                        </tr>
                        <tr>
                            <th>Entrance Type</th>
                            <td><%= rs.getString("entranceType") %></td>
                        </tr>
                        <tr>
                            <th>Seat Type</th>
                            <td><%= rs.getString("seatType") %></td>
                        </tr>
                        <tr>
                            <th>Course</th>
                            <td><%= rs.getString("course") %></td>
                        </tr>
                        <tr>
                            <th>Branch</th>
                            <td><%= rs.getString("branch") %></td>
                        </tr>
                        <tr>
                            <th>Mobile No</th>
                            <td><%= rs.getString("mobileNo") %></td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <td><%= rs.getString("email") %></td>
                        </tr>
                        <tr>
                            <th>Parent Name</th>
                            <td><%= rs.getString("parentName") %></td>
                        </tr>
                        <tr>
                            <th>Parent Mobile No</th>
                            <td><%= rs.getString("parentMobileNo") %></td>
                        </tr>
                        <tr>
                            <th>Address</th>
                            <td><%= rs.getString("doorNo") %> <%= rs.getString("street") %>, <%= rs.getString("village") %>, 
                                <%= rs.getString("mandal") %>, <%= rs.getString("district") %>, <%= rs.getString("state") %> - <%= rs.getString("pin") %></td>
                        </tr>
                        <tr>
                            <th>Village (if not in list)</th>
                            <td><%= rs.getString("villageNotInList") %></td>
                        </tr>
                    </table>
                </div>
                <%
            } else {
                out.println("<p>No records found.</p>");
            }

            // Fetch attendance details
            String attendanceQuery = "SELECT * FROM attendance WHERE username = ?";
            pstmt = conn.prepareStatement(attendanceQuery);
            pstmt.setString(1, username);
            ResultSet rsAttendance = pstmt.executeQuery();
            %>
            <div class="rect">
                <h3><span class="toggle-link" onclick="toggleTable('attendance')">PERFORMANCE (Present)</span></h3>
                <table id="attendance" class="toggle-content">
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
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                out.println("Error closing resources: " + e.getMessage());
            }
        }
    %>

    <!-- Other sections with similar structure -->
    
    <div class="rect">
        <h3><span class="toggle-link" onclick="toggleTable('performancePast')">PERFORMANCE (Past)</span></h3>
        <table id="performancePast" class="toggle-content">
            <tr>
            <td><img src="m1.png" alt="Performance Chart"></td>
        </tr><!-- Table rows for past performance -->
        </table>
    </div>

    <div class="rect">
        <h3><span class="toggle-link" onclick="toggleTable('feedetails')">Fee Details</span></h3>
        <table id="feedetails" class="toggle-content">
            <tr>
            <td>No feed dues!</td>
        </tr><!-- Table rows for fee details -->
        </table>
    </div>

  

    <div class="rect">
        <h3><span class="toggle-link" onclick="toggleTable('outings')">Outings</span></h3>
        <table id="outings" class="toggle-content">
            <tr>
            <td>No Outings! </td>
        </tr><!-- Table rows for outings -->
        </table>
    </div>

    <div class="rect">
        <h3><span class="toggle-link" onclick="toggleTable('counsellingdetails')">Counselling Details</span></h3>
        <table id="counsellingdetails" class="toggle-content">
            <tr>
            <td>Student is fine with all the subjects and asked her to make a timetable</td>
        </tr><!-- Table rows for counselling details -->
        </table>
    </div>

    <div class="rect">
        <h3><span class="toggle-link" onclick="toggleTable('disciplinaryaction')">Disciplinary Action</span></h3>
        <table id="disciplinaryaction" class="toggle-content">
            <tr>
                <td>
            No complaints!
                </td>
            </tr>
            <!-- Table rows for disciplinary action -->
        </table>
    </div>
    
    <button type="button" class="button" onclick="displayForm()">Print</button>
    <button type="button" class="button1" onclick="displayForm()">Export</button>

</body>
</html>