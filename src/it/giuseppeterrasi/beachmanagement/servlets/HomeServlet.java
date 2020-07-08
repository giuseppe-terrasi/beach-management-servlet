package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.daos.ReviewDao;
import it.giuseppeterrasi.beachmanagement.daos.UmbrellaGridDao;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		UmbrellaGridDao umbrellaGridDao = new UmbrellaGridDao(dataSource);
		ReviewDao reviewDao = new ReviewDao(dataSource);
		
		Date now =  Calendar.getInstance().getTime();
		
		Set<Entry<Integer, List<UmbrellaGridDao>>> grid = umbrellaGridDao.getCurrentGridStatus();
		
		List<ReviewDao> reviews = reviewDao.getReviews(5);
		
		request.setAttribute("grid", grid);
		request.setAttribute("reviews", reviews);
		request.setAttribute("now", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(now));
		request.setAttribute("cssActivePage", "home");
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
