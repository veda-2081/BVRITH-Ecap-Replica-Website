<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Successful</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 20px;
            text-align: center;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        h3 {
            color: #073064;
            margin-bottom: 20px;
        }
        p {
            margin: 10px 0;
        }
    </style>
</head>
<body>

<div class="container">
    <h3>Payment Successful!</h3>
    <p>Payment Method: <%= request.getAttribute("paymentMethod") %></p>
    <p>Amount Paid: <%= request.getAttribute("amountPaid") %></p>
</div>

</body>
</html>
