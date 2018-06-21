package fr.epita.quiz.web.services;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.http.MediaType;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.web.actions.SpringServlet;
import fr.epita.quiz.web.services.messages.QuestionMessage;



@WebServlet(urlPatterns = "/questions")
public class QuestionService extends SpringServlet {

	@Inject
	private QuestionDAO repository;

	public List<QuestionMessage> findAllQuestions() {
		List<Question> questions = repository.search(new Question());
		
		if (questions.isEmpty()) {
			createDummyQuestion();
			questions = repository.search(new Question());
		}
		
		final List<QuestionMessage> messages = new ArrayList<>();
		for (final Question question : questions) {
			System.out.println("Writing messages with the question: " + question.getQuestion());
			final QuestionMessage message = new QuestionMessage();
			message.setTitle(question.getQuestion());
			messages.add(message);
		}
		return messages;
	}
	
	public void createDummyQuestion() {
		System.out.println("Writing a new question to the database...");
		final Question q = new Question();
		q.setQuestion("What the fuck is going on?");
		q.setType(QuestionType.MCQ);
		repository.create(q);
		
		for (Question question : repository.search(new Question())) {
			System.out.println("CAMERON: " + question.getQuestion());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<QuestionMessage> messages = findAllQuestions();
		System.out.println(messages.toString());
		response.sendRedirect("welcome.jsp");
	}
}