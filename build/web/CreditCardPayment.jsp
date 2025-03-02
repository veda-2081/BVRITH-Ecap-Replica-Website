<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*" %>
<%
    String cardNumber = request.getParameter("cardNumber");
    String expiryDate = request.getParameter("expiryDate");
    String cvv = request.getParameter("cvv");
    String amount = request.getParameter("amount").trim();

    // Process the credit card payment (dummy processing)
    // In real scenarios, integrate with credit card payment gateway APIs

    request.setAttribute("paymentMethod", "Credit Card");
    request.setAttribute("amountPaid", amount);
    RequestDispatcher rd = request.getRequestDispatcher("PaymentSuccess.jsp");
    rd.forward(request, response);
%>
