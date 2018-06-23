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
import fr.epita.quiz.datamodel.MCQSubmission;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.datamodel.Submission;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.services.MCQSubmissionDAO;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.StudentDAO;
import fr.epita.quiz.services.SubmissionDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/submit_quiz")
public class SubmitQuiz extends SpringServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	SubmissionDAO submissionDAO;
	
	@Inject
	MCQSubmissionDAO mcqSubmissionDAO;
	
	@Inject
	MCQChoiceDAO mcqChoiceDAO;
	
	@Inject
	StudentDAO studentDAO;
	
	@Inject
	ExamDAO examDAO;
	
	@Inject
	QuestionDAO questionDAO;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Map<String, String[]> formInfo = request.getParameterMap();
		
		Submission sub = new Submission();
		MCQSubmission mcqSub;
		Question question = new Question();
		
		// Find the current user
		String username = (String) request.getSession().getAttribute("userName");
		Student student = new Student();
		student.setName(username);
		Student currentUser = studentDAO.search(student).get(0);
		System.out.println("---> " + currentUser.getName());
		
		// Find Exam
		Map<String, String> params = QuestionService.getQueryMap(request.getQueryString());
		Exam currentExam = examDAO.searchExamById(params.get("quiz_id"));
		
		for (String key : formInfo.keySet()) {
			System.out.println("KEY: " + key);
			System.out.println("VAL: " + ((String[])formInfo.get(key))[0]);
			
			if (key.equals("quiz_id")) {
				System.out.println("We're here, skipping the quiz id");
				continue;
			}
			
			// Find current question
			question.setQuestion(key);
			question.setType(QuestionType.MCQ);
			System.out.println("Current question: " + question.getQuestion());
			Question currentQuestion = questionDAO.search(question).get(0);
		
			// Create question submission
			sub.setExam(currentExam);
			sub.setQuestion(currentQuestion);
			sub.setStudent(currentUser);
			submissionDAO.create(sub);
			
			// Find the MCQchoice
			MCQChoice currentChoice = mcqChoiceDAO.searchById(((String[])formInfo.get(key))[0]);
			
			//choice student exam
			mcqSub = new MCQSubmission();	// attempt to stop if from updating, and force a create
			mcqSub.setChoice(currentChoice);
			mcqSub.setExam(currentExam);
			mcqSub.setStudent(currentUser);
			mcqSubmissionDAO.create(mcqSub);
			
		}
		
		response.sendRedirect("quizzes");
	}
}