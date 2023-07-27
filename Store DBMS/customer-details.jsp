<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Details</title>
</head>
<body>
    <h1>Customer Details</h1>
    <%-- Access the customer attribute set in the servlet --%>
    <c:if test="${not empty customer}">
        <p>ID: ${customer.id}</p>
        <p>Name: ${customer.name}</p>
        <p>Email: ${customer.email}</p>
        <p>Phone: ${customer.phone}</p>
    </c:if>
    <c:if test="${empty customer}">
        <p>Customer not found.</p>
    </c:if>
</body>
</html>