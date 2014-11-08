package testJUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import hibernate.Card;
import hibernate.Diagnos;
import hibernate.Disease;
import hibernate.Gender;
import hibernate.Note;
import hibernate.User;
import hibernateConnect.DatabaseConnect;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import logic.OperationsWithCard;
import logic.OperationsWithUser;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Tests {

	ArrayList<User> users;
	ArrayList<Card> cards;
	
	public Tests() {
		// TODO Auto-generated constructor stub
		users = new ArrayList<>();
		users.add(new User("Porky1",(short) 0, "134321223", "1111"));
		users.add(new User("Porky2",(short) 1, "132135223", "0000"));
		users.add(new User("Porky3",(short) 2, "134633432", "jsfn"));
		users.add(new User("Porky4",(short) 2, "435446333", "sdfg"));
		users.add(new User("Porky5",(short) 1, "123456789", "dsgd"));
		users.add(new User("Porky6",(short) 0, "444325252", "dsfd"));
		users.add(new User("Porky7",(short) 0, "657345434", "dsfs"));
		
		cards = new ArrayList<>();
		for(int i = 0;i < 7; i++)
			cards.add(new Card("Card" + String.valueOf(i), (short) (30 + i),
					i % 2 == 0 ? Gender.female : Gender.male, false));
	}
	@Test
	public void testRegisterCard() {
		OperationsWithUser.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUser.register(users.get(i)))
				fail();
		for(int i = 0; i < users.size(); i++)
			if(OperationsWithUser.isUserRegisted(users.get(i)) == null)
				fail();
	}

	@Test
	public void testDeleteUsers() {
		for(int i = 0; i < users.size(); i++)
			OperationsWithUser.register(users.get(i));
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUser.delete(users.get(i)))
				fail();
	}
	
	@Test
	public void testUsersFiltr() {
		OperationsWithUser.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUser.register(users.get(i)))
				fail();
		if(OperationsWithUser.getAllUsersFiltr("Porky", 
				"",(short) 1,(short) 1).size() != 2)
			fail();
		if(OperationsWithUser.getAllUsersFiltr("or", 
				"132135223",(short) 0,(short) 2).size() != 1)
			fail();
		if(OperationsWithUser.getAllUsersFiltr("ork", 
				"",(short) 0,(short) 2).size() != 7)
			fail();
	}
	
	@Test
	public void testUpdateUser() {
		OperationsWithUser.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUser.register(users.get(i)))
				fail();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUser.update(users.get(i)))
				fail();
	}
	
	@Test
	public void testGetUsers() {
		OperationsWithUser.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUser.register(users.get(i)))
				fail();
		List<User> usersP = OperationsWithUser.getAllUsers();
		for(int i = 0; i < users.size(); ++i)
			assertTrue(usersP.get(i).equals(users.get(i)));
	}
	
	@Test
	public void testRegisteCards() {
		OperationsWithCard.deleteAllCards();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithCard.register(cards.get(i)))
				fail();
		for(int i = 0; i < users.size(); i++)
			if(OperationsWithCard.isCardRegisted(cards.get(i)) == null)
				fail();
	}
	@Test
	public void testDeleteCards() {
		for(int i = 0; i < cards.size(); i++)
			OperationsWithCard.register(cards.get(i));
		for(int i = 0; i < cards.size(); i++)
			if(!OperationsWithCard.delete(cards.get(i)))
				fail();
	}
	
	@Test
	public void testCardFiltr() {
		OperationsWithCard.deleteAllCards();
		for(int i = 0; i < cards.size(); i++)
			if(!OperationsWithCard.register(cards.get(i)))
				fail();
		if(OperationsWithCard.getAllCardsFiltr("Card", null,(short) 0,(short) 40).size() != 7)
			fail();
		if(OperationsWithCard.getAllCardsFiltr("ard", 
				Gender.female,(short) 0,(short) 40).size() != 4)
			fail();
		if(OperationsWithCard.getAllCardsFiltr("ork", 
				null,(short) 0,(short) 35).size() != 0)
			fail();
	}
	
	@Test
	public void testUpdateCard() {
		OperationsWithCard.deleteAllCards();
		for(int i = 0; i < cards.size(); i++)
			if(!OperationsWithCard.register(cards.get(i)))
				fail();
		for(int i = 0; i < cards.size(); i++)
			if(!OperationsWithCard.update(cards.get(i)))
				fail();
	}
	
	@Test
	public void testGetCards() {
		OperationsWithCard.deleteAllCards();
		for(int i = 0; i < cards.size(); i++)
			if(!OperationsWithCard.register(cards.get(i)))
				fail();
		List<Card> cardsP = OperationsWithCard.getAllCards();
		for(int i = 0; i < cards.size(); ++i)
			assertTrue(cardsP.get(i).equals(cards.get(i)));
	}
	
	// I know, looks horrible.
	@Test
	public void testRelationship() {
		
	 	Session session = DatabaseConnect.getSessionFactory().openSession();
        session.beginTransaction();
        int size = 2;
        User[] user = new User[size];
        user[0] = new User("Svin Petr",(short) 0, "+43345676543", "qwerty");
        user[1] = new User("Svin Baca",(short) 0, "+97532642225", "ytrewq");
        Note[] note = new Note[size];
        note[0] = new Note(false, Date.valueOf("2000-11-01"), "This is test of hibernete" +
        		" relationship");
        note[1] = new Note(true, Date.valueOf("2012-11-04"), "This is test of hibernete" +
        		" relationship");
        Card card = new Card("Bacilev Andrey", (short) 28, Gender.male, false);
        hibernate.Session[] sessions = new hibernate.Session[size];
        sessions[0] = new hibernate.Session();
        sessions[1] = new hibernate.Session();
        sessions[0].setResult(true);
        sessions[1].setResult(false);
        Diagnos[] diagnos = new Diagnos[2];
        diagnos[0] = new Diagnos("U bolnogo otsustvuet mozg");
        diagnos[1] = new Diagnos("U bolnogo snova otsustvuet mozg");
        Disease[] disease = new Disease[2];
        disease[0] = new Disease("Net mozga.Netu golovnogo mozga");
        disease[1] = new Disease("Net mozga.Netu prodolgovatogo mozga");

        for(int i = 0; i < size; ++i) {
        	diagnos[i].setDisease(disease[i]);
        	sessions[i].setDiagnos(diagnos[i]);
        	sessions[i].setCard(card);
        	note[i].setCard(card);
        	note[i].setUser(user[i]);
        }
        
        session.save(card);
        for(int i = 0; i < size; ++i) {
        	session.save(user[i]);
        	session.save(note[i]);
        	session.save(disease[i]);
        	session.save(diagnos[i]);
        	session.save(sessions[i]);
        }
      
        session.getTransaction().commit();
        session.close();
		
		List<Card> cardsD = OperationsWithCard.getAllCards();
		Card cardD = cardsD.get(0);		        
        ArrayList<Note> notesD = cardD.getAllNotes();	
        notesD.sort(new Comparator<Note>() {

			@Override
			public int compare(Note arg0, Note arg1) {
				// TODO Auto-generated method stub
				if(arg0.getId() > arg1.getId())
					return 1;
				else return -1;
			}
        	
		});
        ArrayList<hibernate.Session> sessionsD = cardD.getAllSessions();
        sessionsD.sort(new Comparator<hibernate.Session>() {

			@Override
			public int compare(hibernate.Session arg0, hibernate.Session arg1) {
				// TODO Auto-generated method stub
				if(arg0.getId() > arg1.getId())
					return 1;
				else return -1;
			}
			
		});
        for(int i = 0; i < size; ++i) {        	
        	Note noteD = notesD.get(i);
        	User userD = noteD.getUser();
        	hibernate.Session sessionD = sessionsD.get(i);
        	Diagnos diagnosD = sessionD.getDiagnos();
        	Disease diseaseD = diagnosD.getDisease();
        	
        	assertTrue(user[i].equals(userD));
        	assertTrue(note[i].equals(noteD));
        	assertTrue(card.equals(cardD));
        	assertTrue(sessions[i].equals(sessionD));
        	assertTrue(diagnos[i].equals(diagnosD));
        	assertTrue(disease[i].equals(diseaseD));
        }
	}
	
	@Before
	@After
	public void cleanAllTables() {
		Session session = DatabaseConnect.getSessionFactory().openSession();
        session.beginTransaction();
        String tablesName[]  = {"card", "diagnosis", "diseases", "groups", "notes",
        		"session", "users"};
        session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for(int i = 0; i < tablesName.length; ++i) {
        	session.createSQLQuery("TRUNCATE " + tablesName[i]).executeUpdate();
        }
        session.createSQLQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        session.getTransaction().commit();
        session.close();
	}
}
