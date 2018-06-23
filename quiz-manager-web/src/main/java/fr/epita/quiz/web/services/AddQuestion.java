package fr.epita.quiz.web.services;

import java.io.IOException;
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

		answer1.setChoice(request.getParameter("answer1"));
		answer2.setChoice(request.getParameter("answer2"));
		answer3.setChoice(request.getParameter("answer3"));
		answer4.setChoice(request.getParameter("answer4"));

		answer1.setValid(true);
		answer2.setValid(false);
		answer3.setValid(false);
		answer4.setValid(false);

		answer1.setQuestion(question);
		answer2.setQuestion(question);
		answer3.setQuestion(question);
		answer4.setQuestion(question);

		mcqChoiceDAO.create(answer1);
		mcqChoiceDAO.create(answer2);
		mcqChoiceDAO.create(answer3);
		mcqChoiceDAO.create(answer4);
		
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