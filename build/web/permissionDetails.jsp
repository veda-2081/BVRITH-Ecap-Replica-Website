<!DOCTYPE html>
<html>

<head>
    <title>Permission Details</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        /* Base styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #fff; /* White background */
            margin: 0;
            padding: 20px;
        }

        h3 {
            color: rgb(7, 48, 100);
            margin-bottom: 20px; /* Added spacing below heading */
        }

        /* Request pending styling */
        .request-pending {
            color: red;
            font-weight: bold;
            display: none; /* Initially hide the message */
            margin-top: 20px; /* Added margin for separation */
        }
    </style>
</head>

<body>
    <h3>REQUEST FACULTY PERMISSION</h3>
    <h2>--------------------------------------------------------------------------------------------------------------------------------------------------------</h2>

    <p><strong>Faculty Counsellor:</strong> <%= request.getParameter("facultyCounsellor") %></p>
    <p><strong>Reason:</strong> <%= request.getParameter("reason") %></p>
    <p><strong>Departure Time:</strong> <%= request.getParameter("departureTime") %></p>

    <div class="request-pending">Request Pending</div>

    <script>
        // Display "Request Pending" message
        document.querySelector('.request-pending').style.display = 'block';

        // Hide after 30000 milliseconds (30 seconds)
        setTimeout(function() {
            document.querySelector('.request-pending').style.display = 'none';
        }, 30000); // 30000 milliseconds (30 seconds)
    </script>
</body>

</html>
