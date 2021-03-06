<%@page import="hibernate.Disease"%>
<%@page import="java.util.ArrayList"%>
<%@page import="logic.OperationsWithDiseases"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Diseases</title>
</head>
<body>
	<h1>Додати хворобу</h1>
	<%	request.setCharacterEncoding("utf8");
		if (request.getParameter("SaveDisease") != null) {
			Disease disease = new Disease();
			disease.setName(request.getParameter("SaveDisease"));
			OperationsWithDiseases.getOperationsWithDiseases().register(disease);
		}	
		if (request.getParameter("DeleteDisease") != null) {
			Disease disease = new Disease();
			disease.setId(Integer.valueOf(request.getParameter("DeleteDisease")));
			OperationsWithDiseases.getOperationsWithDiseases().deleteById(disease);
		}
	%>
	<form name="saveDisease" method="post">
		<table cellpadding="3pt">
			<tr>
				<td>Ім'я нової хвороби</td>
				<td><input type="text" name="SaveDisease"></td>
				<td><input type="submit" value="Додати"></td>
			</tr>
		</table>
	</form>
	 <form action="LogOutServlet" method="post">
		<input type="submit" value="Вийти" >
	</form>
	<a href="StartPage.jsp"><button>Головна сторінка</button></a>
	<h1>Таблиця хвороб</h1>
	<form name="deleteDisease" method="post">
		<table border="1" cellpadding="8">
			<tr>
				<td>Назва хвороби</td>
			</tr>
			<%	ArrayList<Disease> diseases = OperationsWithDiseases.
					getOperationsWithDiseases().getAllObj();
				for(int i = 0; i < diseases.size() ; ++i) {
					out.println("<tr><td>" + diseases.get(i).getName() + "</td><td><button " +
					"name=\"DeleteDisease\" value=\"" + diseases.get(i).getId() + 
					"\">Видалити</button></td></tr>");
				}
			%>
		</table>
	</form>
</body>
</html>