/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

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

	public boolean authenticate(String username, String password) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Student.class);
		Student s1 = (Student)context.getBean("student");
		s1.setName("name");
		s1.setPassword("pass");
		
		return (s1.getName().equals(username) && s1.getPassword().equals(password));
	}

}
