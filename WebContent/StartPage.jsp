<%@page import="logic.AuthorizeUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Головна сторінка</title>
</head>
<body>
	<h2>Головна сторінка Адміністратора</h2>
	<table>	
		<tr>
			<td><a href="UserList.jsp"><button>Список користувачів</button></a></td>
		</tr>
		<tr>
			<td><a href="CardList.jsp"><button>Список карток пацієнтів</button></a></td>
		</tr>
		<tr>
			<td><form action="ModifyUser.jsp" method="post">
					<input type="hidden" name="UserAdd" value="True">
					<input type="submit" value="Додати користувача">
					</form>
			</td>
		</tr>
		<tr>
			<td><a href="Disease.jsp"><button>Додати хворобу</button></a></td>
		</tr>
		<tr>
			<td><form action="ModifyUser.jsp" method="post">
					<button name="ButtonModify" value=
					"<%=AuthorizeUser.getAuthorizeUser().getId()%>">Налаштування акаунту</button>
                </form>
			</td>
		</tr>
		<tr>
			<td><a href="NewFile.jsp"><button>Перейти в режим доктора(Віталін проект)</button></a></td>
		</tr>
	</table>
	<form action="LogOutServlet" method="post">
		<input type="submit" value="Вийти" >
	</form>
</body>
</html>