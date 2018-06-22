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
        <c:if test="${submission==true}"><br><p>Already submitted this exam, additional submissions are not accepted<p></c:if>
        <div class="container">
            <form action="submit_quiz?quiz_id=${quiz_id}" method="post">
	            <c:forEach var="question" items="${questions}">
	            <div class="card" style="margin-bottom: 15px;">
	                <div class="card-header">
	                   ${question.question.question}
	                </div>
	                <div class="card-body">
	                <c:forEach var="choice" items="${choices}">
	                    <c:if test="${question.question.question == choice.question.question}">
	                    <c:choose>
						    <c:when test="${submission==false}">
						        <input type="radio" required name="${question.question.question}" value="${choice.id}" style="margin-right: 5px;">${choice.choice}<br>
						    </c:when>    
						    <c:otherwise>
						        <input disabled type="radio" required name="${question.question.question}" value="${choice.id}" style="margin-right: 5px;">${choice.choice}<br>
						    </c:otherwise>
						</c:choose>
	                        
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