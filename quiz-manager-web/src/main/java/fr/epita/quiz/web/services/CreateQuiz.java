package fr.epita.quiz.web.services;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/create-quiz")

public class CreateQuiz extends SpringServlet {

	@Inject
	ExamDAO examDAO;
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("testName"));
		Exam exam = new Exam();
		exam.setTitle(request.getParameter("testName"));
		examDAO.create(exam);
		System.out.println("\n---------------- Exam List Result -----------------");
		List<Exam> examList = examDAO.search(new Exam());
		for (Exam examResult : examList) {
			System.out.println(examResult);
		}
		System.out.println("---------------------------------------------------\n");
		response.sendRedirect("quizzes");
	}
}

