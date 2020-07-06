package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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
 * Servlet implementation class BookNowServlet
 */
@WebServlet("/book")
public class BookNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookNowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("cssActivePage", "userBookNow");
		request.setAttribute("cssCollapsedDropdown", "userBooking");
		request.setAttribute("cssShowDropdownContent", "userBooking");
		request.getRequestDispatcher("/WEB-INF/views/userBookNow.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		HttpSession session = request.getSession();
		BookingDao bookingDao = new BookingDao(dataSource);
		
		AppUser appUser = (AppUser)session.getAttribute("appUser");
		
		String[] gridIdStrings = request.getParameterValues("grid-id");
		int[] gridIds = Arrays.stream(gridIdStrings).mapToInt(Integer::parseInt).toArray();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	    Date parsedDate;
	    Timestamp fromDate;
	    Timestamp toDate;
		try {
			parsedDate = dateFormat.parse(request.getParameter("dateFrom"));
		    fromDate = new java.sql.Timestamp(parsedDate.getTime());
		    parsedDate = dateFormat.parse(request.getParameter("dateTo"));
		    toDate = new java.sql.Timestamp(parsedDate.getTime());
		    
		    bookingDao.book(appUser.getId(), fromDate, toDate, 2, gridIds);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		doGet(request, response);
	}

}
