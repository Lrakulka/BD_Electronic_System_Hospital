package pagesServlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.AuthorizeUser;

public class MyFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		    HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	        String uri = req.getRequestURI();
	         
	        if (!AuthorizeUser.isAuthorizeUser() && !(uri.endsWith("LogIn") || 
	        		uri.endsWith("Authorize.jsp") || 
	        		uri.endsWith("/BD_Electronic_System_Hospital_/")))
	        		 {
				res.sendRedirect("Authorize.jsp");
			} else
				chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
