package fr.epita.quiz.web.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.ExamQuestion;
import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.services.ExamQuestionDAO;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/add-question")

public class AddQuestion extends SpringServlet{

	private static final long serialVersionUID = 1L;

	@Inject
	ExamDAO examDAO;
	
	@Inject
	ExamQuestionDAO examQuestionDAO;
	
	@Inject
	QuestionDAO questionDAO;
	
	@Inject
	MCQChoiceDAO mcqChoiceDAO;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Find Exam
		Map<String, String> params = QuestionService.getQueryMap(request.getQueryString());
		Exam currentExam = examDAO.searchExamById(params.get("quiz_id"));
		System.out.println(currentExam.getTitle());

		System.out.println("Add question to " + currentExam.getTitle() + " [" + currentExam.getId() + "]");

		Question question = new Question();
		question.setQuestion(request.getParameter("questionName"));
		question.setType(QuestionType.MCQ);
		questionDAO.create(question);

		MCQChoice answer1 = new MCQChoice();
		MCQChoice answer2 = new MCQChoice();
		MCQChoice answer3 = new MCQChoice();
		MCQChoice answer4 = new MCQChoice();
		List<MCQChoice> mcqChoiceList = Arrays.asList(answer1, answer2, answer3, answer4);
		Integer i = 1;

		String test = request.getParameter("div1");
		System.out.println("grabbed div variable!!!!!!");
		System.out.println(test);
		for (MCQChoice answer : mcqChoiceList) {
			System.out.println("Entered MCQChoice for loop");
			if (i == 1) {
				answer.setValid(true);
			} else {
				answer.setValid(false);
			}
			answer.setChoice(request.getParameter("answer" + i.toString()));
			System.out.println(answer.getChoice());
			answer.setQuestion(question);
			mcqChoiceDAO.create(answer);
			System.out.println("Answer created in database");
			i++;
		}

		Question addedQuestion = questionDAO.search(question).get(0);
		System.out.println(addedQuestion.getQuestion());

		ExamQuestion examQuestion = new ExamQuestion();
		examQuestion.setExam(currentExam);
		System.out.println("Add exam to " + examQuestion);

		examQuestion.setQuestion(addedQuestion);
		System.out.println("Add question to " + examQuestion);

		examQuestionDAO.create(examQuestion);
		System.out.println("Add Exam/Question to  " + examQuestion);

		List<ExamQuestion> examQuestionList = examQuestionDAO.search(examQuestion);
		System.out.println(examQuestionList);
		System.out.println("New list of questions");
		for (ExamQuestion examQuestionResult : examQuestionList) {
			System.out.println(examQuestionResult);
		}

		response.sendRedirect("quizzes");
	}
}