package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.daos.ReviewDao;
import it.giuseppeterrasi.beachmanagement.models.AppUser;

/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/saveReview")
@MultipartConfig
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		ReviewDao reviewDao = new ReviewDao(dataSource);
		
		AppUser appUser = (AppUser)session.getAttribute("appUser");
		
		reviewDao.setUserId(appUser.getId());
		reviewDao.setScore(Integer.parseInt(request.getParameter("score")));
		reviewDao.setTitle(request.getParameter("title"));
		reviewDao.setMessage(request.getParameter("message"));
		reviewDao.setReviewedOn(new Timestamp(System.currentTimeMillis()));
		
		reviewDao.save();
		
		request.setAttribute("review", reviewDao);
		
		
		
		if(reviewDao.isInsertSucceded()) {
			
			response.setStatus(201);
		}
		
		request.getRequestDispatcher("/WEB-INF/views/fragments/review/reviewForm.jsp").forward(request, response);	
		
	}

}
