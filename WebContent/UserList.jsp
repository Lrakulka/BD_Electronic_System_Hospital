<%@ page import="logic.AuthorizeUser"%>
<%@ page import="logic.OperationsWithUsers"%>
<%@ page import="hibernate.User"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html; charset=windows-1251" %>
<html>
<head>
    <title>
        Таблиця користувачів
    </title>
</head> 
<body>
    <form action="LogOutServlet" method="post">
	<input type="submit" value="Logout" >
	</form>
	<a href="StartPage.jsp"><button>Main page</button></a>
   <%  if (request.getParameter("ButtonDelete") != null) {
	    	User user = new User();
	    	user.setId(Integer.valueOf(request.getParameter("ButtonDelete")));
	    	OperationsWithUsers.getOperationWithUsers().deleteById(user);
    	} 
		if (request.getParameter("ButtonModify") != null) {
    		request.getSession().setAttribute("ButtonModify", Integer.
    				valueOf(request.getParameter("ButtonModify")));
    		response.sendRedirect("AddModifyUser.jsp");
    	}
   	
    %>
    <h1>List of Users</h1>		
    <table border="1" cellpadding="8">	    	
		<form name="UserListForm" method="post">
	    	<tr>
	    		<%	if (request.getParameter("userName") != null) {
	    				out.print("<th><input type=\"text\" name=\"userName\" value=\"" + 
	    					request.getParameter("userName") + "\" size=\"20\" /></th>");
	    				out.print("<th><input type=\"text\" name=\"userPhone\" value=\"" + 
	    					request.getParameter("userPhone") + "\"size=\"20\" /></th>");
	    				out.print("<th>L:<input type=\"text\" name=\"userLowAccessLevel\"" +
	    						" value=\"" + 
	    					request.getParameter("userLowAccessLevel") + 
		    					"\" size=\"1\" />");
	    				out.print(" H:<input type=\"text\" name=\"userHightAccessLevel\" " + 
		    					"value=\"" + request.getParameter("userHightAccessLevel") + 
	    					"\" size=\"1\" /></th>");
	    			}
		    		else {
		    			out.println("<th><input type=\"text\" name=\"userName\" size=" +
		    					"\"20\" /></th>");
		    			out.println("<th><input type=\"text\" name=\"userPhone\" size=" +
		    					"\"20\" /></th>");
		    			out.println("<th>L:<input type=\"text\" name=\"userLowAcces" +
		    					"sLevel\" size=\"1\" value=\"0\"/>"); 
		    			out.println("H:<input type=\"text\" name=\"userHightAccessLev" +
		    					"el\" size=\"1\" value=\"2\"/></th>");
		    		}
    			%>
	            <th><input type="submit" name="submit" value="Use filters" size="15" /></th>
	        </tr> 
        </form>    
        <tr>
            <th>Name</th>
            <th>Phone</th>
            <th>Access level</th>
        </tr>      
       	<form name="ModifyUser" method="post">
            <% 	ArrayList<User> users;
            	if (request.getParameter("userName") != null) {
            		users = OperationsWithUsers.getOperationWithUsers().
            			getAllObjSatisfyFiltr(request.getParameter("userName"), 
            				request.getParameter("userPhone"), 
            				Short.valueOf(request.getParameter("userLowAccessLevel")), 
            				Short.valueOf(request.getParameter("userHightAccessLevel")));
            	}
            	else {
            		users = OperationsWithUsers.getOperationWithUsers().
            				getAllObj();
            	}
            	for(int i = 0; i < users.size(); ++i){
                out.println("<tr>");
                out.println("<td>" + users.get(i).getName() + "</td>");
                out.println("<td>" + users.get(i).getPhone() + "</td>");
                out.println("<td>" + users.get(i).getAccess_level() + "</td>");  
                if( users.get(i).equals(AuthorizeUser.getAuthorizeUser()))
                	out.println("<td>It's you</td>");  
                else out.println("<td><button name=\"ButtonDelete\" value=\"" +
                		users.get(i).getId() + "\">Delete</button>" +
                		"<button name=\"ButtonModify\" value=\"" +
                    	users.get(i).getId() + "\">Modify</button>" + 
            			"<button name=\"ButtonenterLikeThisUser\" value=\"" +
                    	users.get(i).getId() + "\"><font color=\"blue\">Enter" +
                				" as this user</font></button></td>");  
                out.println("</tr>");
            } %>
        </form>
    </table>
</body>
</html>