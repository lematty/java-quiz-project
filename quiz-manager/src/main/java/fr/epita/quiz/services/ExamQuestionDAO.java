package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.stereotype.Component;
import fr.epita.quiz.datamodel.ExamQuestion;

@Component
public class ExamQuestionDAO extends GenericORMDao<ExamQuestion> {

	@Inject
	@Named("examQuestionQuery")
	String query;

	@Override
	protected String getQuery() {
		return query;
	}
}