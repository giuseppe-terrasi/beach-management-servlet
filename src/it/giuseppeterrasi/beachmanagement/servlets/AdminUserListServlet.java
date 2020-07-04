package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.daos.UserDao;

/**
 * Servlet implementation class AdminUserListServlet
 */
@WebServlet("/admin/users")
public class AdminUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		UserDao userDao = new UserDao(dataSource);
		
		List<UserDao> users = userDao.getUsers();
		
		request.setAttribute("users", users);
		request.setAttribute("cssActivePage", "manageUsers");
		
		request.getRequestDispatcher("/WEB-INF/views/admin/manageUsers.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
