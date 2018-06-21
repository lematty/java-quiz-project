/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

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
}
