package fr.epita.quiz.web.services;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/submit_quiz")
public class SubmitQuiz extends SpringServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Map<String, String[]> formInfo = request.getParameterMap();
		
		for (String key : formInfo.keySet()) {
			System.out.println(formInfo.get(key)[0]);
		}
		
		response.sendRedirect("quizzes");
	}
}
