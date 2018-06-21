package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import fr.epita.quiz.datamodel.Student;

@Component
public class StudentDAO extends GenericORMDao<Student> {
	@Inject
	@Named("authCheck")
	String query;
	
	@Override
	protected String getQuery() {
		return query;
	}
	
	public StudentDAO() {
		
	}
}
