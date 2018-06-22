package fr.epita.quiz.web.services;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/delete-quiz")

public class DeleteQuiz extends SpringServlet {

	@Inject
	ExamDAO examDAO;

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exam exam = new Exam();
		exam.setTitle(request.getParameter("testTitle"));
		List<Exam> examList = examDAO.search(exam);
		for (Exam examResult : examList) {
			exam = examResult;
		}
		System.out.println(exam + " to be removed");
		examDAO.delete(exam);
		response.sendRedirect("quizzes");
	}
}
