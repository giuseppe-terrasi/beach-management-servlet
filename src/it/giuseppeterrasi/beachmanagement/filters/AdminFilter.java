package it.giuseppeterrasi.beachmanagement.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.giuseppeterrasi.beachmanagement.models.AppUser;

@WebFilter("/admin/*")
public class AdminFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		
		AppUser appUser = null;
		
		if(session != null) {
			appUser = (AppUser)session.getAttribute("appUser");
		}
		
		
		if(appUser != null && appUser.getRoles().contains("ADMIN"))
		{
			chain.doFilter(request, response);	
		}
		else
		{
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

}
