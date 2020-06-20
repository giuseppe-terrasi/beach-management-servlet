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

@WebFilter("/*")
public class LoggedInFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		
		boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
		String loginPath = request.getContextPath() + "/login";
		boolean isLoginRequest = request.getRequestURI().equals(loginPath);
		
		if(!isLoggedIn && !isLoginRequest)
			response.sendRedirect(request.getContextPath() + "/login");
		else if(isLoggedIn && isLoginRequest)
			response.sendRedirect(request.getContextPath() + "/");
		else
			chain.doFilter(request, response);
		
	}

}
