package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		DataSource datasource = (DataSource) getServletContext().getAttribute("datasource");
		try {
			Connection connection = datasource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? and password = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			if(!rs.isBeforeFirst()) {
				request.setAttribute("error", true);
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			}
			else {
				while(rs.next()) {
					request.getSession().setAttribute("isLoggedIn", true);
					request.getSession().setAttribute("firstName", rs.getString("first_name"));
					request.getSession().setAttribute("lastName", rs.getString("last_name"));
					response.sendRedirect(request.getContextPath() + "/");
					
				}	
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
