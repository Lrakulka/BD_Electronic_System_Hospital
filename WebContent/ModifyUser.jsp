<%@page import="logic.OperationWithNotes"%>
<%@ page import="hibernate.User"%>
<%@ page import="hibernate.Note"%>
<%@ page import="logic.OperationsWithUsers"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Редагування користовача</title>
</head>
<body>
	<%!	User user;%>
	<%	request.setCharacterEncoding("utf8");
		user = new User("", null, "", "");
	 	if  
	 	(request.getParameter("ButtonCardOpen") != null) {
			request.getSession().setAttribute("ButtonModify", 
					Integer.valueOf(request.getParameter("ButtonCardOpen")));
			response.sendRedirect("ModifyCard.jsp");
		}
		if (request.getParameter("Save") != null) {
			boolean status;
			user = new User(request.getParameter("userName"), 
					Short.valueOf(request.getParameter("userAccess_level")),
					request.getParameter("userPhone"), request.getParameter("userPwd"));
			if (!request.getParameter("UserId").equals("null")) {
				user.setId(Integer.valueOf(request.getParameter("UserId")));
				request.setAttribute("ButtonModify", Integer.valueOf(request.getParameter("UserId")));
				status = OperationsWithUsers.getOperationWithUsers().update(user);
			} else status = OperationsWithUsers.getOperationWithUsers().register(user);
			if( status) {
				out.print("<h1>Успішно</h1>");
				request.getSession().setAttribute("ButtonModify", user.getId());
			}
			else out.print("<h1>Помилка</h1>");
		}
		if( request.getParameter("UserAdd") != null) {
			out.print("<h1>Додати нового користувача</h1>");
		}
		else {
			System.out.println(request.getParameter("ButtonModify"));
			if (request.getParameter("ButtonModify") != null ||
					request.getSession().getAttribute("ButtonModify") != null) {
				if (request.getParameter("ButtonModify") != null)
					request.setAttribute("ButtonModify", 
							Integer.valueOf(request.getParameter("ButtonModify")));
				else request.setAttribute("ButtonModify", 
						request.getSession().getAttribute("ButtonModify"));
				request.getSession().setAttribute("ButtonModify", 
						request.getAttribute("ButtonModify"));
			}
			out.print("<h1>Редагувати користувача</h1>");
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
                <td>Ім'я користувача :</td>
                <td><input type="text" name="userName" value="<%=user.getName()%>" 
                size="30" /></td>
            </tr>
            <tr>
                <td>Телефон користувача :</td>
                <td><input type="text" name="userPhone" value="<%=user.getPhone()%>" 
                size="30" /></td>
            </tr>
            <tr>
                <td>Рівень доступу користувача :</td>
                <td><input type="text" name="userAccess_level" 
                value="<%if (user.getAccess_level() == null) out.print(""); 
                else out.print(user.getAccess_level());%>" size="30" /></td>
            </tr>
            <tr>
                <td>Пароль користувача :</td>
                <td><input type="password" name="userPwd" size="30" /></td>
            </tr>
        </table>
        <p />
        <input type="hidden" name="UserId" value="<%=user.getId()%>" />
        <input type="hidden" name="Save" value="1" />
        <input type="submit" value="Зберегти" />
    </form>
    <form action="LogOutServlet" method="post">
		<input type="submit" value="Вийти" >
	</form>
	<a href="StartPage.jsp"><button>Головна сторінка</button></a>
	<% if (request.getParameter("UserAdd") == null) {
			out.println("<h1>Нотатки</h1>");
			ArrayList<Note> notes = user.getAllNotes();
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
					" Ім'я картки <button value=\"" + notes.get(i).getCard().getId() +
					"\" name=\"ButtonCardOpen\" >" + notes.get(i).getCard().getName() 
					+ "</button></h2>" +
					"<textarea cols=\"40\" rows=\"3\" name=\"userNote\">" + 
							notes.get(i).getHidden_note() + "</textarea>" + 
					"<br><button value=\"" + notes.get(i).getId() + 
					"\" name=\"ButtonNoteSave\" >Зберегти" +
					"</button><button value=\"" + notes.get(i).getId() + 
					"\" name=\"ButtonNoteDelete\" >Видаоити" +
					"</button><button value=\"" + notes.get(i).getId() + 
					"\" name=\"ButtonNoteCencel\" >Відмінити</button>" +
					"Hide note <input type=\"checkbox\" name=\"userNoteIsHide\" " + 
					"value=\"" + notes.get(i).getHide() + 
					"\" size=\"1\"" + (notes.get(i).getHide().equals(true) ? 
					"checked" : "") + "/>Час <input type=\"text\" name=\"userNoteDate\"" +
					"size=\"5\" value=\"" + notes.get(i).getDate().toString() + "\">" + 
					"<input type=\"hidden\" name=\"ButtonModify\" value=\"" + 
					user.getId() + "\"></form><br>");
			}
	    }
	%>
</body>
</html>