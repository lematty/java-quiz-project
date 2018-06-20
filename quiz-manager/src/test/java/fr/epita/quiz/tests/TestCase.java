/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.tests;


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

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;
import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.AuthenticationService;
import fr.epita.quiz.services.StudentDAO;

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
public class TestCase {

	private static final Logger LOGGER = LogManager.getLogger(TestCase.class);

	@Inject
	SessionFactory sf;

	@Inject
	AuthenticationService auth;

	@Inject
	Student student;

	@Inject
	StudentDAO studentDAO;

	@Test
	public void testMethod() {
		// given
		Assert.assertNotNull(sf);

		final Question question = new Question();
		question.setQuestion("How to configure Hibernate?");
		question.setType(QuestionType.MCQ);

		final Session session = sf.openSession();

		// when
		final Transaction tx = session.beginTransaction();
		session.saveOrUpdate(question);
		tx.commit();
		// then
		// TODO
	}

	@Test
	public void testAuthentication() {

		Student student = new Student();
		student.setName("name");
		student.setPassword("pass");
		studentDAO.create(student);

		final boolean authenticated = auth.authenticate(student.getName(), student.getPassword());
		if (authenticated == true) {
			System.out.println("Login test passed!!!!!!");
		} else {
			System.out.println("!!!!!!!Login test failed");
		}
	}
}
