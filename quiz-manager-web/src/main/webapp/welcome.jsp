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
		<div class="container">
			<div class="row">
				<h1 class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >Welcome, <%=session.getAttribute("userName") %></h1><br>
			</div><br>
			<div class="row">
				<c:forEach var="exam" items="${exams}">
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
		                <a href="questions?quiz_id=${exam.id}">${exam.title}</a>
					</div>
					<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
							<input name="testTitle" value="${exam.title}" class="form-control" type="text" hidden="true"/>
							<a href="add-question.jsp?quiz_id=${exam.id}">
								<button class="btn btn-sm btn-outline-success" type="button">Add</button>							
							</a>
					</div>
					<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
						<form action="edit-question" method="post">
							<input name="testTitle" value="${exam.title}" class="form-control" type="text" hidden="true"/>
							<button class="btn btn-sm btn-outline-warning" type="button">Edit</button>
						</form>
					</div>
					<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
						<form action="delete-quiz" method="post">
							<input name="testTitle" value="${exam.title}" class="form-control" type="text" hidden="true"/>
							<button class="btn btn-sm btn-outline-danger" type="submit">Delete</button>
						</form>
					</div>
					<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5"></div>
	            </c:forEach>
			</div><br><br>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
				<a href="create-quiz.jsp">
					<button class="btn btn-lg btn-outline-success" type="button">Create Quiz</button>
				</a>
			</div>
		</div>
		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	</body>
</html>