/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.tests;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.services.MCQChoiceDAO;
import fr.epita.quiz.services.QuestionDAO;

/**
 * <h3>Description</h3>
 * <p>This class allows to ...</p>
 *
 * <h3>Usage</h3>
 * <p>This class should be used as follows:
 *   <pre><code>${type_name} instance = new ${type_name}();</code></pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 * ${tags}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TestMCQSearch {

	private static final Logger LOGGER = LogManager.getLogger(TestCase.class);
	
	@Inject
	QuestionDAO questDAO;

	@Inject
	MCQChoiceDAO mcqDAO;

	@Inject
	SessionFactory sf;

	@Test
	public void testSearch() {
		// given
		Assert.assertNotNull(sf);
		
		final Question question = new Question();
		question.setQuestion("What is the capital of France");
		question.setType(QuestionType.MCQ);
		questDAO.create(question);
		
		final Question question2 = new Question();
		question2.setQuestion("What is the capital of France");
		question2.setType(QuestionType.MCQ);
		questDAO.create(question2);

		final MCQChoice choice = new MCQChoice();
		choice.setValid(true);
		choice.setChoice("Paris");
		choice.setOrder(0);
		choice.setQuestion(question);
		mcqDAO.create(choice);
	
		final MCQChoice choice2 = new MCQChoice();
		choice2.setValid(false);
		choice2.setChoice("Bordeux");
		choice2.setOrder(1);
		choice2.setQuestion(question);
		mcqDAO.create(choice2);
		
		final Question q = new Question();
		q.setType(QuestionType.MCQ);
		
		List<Question> questions = questDAO.search(q);
		
		LOGGER.debug("CAMERON");
		for (Question thisQuestion : questions) {
			LOGGER.debug("CAMERON" + thisQuestion.toString());
		}
	}


}
