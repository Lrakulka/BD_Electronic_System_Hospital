<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log in form</title>
</head>
<body>
	<h1>Log in form</h1>
    <form action="LogIn" method="post">
        <table cellpadding="3pt">
            <tr>
                <td>User Name :</td>
                <td><input type="text" name="userName" size="30" /></td>
            </tr>
            <tr>
                <td>Password :</td>
                <td><input type="password" name="password" size="30" /></td>
            </tr>
        </table>
        <p />
        <input type="submit" value="Log in" />
    </form>
    <%     
    	String value = (String) session.getAttribute("ErrorLogIn");
    	if (value != null && value.equals("TRUE")) {
    		out.println("<h1><font color=\"red\">Incorrect password or login or access level or something else</font></h1>");    
    		session.removeAttribute("ErrorLogIn");
    	}
    %>
</body>
</html>