<%@ page import="logic.AuthorizeUser"%>
<%@ page import="logic.OperationsWithUsers"%>
<%@ page import="hibernate.User"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html; charset=windows-1251" %>
<html>
<head>
    <title>
        Таблица пользователей
    </title>
</head>
 
<body>
    <h1>List of Users</h1>
	<form name="UserListForm" method="post">
	    <table border="1" cellpadding="8">
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
	    				out.print("H:<input type=\"text\" name=\"userHightAccessLevel\" value=\"" + 
	    					request.getParameter("userHightAccessLevel") + 
	    					"\" size=\"1\" /></th>");
	    			}
		    		else {
		    			out.println("<th><input type=\"text\" name=\"userName\" size=\"20\" /></th>");
		    			out.println("<th><input type=\"text\" name=\"userPhone\" size=\"20\" /></th>");
		    			out.println("<th><input type=\"text\" name=\"userLowAccessLevel\" size=\"5\" value=\"0\"/>"); 
		    			out.println("<input type=\"text\" name=\"userHightAccessLevel\" size=\"5\" value=\"2\"/></th>");
		    		}
    			%>
	            <th><input type="submit" name="submit" value="Use filters" size="15" /></th>
	        </tr>     
	        <tr>
	            <th>Name</th>
	            <th>Phone</th>
	            <th>Access level</th>
	        </tr>      
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
	            				getAllObjSatisfyFiltr("", "", (short) 0, (short) 2);
	            	}
	            	for(int i = 0; i < users.size(); ++i){
	                out.println("<tr>");
	                out.println("<td>" + users.get(i).getName() + "</td>");
	                out.println("<td>" + users.get(i).getPhone() + "</td>");
	                out.println("<td>" + users.get(i).getAccess_level() + "</td>");  
	                if( users.get(i).equals(AuthorizeUser.getAuthorizeUser()))
	                	out.println("<td>It's you</td>");  
	                else out.println("<td><button>Delete</button><button>Modifier</button></td>");  
	                out.println("</tr>");
	            } %>
	    </table>
    </form>
    <form action="LogOutServlet" method="post">
		<input type="submit" value="Logout" >
	</form>
</body>
</html>