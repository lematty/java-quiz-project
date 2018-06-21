package fr.epita.quiz.tests;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.services.QuestionDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TestMCQChoice {
	@Inject
	QuestionDAO questDAO;

	@Inject
	MCQChoiceDAO mcqDAO;

	@Inject
	SessionFactory factory;

	@Test
	public void testMCQChoiceSearch() {
		//////////////////////////////////////////////////////////
		final Question question1 = new Question();
		question1.setQuestion("What is the capital of France");
		question1.setType(QuestionType.MCQ);
		questDAO.create(question1);

		final MCQChoice choice1 = new MCQChoice();
		choice1.setValid(false);
		choice1.setChoice("Marseille");
		choice1.setOrder(0);
		choice1.setQuestion(question1);
		mcqDAO.create(choice1);
		
		final MCQChoice choice2 = new MCQChoice();
		choice2.setValid(true);
		choice2.setChoice("Paris");
		choice2.setOrder(1);
		choice2.setQuestion(question1);
		mcqDAO.create(choice2);
		
		////////////////////////////////////////////////////////
		final Question question2 = new Question();
		question2.setQuestion("What is the capital of USA");
		question2.setType(QuestionType.MCQ);
		questDAO.create(question2);
		
		final MCQChoice choice3 = new MCQChoice();
		choice3.setValid(true);
		choice3.setChoice("DC");
		choice3.setOrder(0);
		choice3.setQuestion(question2);
		mcqDAO.create(choice3);
		
		final MCQChoice choice4 = new MCQChoice();
		choice4.setValid(false);
		choice4.setChoice("Philly");
		choice4.setOrder(1);
		choice4.setQuestion(question2);
		mcqDAO.create(choice4);
		
		////////////////////////////////////////////
		
		final MCQChoice tester = new MCQChoice();
		tester.setQuestion(question1);
		List<MCQChoice> something = mcqDAO.search(tester);

		for (MCQChoice c : something) {
			System.out.println("CAMERON: " + c.getChoice());
		}
		
		System.out.println("testSave ran successfully...");
	}
}
