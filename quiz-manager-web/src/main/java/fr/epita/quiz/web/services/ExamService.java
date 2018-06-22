package fr.epita.quiz.web.services;


import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/quizzes")
public class ExamService extends SpringServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ExamDAO repository;

	public List<Exam> findAllExams() {
		List<Exam> exams = repository.search(new Exam());
		
		if (exams.isEmpty()) {
			createDummyQuestion();
			exams = repository.search(new Exam());
		}
		return exams;
	}

	public void createDummyQuestion() {
		final Exam e = new Exam();
		e.setTitle("This is my goddamn exam");
		repository.create(e);
		final Exam e2 = new Exam();
		e2.setTitle("Are you tired of this yet?");
		repository.create(e2);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Exam> exams = findAllExams();
		request.getSession().setAttribute("exams", exams);
		response.sendRedirect("welcome.jsp");
	}
}