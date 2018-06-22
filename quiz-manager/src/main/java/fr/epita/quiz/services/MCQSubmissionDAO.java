package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import fr.epita.quiz.datamodel.Exam;
import fr.epita.quiz.datamodel.MCQSubmission;

@Component
public class MCQSubmissionDAO extends GenericORMDao<MCQSubmission> {

	@Inject
	@Named("mcqSubmissionQuery")
	String query;

	@Override
	protected String getQuery() {
		return query;
	}
}