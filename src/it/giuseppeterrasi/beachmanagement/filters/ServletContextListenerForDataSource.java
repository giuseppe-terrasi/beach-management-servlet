package it.giuseppeterrasi.beachmanagement.filters;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ServletContextListenerForDataSource implements ServletContextListener {

	@Resource(name = "jdbc/mysql")
	DataSource dataSource;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("datasource", dataSource);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContextListener.super.contextDestroyed(sce);
	}
}
