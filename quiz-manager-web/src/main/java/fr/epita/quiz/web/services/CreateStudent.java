package fr.epita.quiz.web.services;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.StudentDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/createStudent")

public class CreateStudent extends SpringServlet {
	@Inject
	StudentDAO studentDAO;

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Student addStudent = new Student();
		addStudent.setName(request.getParameter("username"));
		addStudent.setPassword(request.getParameter("password"));
		System.out.println("username : " + addStudent.getName());
		System.out.println("password : " + addStudent.getPassword());
		studentDAO.create(addStudent);
		request.getSession().setAttribute("authenticated", true);
		request.getSession().setAttribute("userName", addStudent.getName());
		response.sendRedirect("quizzes");
	}
}
