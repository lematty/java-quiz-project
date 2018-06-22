<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
boolean auth = (Boolean) session.getAttribute("authenticated");
if (! auth){
    response.sendRedirect("index.html");
}
%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Welcome</title>
        <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    </head>
    
    <body>
        <h1>Welcome, <%=session.getAttribute("userName") %></h1>
        <div class="container">
            <form action="submit_quiz" method="post">
	            <c:forEach var="question" items="${questions}">
	            <div class="card" style="margin-bottom: 15px;">
	                <div class="card-header">${question.question.question}</div>
	                <div class="card-body">
	                <c:forEach var="choice" items="${choices}">
	                    <c:if test="${question.question.question == choice.question.question}">
	                        <input type="radio" required name="${question.question.question}" value="${choice.id}" style="margin-right: 5px;">${choice.choice}<br>
	                    </c:if>
	                </c:forEach>
	                </div>
	            </div>
	            </c:forEach>
	            <input type="submit">
            </form>
        </div>
    </body>
</html>