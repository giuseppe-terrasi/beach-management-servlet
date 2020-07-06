package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.giuseppeterrasi.beachmanagement.daos.UmbrellaGridDao;
import it.giuseppeterrasi.beachmanagement.models.BookingListRequestModel;

/**
 * Servlet implementation class BookingListServlet
 */
@WebServlet("/getBookings")
@MultipartConfig
public class BookingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingListServlet() {
        super();
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
		
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		UmbrellaGridDao umbrellaGridDao = new UmbrellaGridDao(dataSource);
		
		String jsonString = request.getParameter("data");
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		mapper.setDateFormat(df);
		
		BookingListRequestModel model = mapper.readValue(jsonString, BookingListRequestModel.class);
		
		Set<Entry<Integer, List<UmbrellaGridDao>>> grid = umbrellaGridDao.getCurrentGridStatus(model.getFromDate(), model.getToDate());
		
		request.setAttribute("grid", grid);
		request.getRequestDispatcher("/WEB-INF/views/fragments/grid/bookingGrid.jsp").forward(request, response);
	}

}
