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
import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.services.ExamQuestionDAO;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/quizzes")
public class ExamService extends SpringServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private QuestionDAO qDAO;	// TODO temporary
	
	@Inject
	private ExamQuestionDAO eqDAO;	//TODO temporary
	
	@Inject
	private MCQChoiceDAO choiceDAO;	//TODO temporary
	
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
		
		final Question q1 = new Question();
		q1.setQuestion("question 1");
		q1.setType(QuestionType.MCQ);
		qDAO.create(q1);
		
		//// --->
		final MCQChoice choice = new MCQChoice();
		choice.setChoice("Choice A for q1");
		choice.setQuestion(q1);
		choiceDAO.create(choice);
		
		final MCQChoice choice2 = new MCQChoice();
		choice2.setChoice("Choice B for q1");
		choice2.setQuestion(q1);
		choiceDAO.create(choice2);
		
		final Question q2 = new Question();
		q2.setQuestion("question 2");
		q2.setType(QuestionType.MCQ);
		qDAO.create(q2);
		
		//// --->
		final MCQChoice choice3 = new MCQChoice();
		choice3.setChoice("Choice A for q2");
		choice3.setQuestion(q2);
		choiceDAO.create(choice3);
		
		final MCQChoice choice4 = new MCQChoice();
		choice4.setChoice("Choice B for q2");
		choice4.setQuestion(q2);
		choiceDAO.create(choice4);
			
		final ExamQuestion eq = new ExamQuestion();
		eq.setExam(e);
		eq.setQuestion(q1);
		eqDAO.create(eq);
		
		final ExamQuestion eq1 = new ExamQuestion();
		eq1.setExam(e);
		eq1.setQuestion(q2);
		eqDAO.create(eq1);
		
		final ExamQuestion eq2 = new ExamQuestion();
		eq2.setExam(e2);
		eq2.setQuestion(q2);
		eqDAO.create(eq2);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Exam> exams = findAllExams();
		request.getSession().setAttribute("exams", exams);
		response.sendRedirect("welcome.jsp");
	}
}