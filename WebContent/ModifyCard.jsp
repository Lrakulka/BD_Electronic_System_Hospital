<%@page import="logic.OperationsWithDiagnosis"%>
<%@page import="logic.OperationsWithDiseases"%>
<%@page import="logic.OperationWithSessions"%>
<%@page import="hibernate.Gender"%>
<%@page import="logic.OperationsWithCards"%>
<%@page import="java.util.ArrayList"%>
<%@page import="hibernate.Note"%>
<%@page import="logic.OperationWithNotes"%>
<%@page import="java.sql.Date"%>
<%@page import="hibernate.Session"%>
<%@page import="hibernate.Disease"%>
<%@page import="hibernate.Diagnos"%>
<%@ page import="hibernate.Card"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Редагування картки</title>
</head>
<body>
	<%! Card card;%>
	<%	card = new Card();
		if (request.getParameter("ButtonSessionsDelete") != null) {
			Session sess = new Session();
			sess.setId(Integer.valueOf(request.getParameter("ButtonSessionsDelete")));
			OperationWithSessions.getOperationWithSessions().deleteById(sess);
		}
		if (request.getParameter("ButtonNoteDelete") != null) {
			Note note = new Note();
			note.setId(Integer.valueOf(request.getParameter("ButtonNoteDelete")));
			OperationWithNotes.getOperationWithNotes().deleteById(note);
		}
		card.setId((Integer) request.getSession().getAttribute("ButtonModify")); 
		if ( request.getParameter("ButtonSave") != null) {
			card.setAge(Short.valueOf(request.getParameter("CardAge")));
			card.setIsAgain((request.getParameter("CardIsAgain") != null ? true :false));
			card.setName(request.getParameter("CardName"));
			card.setNote(request.getParameter("CardNote"));
			card.setSex(Gender.getValue(request.getParameter("CardGender")));
			if( OperationsWithCards.getOperationWithCard().update(card))
				out.print("<h1>Успішно</h1>");
			else out.print("<h1>Помилка</h1>");
		} 
		card = OperationsWithCards.getOperationWithCard().isRegisted(card);
	%>
	<h1>Картка</h1>
	<form name="CardDate">
		<table>
			<tr><td>Ім'я:</td><td><input type="text" name="CardName" 
			value="<%=card.getName() %>"></td></tr>
			<tr><td>Вік:</td><td><input type="text" name="CardAge" 
			value="<%=card.getAge() %>"></td></tr>
			<tr><td>Стать:</td><td>Чоловік<input type="radio" name="CardGender" value="Man" 
			<%=card.getSex().equals(Gender.male) ? "checked" : "" %>>
			<input type="radio" name="CardGender" value="Woman" 
			<%=card.getSex().equals(Gender.female) ? "checked" : "" %>>Жінка</td></tr>
			<tr><td>Вперше:</td><td><input type="checkbox" name="CardIsAgain" 
			value="<%=card.getIsAgain() %>"
			 <%=card.getIsAgain().equals(true) ? "checked" : "" %>></td></tr>
			<tr><td>Нотатки:</td><td><textarea cols="40" rows="5" name="CardNote"><%=card.getNote()%></textarea></td></tr>
		</table>
		<button name="ButtonSave" value="Save">Зберегти</button>
		<button name="ButtonCencel" value="Cancel">Відмінити</button>
	</form>
	<br>
	<form action="LogOutServlet" method="post">
		<input type="submit" value="Вийти" >
	</form>
	<a href="StartPage.jsp"><button>Головна сторінка</button></a>
	<%
		out.println("<h1>Нотатки</h1>");
		ArrayList<Note> notes = card.getAllNotes();
		for(int i = 0; i < notes.size(); ++i) {
			if ( request.getParameter("ButtonNoteSave") != null &&
					Integer.valueOf(request.getParameter("ButtonNoteSave")).
					equals(notes.get(i).getId())) {
				notes.get(i).setHidden_note(request.getParameter("userNote"));
				notes.get(i).setHide(request.getParameter("userNoteIsHide") != 
						null ? true : false);
				notes.get(i).setDate(Date.valueOf(request.getParameter("userNoteDate")));
				OperationWithNotes.getOperationWithNotes().update(notes.get(i));
			}
			out.println(
				"<form name=\"Note #" + (1 + i) + "\" method=\"post\">" +
				"<h2>Нотаток " + (i + 1) + 
				"</h2><textarea cols=\"40\" rows=\"3\" name=\"userNote\">" + 
						notes.get(i).getHidden_note() + "</textarea>" + 
				"<br><button value=\"" + notes.get(i).getId() + 
				"\" name=\"ButtonNoteSave\" >Зберегти" +
				"</button><button value=\"" + notes.get(i).getId() + 
				"\" name=\"ButtonNoteDelete\" >Видалити" +
				"</button><button value=\"" + notes.get(i).getId() + 
				"\" name=\"ButtonNoteCencel\" >Відмінити</button>" +
				"Hide note <input type=\"checkbox\" name=\"userNoteIsHide\" " + 
				"value=\"" + notes.get(i).getHide() + 
				"\" size=\"1\"" + (notes.get(i).getHide().equals(true) ? "checked" : "") +
				">Час <input type=\"text\" name=\"userNoteDate\"" +
				"size=\"5\" value=\"" + notes.get(i).getDate().toString() + "\">" + 
				"<input type=\"hidden\" name=\"ButtonModify\" value=\"" + 
						card.getId() + "\"></form><br>");
		}
		out.println("<h1>Сесії лікування</h1>");
		ArrayList<Session> sessions = card.getAllSessions();
		for(int i = 0; i < sessions.size(); ++i) {
			if ( request.getParameter("ButtonSessionsSave") != null &&
					Integer.valueOf(request.getParameter("ButtonSessionsSave")).
					equals(sessions.get(i).getId())) {
				sessions.get(i).setResult(request.getParameter("sessionResult") != 
						null ? true : false );
				Diagnos diagnos = sessions.get(i).getDiagnos();
				diagnos.setDescription(request.getParameter("sessionDiagnosDescription"));
				Disease disease = new Disease();
				disease.setName(request.getParameter("sessionDiagnosDescriptionDisease"));
				if (disease.getName() != null) {
					disease = OperationsWithDiseases.getOperationsWithDiseases().isRegisted(disease);
					diagnos.setDisease(disease);
				}
				OperationsWithDiagnosis.getOperationsWithDiagnosis().update(diagnos);
				
				OperationWithSessions.getOperationWithSessions().update(sessions.get(i));
			}
			out.println(
				"<form name=\"Session #" + (1 + i) + "\" method=\"post\">" +
				"<h2>Сесія " + (i + 1) + 
				"</h2><textarea cols=\"40\" rows=\"3\" name=\"sessionDiagnosDescription\">" + 
						sessions.get(i).getDiagnos().getDescription() + "</textarea>" + 
				"<br><button value=\"" + sessions.get(i).getId() + 
				"\" name=\"ButtonSessionsSave\" >Зберегти" +
				"</button><button value=\"" + sessions.get(i).getId() + 
				"\" name=\"ButtonSessionsDelete\" >Видалити" +
				"</button><button value=\"" + sessions.get(i).getId() + 
				"\" name=\"ButtonSessionsCencel\" >Відмінити</button>" +
				"Результат лікування <input type=\"checkbox\" name=\"sessionResult\" " + 
				"value=\"" + sessions.get(i).getResult() + 
				"\" size=\"1\"" + (sessions.get(i).getResult().equals(true) ? 
				"checked" : "") + "/><select size=\"1\" " + 
				"name=\"sessionDiagnosDescriptionDisease\"><option disabled selected>" + 
				sessions.get(i).
				getDiagnos().getDisease().getName() + "</option>");
				ArrayList<Disease> diseases = OperationsWithDiseases.getOperationsWithDiseases().
						getAllObj();
				for( i = 0; i < diseases.size(); ++i)
					out.println("<option value=\"" + diseases.get(i).getName() + "\">" + 
				diseases.get(i).getName() + "</option>");
				out.println("</select><input type=\"hidden\" name=\"ButtonModify\" value=\"" + 
						card.getId() + "\"></form><br>");
		}
	%>
</body>
</html>