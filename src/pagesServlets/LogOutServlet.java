package pagesServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.AuthorizeUser;
/**����-�������� �������� ����� �����������*/
public class LogOutServlet extends HttpServlet {

	/**��������� ����������� ��� ���������*/
	private static final long serialVersionUID = 361381042632159371L;

	/**����� ������ �������� ������ ����������� � ������� 
	 * �����  AuthorizeUser.setUserLogOut ��� ����� ������ �� �������
	 * @param request �����, ���� ��������� �� �����������
	 * @param response �������, ��� ����������� �����������*/
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
