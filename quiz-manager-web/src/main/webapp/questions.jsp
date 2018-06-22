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
    </head>
    
    <body>
        <h1>Welcome, <%=session.getAttribute("userName") %></h1>
        <div class="container">
            <c:forEach var="question" items="${questions}">
                <p>${question.question.question}</p>
                <c:forEach var="choice" items="${choices}">
                    <c:if test="${question.question.question == choice.question.question}">
                        <p>${choice.choice}</p>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </div>
    </body>
</html>