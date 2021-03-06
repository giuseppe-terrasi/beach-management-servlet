package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.daos.UserDao;
import it.giuseppeterrasi.beachmanagement.models.AppUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource datasource = (DataSource) getServletContext().getAttribute("datasource");
		UserDao userDao = new UserDao(datasource);
		userDao.setUsername(request.getParameter("username"));
		userDao.setPassword(request.getParameter("password"));
		
		String requestUrl = (String)request.getAttribute("requestUrl");
		
		AppUser appUser = userDao.login();
		
		if(appUser != null) {
			request.getSession().setAttribute("appUser", appUser);
			requestUrl = requestUrl != null ? requestUrl : request.getContextPath() + "/";
			response.sendRedirect(requestUrl);
			
		} else {
			request.setAttribute("error", true);
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
		
	}

}
