/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import fr.epita.quiz.datamodel.Exam;

@Component
public class ExamDAO extends GenericORMDao<Exam> {

	@Inject
	@Named("examQuery")
	String query;

	@Override
	protected String getQuery() {
		return query;
	}
	
	@SuppressWarnings("unchecked")
	public final Exam searchExamById(String id) {
		final Session session = sf.openSession();
		final Transaction tx = session.beginTransaction();
		
		final Query<Exam> searchQuery = session.createQuery("from Exam where id=" + id);

		tx.commit();
		return searchQuery.list().get(0);
	}
}
