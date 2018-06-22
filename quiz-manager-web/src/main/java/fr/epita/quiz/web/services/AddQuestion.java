package fr.epita.quiz.web.services;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.ExamQuestion;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.services.ExamQuestionDAO;
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
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Exam exam = new Exam();
		exam.setTitle(request.getParameter("testTitle"));
		List<Exam> examList = examDAO.search(exam);
		System.out.println(examList);
		for (Exam examResult : examList) {
			exam = examResult;
		}
		System.out.println("Add question to " + exam);
		
		Question question = new Question();
		question.setQuestion(request.getParameter("questionName"));
		question.setType(QuestionType.MCQ);
		questionDAO.create(question);
		
		List<Question> questionList = questionDAO.search(question);
		for (Question questionResult : questionList) {
			question = questionResult;
		}
		
		ExamQuestion examQuestion = new ExamQuestion();
		examQuestion.setExam(exam);
		System.out.println("Add exam to " + examQuestion);

		examQuestion.setQuestion(question);
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
