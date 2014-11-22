<%@ page import="hibernate.Gender"%>
<%@ page import="logic.OperationsWithCards"%>
<%@ page import="hibernate.Card"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>
        Таблиця карток
    </title>
</head> 
<body>
    <form action="LogOutServlet" method="post">
	<input type="submit" value="Вийти" >
	</form>
	<a href="StartPage.jsp"><button>Головна сторінка</button></a>
   <%  if (request.getParameter("ButtonDelete") != null) {
	    	Card card = new Card();
	    	card.setId(Integer.valueOf(request.getParameter("ButtonDelete")));
	    	OperationsWithCards.getOperationWithCard().deleteById(card);
    	}
   	   if (request.getParameter("ButtonModify") != null) {
			request.getSession().setAttribute("ButtonModify", 
					Integer.valueOf(request.getParameter("ButtonModify")));
			response.sendRedirect("ModifyCard.jsp");
		}
    %>
    <h1>Таблиця карток</h1>	
    <form name="CardListForm" method="post">	
    	<table border="1" cellpadding="8">	    	
	    	<tr>
	    		<%	if (request.getParameter("cardName") != null) {
	    				out.print("<th><input type=\"text\" name=\"cardName\" value=\"" + 
	    					request.getParameter("cardName") + "\" size=\"20\" /></th>");
	    				out.print("<th>Чоловік<input type=\"checkbox\" name=\"genderMan\" " + 
		    					"value=\"" + request.getParameter("cardGender") + 
		    					"\" size=\"1\" />");
	    				out.print("<input type=\"checkbox\" name=\"genderWoman\" " + 
		    					"value=\"" + request.getParameter("cardGender") + 
		    					"\" size=\"1\" />Жінка</th>");
	    				out.print("<th>Н:<input type=\"text\" name=\"cardLowAgeLevel\"" +
	    						" value=\"" + request.getParameter("cardLowAgeLevel") + 
		    					"\" size=\"1\" />");
	    				out.print(" В:<input type=\"text\" name=\"cardHightAgeLevel\" " + 
		    					"value=\"" + request.getParameter("cardHightAgeLevel") + 
	    					"\" size=\"1\" /></th>");
	    				out.print("<th>Так<input type=\"checkbox\" name=\"cardIsAgainTrue\" " + 
		    					"value=\"" + request.getParameter("cardIsAgain") + 
	    					"\" size=\"1\" />");
	    				out.print("<input type=\"checkbox\" name=\"cardIsAgainFalse\" " + 
		    					"value=\"" + request.getParameter("cardIsAgain") + 
	    					"\" size=\"1\" />Ні</th>");
	    			}
		    		else {
		    			out.println("<th><input type=\"text\" name=\"cardName\" size=" +
		    					"\"20\" /></th>");
		    			out.print("<th>Чоловік<input type=\"checkbox\" name=\"genderMan\" " + 
		    					"value=\"\" size=\"1\" />");
	    				out.print("<input type=\"checkbox\" name=\"genderWoman\" " + 
		    					"value=\"\" size=\"1\" />Жінка</th>");
		    			out.println("<th>Н:<input type=\"text\" name=\"cardLowAgeLevel\"" +
		    					" size=\"1\" value=\"0\"/>"); 
		    			out.println("В:<input type=\"text\" name=\"cardHightAgeLevel\"" +
		    					" size=\"1\" value=\"200\"/></th>");
		    			out.print("<th>Так<input type=\"checkbox\" name=\"cardIsAgainTrue\" " + 
		    					"value=\"\" size=\"1\" />");
	    				out.print("<input type=\"checkbox\" name=\"cardIsAgainFalse\" " + 
		    					"value=\"\" size=\"1\" />Ні</th>");
		    		}
    			%>
	        <th><input type="submit" name="submit" value="Використати фільтр" size="15" /></th>
	        </tr>    
	        <tr>
	            <th>Ім'я пацієнта</th>
	            <th>Стать пацієнта</th>
	            <th>Вік пацієнта</th>
	            <th>Вперше</th>
	        </tr> 
        </form>      
       	<form name="ModifyCard" method="post">
            <% 	ArrayList<Card> cards;
            	if (request.getParameter("cardName") != null) {
            		cards = OperationsWithCards.getOperationWithCard().
            			getAllCardsFiltr(request.getParameter("cardName"), 
           					request.getParameter("genderMan") == null ?
               						request.getParameter("genderWoman") == null ?
               								null : Gender.female : request.getParameter(
               						"genderWoman") == null ? Gender.male : null, 
            				Short.valueOf(request.getParameter("cardLowAgeLevel")), 
            				Short.valueOf(request.getParameter("cardHightAgeLevel")),
            				request.getParameter("cardIsAgainFalse") == null ?
            						request.getParameter("cardIsAgainTrue") == null ?
            								null : true : request.getParameter(
            						"cardIsAgainTrue") == null ? false : null);
            	}
            	else {
            		cards = OperationsWithCards.getOperationWithCard().
            				getAllObj();
            	}
            	for(int i = 0; i < cards.size(); ++i){
                out.println("<tr>");
                out.println("<td>" + cards.get(i).getName() + "</td>");
                out.println("<td>" + cards.get(i).getSex().getGenderName() + "</td>");
                out.println("<td>" + cards.get(i).getAge() + "</td>");
                out.println("<td>" + cards.get(i).getIsAgain() + "</td>");
                out.println("<td><button name=\"ButtonDelete\" value=\"" +
               		cards.get(i).getId() + "\">Видалити</button>" +
               		"<button name=\"ButtonModify\" value=\"" +
                       		cards.get(i).getId() + "\">Редагувати</button></td>");  
                out.println("</tr>");
            } %>
        </form>
    </table>
</body>
</html>