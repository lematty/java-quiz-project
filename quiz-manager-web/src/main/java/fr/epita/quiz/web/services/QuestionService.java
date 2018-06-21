package fr.epita.quiz.web.services;


import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/questions")
public class QuestionService extends SpringServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private QuestionDAO repository;

	public List<Question> findAllQuestions() {
		List<Question> questions = repository.search(new Question());
		
		if (questions.isEmpty()) {
			createDummyQuestion();
			questions = repository.search(new Question());
		}
		
		return questions;
	}
	
	public void createDummyQuestion() {
		final Question q = new Question();
		q.setQuestion("What the fuck is going on?");
		q.setType(QuestionType.MCQ);
		repository.create(q);
		final Question q2 = new Question();
		q2.setQuestion("Are you tired of this yet?");
		repository.create(q2);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Question> questions = findAllQuestions();
		request.getSession().setAttribute("questions", questions);
		response.sendRedirect("welcome.jsp");
	}
}