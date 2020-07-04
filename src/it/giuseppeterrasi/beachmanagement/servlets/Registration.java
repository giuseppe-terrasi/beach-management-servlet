package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.daos.UserDao;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		UserDao userDao = new UserDao();
		userDao.setFirstName(request.getParameter("firstName"));
		userDao.setLastName(request.getParameter("lastName"));
		userDao.setUsername(request.getParameter("username"));
		userDao.setPassword(request.getParameter("password"));
		userDao.setConfirmPassword(request.getParameter("confirmPassword"));
		
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		
		int created = userDao.create(dataSource);
		
		if(created > 0) request.setAttribute("successMessage", "Registration succeded");
		else if(created < 0) request.setAttribute("errorMessage", "User already exists");
		else request.setAttribute("errorMessage", "An errror occurred while creating user");
		
		if(created <= 0) request.setAttribute("userModel", userDao);
		
		request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
	}

}
