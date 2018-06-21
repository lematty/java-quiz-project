package fr.epita.quiz.web.actions;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.quiz.datamodel.Student;
import fr.epita.quiz.services.AuthenticationService;
import fr.epita.quiz.services.StudentDAO;

/**
 * Servlet implementation class Login
 */

@WebServlet(urlPatterns = "/login")
public class Login extends SpringServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	AuthenticationService auth;

	/**
	 * Default constructor.
	 */
	public Login() {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		System.out.println("username : " + username);
		System.out.println("password : " + password);
		final boolean authenticated = auth.authenticate(username, password);
		request.getSession().setAttribute("authenticated", authenticated);
		request.getSession().setAttribute("userName", username);

		if (authenticated) {
			response.sendRedirect("quizzes");
		} else {
			response.sendRedirect("index.html");
			System.out.println("Invalid username or password");
		}
	}
}
