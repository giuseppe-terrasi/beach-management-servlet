package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.daos.BookingDao;
import it.giuseppeterrasi.beachmanagement.models.AppUser;

/**
 * Servlet implementation class BookingListServlet
 */
@WebServlet("/mybookings")
public class BookingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		BookingDao bookingDao = new BookingDao(dataSource);
		HttpSession session = request.getSession();
		
		AppUser user = (AppUser)session.getAttribute("appUser");
		
		List<BookingDao> bookings = bookingDao.getAllUserBookings(user.getId());
		
		request.setAttribute("bookings", bookings);
		
		request.setAttribute("cssActivePage", "userBookingList");
		request.setAttribute("cssCollapsedDropdown", "userBooking");
		request.setAttribute("cssShowDropdownContent", "userBooking");
		request.getRequestDispatcher("/WEB-INF/views/userBookingList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
