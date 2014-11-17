<%@ page import="hibernate.Gender"%>
<%@ page import="logic.OperationsWithCards"%>
<%@ page import="hibernate.Card"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html; charset=windows-1251" %>
<html>
<head>
    <title>
        Таблиця карток
    </title>
</head> 
<body>
    <form action="LogOutServlet" method="post">
	<input type="submit" value="Logout" >
	</form>
	<a href="StartPage.html"><button>Main page</button></a>
   <%  if (request.getParameter("ButtonDelete") != null) {
	    	Card card = new Card();
	    	card.setId(Integer.valueOf(request.getParameter("ButtonDelete")));
	    	OperationsWithCards.getOperationWithCard().deleteById(card);
    	}
    %>
    <h1>List of Cards</h1>		
    <table border="1" cellpadding="8">	    	
		<form name="CardListForm" method="post">
	    	<tr>
	    		<%	if (request.getParameter("cardName") != null) {
	    				out.print("<th><input type=\"text\" name=\"cardName\" value=\"" + 
	    					request.getParameter("cardName") + "\" size=\"20\" /></th>");
	    				out.print("<th><input type=\"text\" name=\"cardGender\" value=\"" + 
	    					request.getParameter("cardGender") + "\"size=\"20\" /></th>");
	    				out.print("<th>L:<input type=\"text\" name=\"cardLowAgeLevel\"" +
	    						" value=\"" + request.getParameter("cardLowAgeLevel") + 
		    					"\" size=\"1\" />");
	    				out.print(" H:<input type=\"text\" name=\"cardHightAgeLevel\" " + 
		    					"value=\"" + request.getParameter("cardHightAgeLevel") + 
	    					"\" size=\"1\" /></th>");
	    				out.print("<th><input type=\"text\" name=\"cardIsAgain\" " + 
		    					"value=\"" + request.getParameter("cardIsAgain") + 
	    					"\" size=\"1\" /></th>");
	    			}
		    		else {
		    			out.println("<th><input type=\"text\" name=\"cardName\" size=" +
		    					"\"20\" /></th>");
		    			out.println("<th><input type=\"text\" name=\"cardGender\" size=" +
		    					"\"20\" /></th>");
		    			out.println("<th>L:<input type=\"text\" name=\"cardLowAgeLevel\"" +
		    					" size=\"1\" value=\"0\"/>"); 
		    			out.println("H:<input type=\"text\" name=\"cardHightAgeLevel\"" +
		    					" size=\"1\" value=\"200\"/></th>");
		    			out.println("<th><input type=\"text\" name=\"cardIsAgain\"" +
		    					" size=\"1\" value=\"\"/></th>");
		    		}
    			%>
	            <th><input type="submit" name="submit" value="Use filters" size="15" /></th>
	        </tr> 
        </form>    
        <tr>
            <th>Patient Name</th>
            <th>Patient Gender</th>
            <th>Patient Age</th>
            <th>Patient IsAgain</th>
        </tr>      
       	<form action="ModifyCard" method="post">
            <% 	ArrayList<Card> cards;
            	if (request.getParameter("cardName") != null) {
            		cards = OperationsWithCards.getOperationWithCard().
            			getAllCardsFiltr(request.getParameter("cardName"), 
            				Gender.getValue(request.getParameter("cardGender")), 
            				Short.valueOf(request.getParameter("cardLowAgeLevel")), 
            				Short.valueOf(request.getParameter("cardHightAgeLevel")),
            				Boolean.valueOf(request.getParameter("cardIsAgain")));
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
               		cards.get(i).getId() + "\">Delete</button>" +
               		"<input type=\"hidden\" name=\"ButtonModify\"" +
               		" value=\"" + cards.get(i).getId() +
               		"\"><input type=\"submit\" value=\"Modify\"></td>");  
                out.println("</tr>");
            } %>
        </form>
    </table>
</body>
</html>