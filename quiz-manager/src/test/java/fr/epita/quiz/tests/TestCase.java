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

	@Test
	public void testAddDeleteStudent() {
		Student student = new Student();
		student.setName("student2");
		student.setPassword("pass2");

		Student student2 = new Student();
		student2.setName("student3");
		student2.setPassword("pass3");

		studentDAO.create(student);
		studentDAO.create(student2);
		System.out.println(student + " was added\n");
		System.out.println(student2 + " was added\n");

		List<Student> studentList = studentDAO.search(new Student());
		for (Student s : studentList) {
			System.out.println(s.getName());
			System.out.println(s + " was selected\n");
		}

		studentDAO.delete(student);
		System.out.println("Deleted student " + student);
		List<Student> studentDeleteCheck = studentDAO.search(student);
		for (Student s : studentDeleteCheck) {
			System.out.println(s.getName());
			System.out.println(s + " was deleted\n");
		}
	}

	@Test
	public void testUpdate() {
		Student studentToBeUpdated = new Student();
		studentToBeUpdated.setName("name");
		List<Student> getStudent = studentDAO.search(studentToBeUpdated);
		System.out.println("\n--------------- Before Update  ----------------");
		for (Student s : getStudent) {
			studentToBeUpdated = s;
			System.out.println(s + " <--- this object was selected for update");
		}
		System.out.println("-----------------------------------------------\n");

		studentToBeUpdated.setName("nameHasChangedSuccessfully!!");
		studentDAO.update(studentToBeUpdated);
		List<Student> updatedStudentList = studentDAO.search(studentToBeUpdated);

		System.out.println("\n---------------- After Update -----------------");
		for (Student updateCheck : updatedStudentList) {
			System.out.println(updateCheck + " <--- this object was updated");
		}
		System.out.println("-----------------------------------------------\n");

		List<Student> getAllStudents = studentDAO.search(new Student());
		System.out.println("\n----------------- All results ------------------");
		for (Student allStudentList : getAllStudents) {
			System.out.println(allStudentList);
		}
		System.out.println("------------------------------------------------\n");
	}
}
