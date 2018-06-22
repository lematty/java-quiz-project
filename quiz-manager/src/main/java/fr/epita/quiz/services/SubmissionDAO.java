package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import fr.epita.quiz.datamodel.Submission;

@Component
public class SubmissionDAO extends GenericORMDao<Submission> {

	@Inject
	@Named("submissionQuery")
	String query;

	@Override
	protected String getQuery() {
		return query;
	}
}