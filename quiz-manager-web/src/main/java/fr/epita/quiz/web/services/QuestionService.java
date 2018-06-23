package fr.epita.quiz.web.services;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.datamodel.Submission;
import fr.epita.quiz.services.ExamDAO;
import fr.epita.quiz.services.StudentDAO;
import fr.epita.quiz.services.ExamQuestionDAO;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.services.MCQSubmissionDAO;
import fr.epita.quiz.services.QuestionDAO;
import fr.epita.quiz.services.SubmissionDAO;
import fr.epita.quiz.web.actions.SpringServlet;

@WebServlet(urlPatterns = "/questions")
public class QuestionService extends SpringServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private QuestionDAO repository;
	
	@Inject
	private ExamDAO examDAO;
	
	@Inject
	private MCQChoiceDAO choiceDAO;
	
	@Inject
	private ExamQuestionDAO examquestionDAO;
	
	@Inject
	private SubmissionDAO submissionDAO;
	
	@Inject
	private MCQSubmissionDAO mcqSubmissionDAO;
	
	@Inject
	private StudentDAO studentDAO;
	
	public static Map<String, String> getQueryMap(String query)  
	{  
		System.out.println(query);
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}

	public List<ExamQuestion> findAllQuestions(String quizId) {
		Exam exam = examDAO.searchExamById(quizId);
		System.out.println(exam.getTitle());
		ExamQuestion eq = new ExamQuestion();
		eq.setExam(exam);
		List<ExamQuestion> examQuestions = examquestionDAO.search(eq);
		
		for (ExamQuestion q : examQuestions) {
			System.out.println(q.getQuestion().getQuestion());
		}
		
		return examQuestions;
	}
	
	public List<MCQChoice> findAllChoices(Question question) {
		MCQChoice choice = new MCQChoice();
		choice.setQuestion(question);
		return choiceDAO.search(choice);
	}
	
	private List<MCQChoice> combineLists(List<MCQChoice> a, List<MCQChoice> b) {
		List<MCQChoice> newList = new ArrayList<MCQChoice>();
		newList.addAll(a);
		newList.addAll(b);
		return newList;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> params = getQueryMap(request.getQueryString());
		List<ExamQuestion> questions = findAllQuestions(params.get("quiz_id"));
		List<MCQChoice> choices = new ArrayList<MCQChoice>();
		int total = 0;
		int correct = 0;
		
		String username = (String) request.getSession().getAttribute("userName");
		Student student = new Student();
		student.setName(username);
		Student currentUser = studentDAO.search(student).get(0);
		
		// Find the submission, if there is one
		Submission sub = new Submission();
		sub.setExam(examDAO.searchExamById(params.get("quiz_id")));
		sub.setStudent(currentUser);
		List<Submission> subList = submissionDAO.search(sub);
		
		request.getSession().setAttribute("submission", false);
		
		if (!subList.isEmpty()) {
			request.getSession().setAttribute("submission", true);
			
			// Get all MCQSubmissions
			MCQChoice mcqChoice = new MCQChoice();
			MCQSubmission choiceSubmission = new MCQSubmission();
			choiceSubmission.setStudent(currentUser);
			
			// Foreach question
			for (ExamQuestion eq : questions) {
				System.out.println("Searching submission for question: " + eq.getQuestion().getQuestion());
				mcqChoice.setQuestion(eq.getQuestion());
				choices = choiceDAO.search(mcqChoice);
				
				// Foreach choice
				for (MCQChoice choice : choices) {
					
					System.out.println("Found choice: " + choice.getChoice());
					System.out.println("\t\twith id = " + choice.getId());
					
					// Get the choice submission
					choiceSubmission.setChoice(choice);
					List<MCQSubmission> choiceSubmissions = mcqSubmissionDAO.search(choiceSubmission);
					
					// Make sure something has been submitted
					if (choiceSubmissions.isEmpty()) {
						System.out.println("Could not find choice submissions for this one");
						continue;
					}
					
					total++;
					System.out.println("Increasing total by 1");
					
					if (choiceSubmissions.get(0).getChoice().isValid()) {
						correct++;
						System.out.println("Increasing correct by 1");
					}
					
				}
			}
		}
		
		choices = new ArrayList<MCQChoice>();
		List<MCQChoice> newList;
		List<MCQChoice> temp;
		for (ExamQuestion eq : questions) {
			newList = new ArrayList<MCQChoice>();
			temp = new ArrayList<MCQChoice>();
			newList = findAllChoices(eq.getQuestion());
			temp = choices;
			choices = combineLists(temp, newList);
		}
		
		request.getSession().setAttribute("questions", questions);
		request.getSession().setAttribute("choices", choices);
		request.getSession().setAttribute("quiz_id", params.get("quiz_id"));
		request.getSession().setAttribute("total", total);
		request.getSession().setAttribute("correct", correct);
		response.sendRedirect("questions.jsp?" +  request.getQueryString());
	}
}