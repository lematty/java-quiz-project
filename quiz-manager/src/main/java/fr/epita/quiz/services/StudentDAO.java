package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

import fr.epita.quiz.datamodel.Student;

public class StudentDAO extends GenericORMDao<Student> {
	@Inject
	@Named("authCheck")
	String query;
	
	@Override
	protected String getQuery() {
		return query;
	}
}
