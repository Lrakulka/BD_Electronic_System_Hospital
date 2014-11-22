<%@page import="logic.AuthorizeUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Авторизація</title>
</head>
<body>
	<h1>Вхід в систему</h1>
    <form action="LogIn" method="post">
        <table cellpadding="3pt">
            <tr> 
                <td>Ім'я користувача :</td>
                <td><input type="text" name="userName" size="30" /></td>
            </tr>
            <tr>
                <td>Пароль :</td>
                <td><input type="password" name="password" size="30" /></td>
            </tr>
        </table>
        <p />
        <input type="submit" value="Увійти" />
    </form>
    <%     
    	String value = (String) session.getAttribute("ErrorLogIn");
    	if (value != null && value.equals("TRUE") && !AuthorizeUser.isAuthorizeUser()) {
    		out.println("<h1><font color=\"red\">Неправельний пароль або логін або рівеь доступу не підходить</font></h1>");    
    		session.removeAttribute("ErrorLogIn");
    	}
    %>
</body>
</html>