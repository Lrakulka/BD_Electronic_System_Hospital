<%@page import="logic.OperationWithNotes"%>
<%@ page import="hibernate.User"%>
<%@ page import="hibernate.Note"%>
<%@ page import="logic.OperationsWithUsers"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User date</title>
</head>
<body>
	<!-- Shito kod -->
	<%!	User user = new User("", null, "", "");%>
	<%	if (request.getParameter("Save") != null) {
			boolean status;
			user = new User(request.getParameter("userName"), 
					Short.valueOf(request.getParameter("userAccess_level")),
					request.getParameter("userPhone"), request.getParameter("userPwd"));
			if (!request.getParameter("UserId").equals("null")) {
				user.setId(Integer.valueOf(request.getParameter("UserId")));
				status = OperationsWithUsers.getOperationWithUsers().update(user);
			} else status = OperationsWithUsers.getOperationWithUsers().register(user);
			if( status)
				out.print("<h1>Successful</h1>");
			else out.print("<h1>Not Successful</h1>");
		}
		if( request.getParameter("UserAdd") != null) {
			out.print("<h1>Add new user</h1>");
		}
		else {
			if (request.getSession().getAttribute("ButtonModify") != null) {
				request.setAttribute("ButtonModify", 
						request.getSession().getAttribute("ButtonModify"));
				request.getSession().setAttribute("ButtonModify", 
						request.getSession().getAttribute("ButtonModify"));
				//request.getSession().removeAttribute("ButtonModify");
			}
			out.print("<h1>Modify user</h1>");
			user.setId((Integer) request.getAttribute("ButtonModify"));
			user = OperationsWithUsers.getOperationWithUsers().isRegisted(user);
		}
		if (request.getParameter("ButtonNoteDelete") != null) {
			Note note = new Note();
			note.setId(Integer.valueOf(request.getParameter("ButtonNoteDelete")));
			OperationWithNotes.getOperationWithNotes().deleteById(note);
		}
	%>
    <form name="AddModify" method="post">
        <table cellpadding="3pt">
            <tr>
                <td>User Name :</td>
                <td><input type="text" name="userName" value="<%=user.getName()%>" 
                size="30" /></td>
            </tr>
            <tr>
                <td>User Phone :</td>
                <td><input type="text" name="userPhone" value="<%=user.getPhone()%>" 
                size="30" /></td>
            </tr>
            <tr>
                <td>User Access level :</td>
                <td><input type="text" name="userAccess_level" 
                value="<%if (user.getAccess_level() == null) out.print(""); 
                else out.print(user.getAccess_level());%>" size="30" /></td>
            </tr>
            <tr>
                <td>Password :</td>
                <td><input type="password" name="userPwd" size="30" /></td>
            </tr>
        </table>
        <p />
        <input type="hidden" name="UserId" value="<%=user.getId()%>" />
        <input type="hidden" name="Save" value="1" />
        <input type="submit" value="Save" />
    </form>
    <form action="LogOutServlet" method="post">
		<input type="submit" value="Logout" >
	</form>
	<a href="StartPage.html"><button>Main page</button></a>
	<% if (request.getParameter("UserAdd") == null) {
			out.println("<h1>Notes</h1>");
			ArrayList<Note> notes = user.getAllNotes();
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
					" Card name <button value=\"" + notes.get(i).getCard().getId() +
					"\" name=\"ButtonNoteOpen\" >" + notes.get(i).getCard().getName() 
					+ "</button></h2>" +
					"<textarea cols=\"40\" rows=\"3\" name=\"userNote\">" + 
							notes.get(i).getHidden_note() + "</textarea>" + 
					"<br><button value=\"" + notes.get(i).getId() + 
					"\" name=\"ButtonNoteSave\" >Save" +
					"</button><button value=\"" + notes.get(i).getId() + 
					"\" name=\"ButtonNoteDelete\" >Delete" +
					"</button><button value=\"" + notes.get(i).getId() + 
					"\" name=\"ButtonNoteCencel\" >Cencel</button>" +
					"Hide note <input type=\"text\" name=\"userNoteIsHide\" size=\"1\" value=\"" + 
					notes.get(i).getHide() + "\">Date <input type=\"text\" name=\"userNoteDate\"" +
					"size=\"5\" value=\"" + notes.get(i).getDate().toString() + "\"></form><br>");
			}
	    }
	%>
</body>
</html>