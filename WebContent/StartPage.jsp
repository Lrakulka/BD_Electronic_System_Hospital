<%@page import="logic.AuthorizeUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Admin mod</h2>
	<table>	
		<tr>
			<td><a href="UserList.jsp"><button>List of Users</button></a></td>
		</tr>
		<tr>
			<td><a href="CardList.jsp"><button>List of Cards</button></a></td>
		</tr>
		<tr>
			<td><form action="AddModifyUser.jsp" method="post">
					<input type="hidden" name="UserAdd" value="True">
					<input type="submit" value="Add user">
					</form>
			</td>
		</tr>
		<tr>
			<td><a href="Disease.jsp"><button>Disease</button></a></td>
		</tr>
		<tr>
			<td><form action="AddModifyUser.jsp" method="post">
					<button name="ButtonModify" value=
					"<%=AuthorizeUser.getAuthorizeUser().getId()%>">Account options</button>
                </form>
			</td>
		</tr>
		<tr>
			<td><a href="NewFile.jsp"><button>Change to doctor mod (Vitalin project)</button></a></td>
		</tr>
	</table>
	<form action="LogOutServlet" method="post">
		<input type="submit" value="Logout" >
	</form>
</body>
</html>