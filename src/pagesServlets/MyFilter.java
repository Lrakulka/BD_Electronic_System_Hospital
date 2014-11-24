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

/**Клас-фільтр для захисту від не авторизованого доступу до системи*/
public class MyFilter implements Filter {

	/**Метод викликається при знищені об'єкта фільтра*/
	@Override
	public void destroy() {
	}
	
	/**Метод викликається при будь-яких запитах і відповідях клієнту.
	 * Метод перевіряє чи користувач авторизован, якщо так то передати 
	 * далі запит\відповідь, якщо ні перенаправити користувача на 
	 * сторіну авторизації
	 * @param request запит клієнта
	 * @param response відповідь клієнта
	 * @param chain ланцюг фільтрів*/
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
			request.setCharacterEncoding("utf8");
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

	/**Метод викликається при створені об'єкта фільтра
	 * @param fConfig налаштування для фільтра*/
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
