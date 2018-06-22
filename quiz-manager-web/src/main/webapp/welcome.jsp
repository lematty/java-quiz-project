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
            <c:forEach var="exam" items="${exams}">
                <a href="quiz?id=${exam.id}">${exam.title}</a><br>
            </c:forEach>
		</div>

		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
			<a href="create-quiz.jsp">
				<button class="btn btn-lg btn-outline-success" type="button">Create Quiz!</button>
			</a>
		</div>
			<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	</body>
</html>