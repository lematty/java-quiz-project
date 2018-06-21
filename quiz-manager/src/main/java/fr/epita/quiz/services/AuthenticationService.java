/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import fr.epita.quiz.datamodel.Student;

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


@Repository
public class AuthenticationService {

	@Inject
	StudentDAO authCheck;

	public boolean authenticate(String username, String password) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Student.class);
		Student student = (Student)context.getBean("student");

		List<Student> students = authCheck.search(student);
		String studentNameCheck = null;
		String studentPassCheck = null;
		for (Student s : students) {
			studentNameCheck = s.getName();
			studentPassCheck = s.getPassword();
		}
		System.out.println(studentNameCheck);
		System.out.println(studentPassCheck);

		return (studentNameCheck.equals(username) && studentPassCheck.equals(password));
	}
}
