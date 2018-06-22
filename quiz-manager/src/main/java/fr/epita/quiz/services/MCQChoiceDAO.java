/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.MCQChoice;

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
public class MCQChoiceDAO extends GenericORMDao<MCQChoice> {

	@Inject
	@Named("mcqChoiceQuery")
	String query;
	
	@Override
	protected String getQuery() {
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public final MCQChoice searchById(String id) {
		final Session session = sf.openSession();
		final Transaction tx = session.beginTransaction();
		
		final Query<MCQChoice> searchQuery = session.createQuery("from MCQChoice where id=" + id);

		tx.commit();
		return searchQuery.list().get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.epita.quiz.services.GenericORMDao#beforeCreate(java.lang.Object)
	 */
	@Override
	public boolean beforeCreate(MCQChoice entity) {
		return entity.getChoice() != null && entity.getQuestion() != null;

	}

}
