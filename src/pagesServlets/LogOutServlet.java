package pagesServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.AuthorizeUser;
/**Клас-сервелет реалізовує вихід користувача*/
public class LogOutServlet extends HttpServlet {

	/**Унікальний індефікатор для сервелету*/
	private static final long serialVersionUID = 361381042632159371L;

	/**Метод виконує операцію виходу користувача і викликає 
	 * метод  AuthorizeUser.setUserLogOut для йогот виходу із системи
	 * @param request запит, який приходить від користувача
	 * @param response відповідь, яка надсилається користувачу*/
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		 request.setCharacterEncoding("utf8");
		 HttpSession session = request.getSession();
		 if (session != null)
			 session.invalidate();
		 AuthorizeUser.setUserLogOut();
		 response.sendRedirect("Authorize.jsp");		 
	 }
}
