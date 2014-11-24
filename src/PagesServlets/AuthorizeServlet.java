package pagesServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.AuthorizeUser;

/**����-�������� ��� ����������� �����������*/
public class AuthorizeServlet extends HttpServlet{

	/**��������� ����������� ��� ���������*/
	private static final long serialVersionUID = 1L;
	
	/**����� ������ ������ ��� �� ���� ������������ � ������� 
	 * ����� AuthorizeUser.authorizeUser ��� ����� �����������
	 * @param request �����, ���� ��������� �� �����������
	 * @param response �������, ��� ����������� �����������*/
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
