<%@page import="logic.OperationsWithDiagnosis"%>
<%@page import="logic.OperationsWithDiseases"%>
<%@page import="logic.OperationWithSessions"%>
<%@page import="hibernate.Gender"%>
<%@page import="javax.swing.text.StyledEditorKit.BoldAction"%>
<%@page import="logic.OperationsWithCards"%>
<%@page import="java.util.ArrayList"%>
<%@page import="hibernate.Note"%>
<%@page import="logic.OperationWithNotes"%>
<%@page import="java.sql.Date"%>
<%@page import="hibernate.Session"%>
<%@page import="hibernate.Disease"%>
<%@page import="hibernate.Diagnos"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="hibernate.Card"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
			card.setIsAgain(Boolean.valueOf(request.getParameter("CardIsAgain")));
			card.setName(request.getParameter("CardName"));
			card.setNote(request.getParameter("CardNote"));
			card.setSex(Gender.getValue(request.getParameter("CardGender")));
			OperationsWithCards.getOperationWithCard().update(card);
		} 
		card = OperationsWithCards.getOperationWithCard().isRegisted(card);
	%>
	<h1>Card</h1>
	<form name="CardDate">
		<table>
			<tr><td>Name:</td><td><input type="text" name="CardName" value="<%=card.getName() %>"></td></tr>
			<tr><td>Age:</td><td><input type="text" name="CardAge" value="<%=card.getAge() %>"></td></tr>
			<tr><td>Gender:</td><td><input type="text" name="CardGender" value="<%=card.getSex().getGenderName()%>"></td></tr>
			<tr><td>Is Again:</td><td><input type="text" name="CardIsAgain" value="<%=card.getIsAgain() %>"></td></tr>
			<tr><td>Note:</td><td><textarea cols="40" rows="5" name="CardNote"><%=card.getNote()%></textarea></td></tr>
		</table>
		<button name="buttonSave" value="Save">Save</button>
		<button name="buttonCencel" value="Cancel">Cancel</button>
	</form>
	<br>
	<form action="LogOutServlet" method="post">
		<input type="submit" value="Logout" >
	</form>
	<a href="StartPage.jsp"><button>Main page</button></a>
	<%
		out.println("<h1>Notes</h1>");
		ArrayList<Note> notes = card.getAllNotes();
		for(int i = 0; i < notes.size(); ++i) {
			if ( request.getParameter("ButtonNoteSave") != null &&
					Integer.valueOf(request.getParameter("ButtonNoteSave")).
					equals(notes.get(i).getId())) {
				notes.get(i).setHidden_note(request.getParameter("userNote"));
				notes.get(i).setHide(Boolean.valueOf(request.getParameter("userNoteIsHide")));
				notes.get(i).setDate(Date.valueOf(request.getParameter("userNoteDate")));
				OperationWithNotes.getOperationWithNotes().update(notes.get(i));
			}
			out.println(
				"<form name=\"Note #" + (1 + i) + "\" method=\"post\">" +
				"<h2>Note " + (i + 1) + 
				"</h2><textarea cols=\"40\" rows=\"3\" name=\"userNote\">" + 
						notes.get(i).getHidden_note() + "</textarea>" + 
				"<br><button value=\"" + notes.get(i).getId() + 
				"\" name=\"ButtonNoteSave\" >Save" +
				"</button><button value=\"" + notes.get(i).getId() + 
				"\" name=\"ButtonNoteDelete\" >Delete" +
				"</button><button value=\"" + notes.get(i).getId() + 
				"\" name=\"ButtonNoteCencel\" >Cancel</button>" +
				"Hide note <input type=\"text\" name=\"userNoteIsHide\" size=\"1\" value=\"" + 
				notes.get(i).getHide() + "\">Date <input type=\"text\" name=\"userNoteDate\"" +
				"size=\"5\" value=\"" + notes.get(i).getDate().toString() + "\">" + 
				"<input type=\"hidden\" name=\"ButtonModify\" value=\"" + 
						card.getId() + "\"></form><br>");
		}
	%>
	<%
		out.println("<h1>Sessions</h1>");
		ArrayList<Session> sessions = card.getAllSessions();
		for(int i = 0; i < sessions.size(); ++i) {
			if ( request.getParameter("ButtonSessionsSave") != null &&
					Integer.valueOf(request.getParameter("ButtonSessionsSave")).
					equals(sessions.get(i).getId())) {
				sessions.get(i).setResult(Boolean.valueOf(request.getParameter("sessionResult")));
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
				"<h2>Session " + (i + 1) + 
				"</h2><textarea cols=\"40\" rows=\"3\" name=\"sessionDiagnosDescription\">" + 
						sessions.get(i).getDiagnos().getDescription() + "</textarea>" + 
				"<br><button value=\"" + sessions.get(i).getId() + 
				"\" name=\"ButtonSessionsSave\" >Save" +
				"</button><button value=\"" + sessions.get(i).getId() + 
				"\" name=\"ButtonSessionsDelete\" >Delete" +
				"</button><button value=\"" + sessions.get(i).getId() + 
				"\" name=\"ButtonSessionsCencel\" >Cancel</button>" +
				"Session result <input type=\"text\" name=\"sessionResult\" size=\"1\" value=\"" + 
				sessions.get(i).getResult() + "\"><select size=\"1\" " + 
				"name=\"sessionDiagnosDescriptionDisease\"><option disabled selected>" + sessions.get(i).
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