package it.giuseppeterrasi.beachmanagement.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.daos.UmbrellaGridDao;

/**
 * Servlet implementation class AdminUmbrellaGridServlet
 */
@WebServlet("/admin/umbrellagrid")
@MultipartConfig
public class AdminUmbrellaGridServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUmbrellaGridServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		UmbrellaGridDao umbrellaGridDao = new UmbrellaGridDao(dataSource);
		Set<Entry<Integer, List<UmbrellaGridDao>>> grid = umbrellaGridDao.getGrid();
		
		request.setAttribute("grid", grid);
		request.setAttribute("cssActivePage", "gird");
		
		request.getRequestDispatcher("/WEB-INF/views/admin/umbrellaGrid.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource dataSource = (DataSource) getServletContext().getAttribute("datasource");
		UmbrellaGridDao umbrellaGridDao = new UmbrellaGridDao(dataSource);
		
		umbrellaGridDao.setGridRow(Integer.parseInt(request.getParameter("gridRow")));
		umbrellaGridDao.setGridColumn(Integer.parseInt(request.getParameter("gridColumn")));
		umbrellaGridDao.setCapacity(Integer.parseInt(request.getParameter("capacity")));
		umbrellaGridDao.setPrice(Float.parseFloat(request.getParameter("price")));
		
		umbrellaGridDao.save();
		
		request.setAttribute("column", umbrellaGridDao);
		
		request.getRequestDispatcher("/WEB-INF/views/fragments/grid/gridTd.jsp").forward(request, response);
	}

}
