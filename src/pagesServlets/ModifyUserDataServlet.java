package pagesServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyUserDataServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7986124191451283511L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		 System.out.print(request.getParameter("ButtonDelete") != null);		 
	 }
	
}
