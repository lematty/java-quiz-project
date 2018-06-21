package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import fr.epita.quiz.datamodel.Student;

@Component
public class StudentDAO extends GenericORMDao<Student> {
	@Inject
	@Named("authCheck")
	String authenticationQuery;
	
	@Override
	protected String getQuery() {
		return authenticationQuery;
	}
	
	public StudentDAO() {
		
	}
}
