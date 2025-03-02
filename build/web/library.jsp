<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Book Details</title>
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

        h3, h2 {
            font-weight: normal;
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

        .no-books {
            font-size: 18px;
            color: #3498db; /* Light blue color */
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
    <h3>Library Book Details</h3>
    <h2>---------------------------------------------------------------------------------------------------------------------------------------------------------</h2>

    <div class="container">
        <% 
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rsBooks = null;

            try {
                // Establish database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/225d5", "root", "root123");

                // Retrieve username from session
                String username = (String) session.getAttribute("username");

                // Fetch book details
                String booksQuery = "SELECT issue_date, return_date, fine, book_details FROM library_books WHERE username = ?";
                pstmt = conn.prepareStatement(booksQuery);
                pstmt.setString(1, username);
                rsBooks = pstmt.executeQuery();
                
                if (!rsBooks.isBeforeFirst()) {
                    // No books found for the user
                    %>
                    <div class="no-books">No books issued to this user.</div>
                    <%
                } else {
                    // Display book details
                    %>
                    <div class="table-container">
                        <table>
                            <tr>
                                <th>Issue Date</th>
                                <th>Return Date</th>
                                <th>Fine</th>
                                <th>Book Details</th>
                            </tr>
                            <%
                            while (rsBooks.next()) {
                                %>
                                <tr>
                                    <td><%= rsBooks.getString("issue_date") %></td>
                                    <td><%= rsBooks.getString("return_date") %></td>
                                    <td><%= rsBooks.getString("fine") %></td>
                                    <td><%= rsBooks.getString("book_details") %></td>
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
                    if (rsBooks != null) rsBooks.close();
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
