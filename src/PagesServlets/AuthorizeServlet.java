package PagesServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	 protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	 
	        String userName = request.getParameter("userName");
	        String password = request.getParameter("password");
	 
	       // HttpSession session = request.getSession(true);
	        try {
	        	/*
	            UserDAO userDAO = new UserDAO();
	            userDAO.addUserDetails(userName, password, "1234567", 3);
	            response.sendRedirect("Success");
	            */
	        } catch (Exception e) {
	 
	            e.printStackTrace();
	        }
	 
	    }
}
