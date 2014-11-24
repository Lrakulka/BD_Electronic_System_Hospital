package pagesServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.AuthorizeUser;

/**Клас-сервелет для авторизації користувача*/
public class AuthorizeServlet extends HttpServlet{

	/**Унікальний індефікатор для сервелету*/
	private static final long serialVersionUID = 1L;
	
	/**Метод приймає введені дані на формі користувачем і викликає 
	 * метод AuthorizeUser.authorizeUser для йогот авторизації
	 * @param request запит, який приходить від користувача
	 * @param response відповідь, яка надсилається користувачу*/
	@Override
	protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("utf8");
		 	if (AuthorizeUser.authorizeUser(request.getParameter("userName"),
		 			request.getParameter("password")))
	            response.sendRedirect("StartPage.jsp");
		 	else {
			 	HttpSession session = request.getSession();
			 	session.setAttribute("ErrorLogIn", "TRUE");
		 		response.sendRedirect("Authorize.jsp");
		 	}
	    }
}
