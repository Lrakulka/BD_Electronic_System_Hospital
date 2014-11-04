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
import java.util.List;

import logic.OperationsWithCard;
import logic.OperationsWithUser;

import org.hibernate.Session;
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
		{
		 	Session session = DatabaseConnect.getSessionFactory().openSession();
	        session.beginTransaction();
	        
	       // session.createQuery("set FOREIGN_KEY_CHECKS = 0").executeUpdate();
	        session.createQuery("DELETE FROM Card").executeUpdate();
	        session.createQuery("DELETE FROM Diagnos").executeUpdate();
	        session.createQuery("DELETE FROM Disease").executeUpdate();
	        session.createQuery("DELETE FROM Group").executeUpdate();
	        session.createQuery("DELETE FROM Note").executeUpdate();
	        session.createQuery("DELETE FROM Session").executeUpdate();
	        session.createQuery("DELETE FROM User").executeUpdate();
	        
	        User user1 = new User("Svin Petr",(short) 0, "+43345676543", "qwerty");
	        User user2 = new User("Svin Baca",(short) 0, "+97532642225", "ytrewq");
	        Note note1 = new Note(false, new Date(1000), "This is test of hibernete" +
	        		" relationship");
	        Note note2 = new Note(true, new Date(1234), "This is test of hibernete" +
	        		" relationship");
	        Card card = new Card("Bacilev Andrey", (short) 28, Gender.male, false);
	        hibernate.Session session1 = new hibernate.Session();
	        hibernate.Session session2 = new hibernate.Session();
	        session1.setResult(true);
	        session2.setResult(false);
	        Diagnos diagnos1 = new Diagnos("U bolnogo otsustvuet mozg");
	        Diagnos diagnos2 = new Diagnos("U bolnogo snova otsustvuet mozg");
	        Disease disease1 = new Disease("Net mozga.Netu golovnogo mozga");
	        Disease disease2 = new Disease("Net mozga.Netu prodolgovatogo mozga");

	        diagnos1.setDisease(disease1);
	        diagnos2.setDisease(disease2);
	        session1.setDiagnos(diagnos1);
	        session2.setDiagnos(diagnos2);
	        session1.setCard(card);
	        session2.setCard(card);
	        note1.setCard(card);
	        note2.setCard(card);
	        note1.setUser(user1);
	        note2.setUser(user2);
	        
	        session.save(card);
	        session.save(user1);
	        session.save(user2);
	        session.save(note1);
	        session.save(note2);
	        session.save(disease1);
	        session.save(disease2);
	        session.save(diagnos1);
	        session.save(diagnos2);
	        session.save(session1);
	        session.save(session2);
	      
	        session.getTransaction().commit();
	        session.close();
		}
		List<Card> cards = OperationsWithCard.getAllCards();
        for(int i = 0; i < 2; ++i) {
        	Card card = cards.get(0);		        
	        ArrayList<Note> notes = card.getAllNotes();	
	        ArrayList<hibernate.Session> sessions = card.getAllSessions();
        	Note note = notes.get(i);
        	User user = note.getUser();
        	hibernate.Session session = sessions.get(i);
        	Diagnos diagnos = session.getDiagnos();
        	Disease disease = diagnos.getDisease();
        	String history = new String(user.getName() + " " + user.getPhone() +
        			" " + user.getPwd() + " " + user.getAccess_level() + ". " +
        			note.getHidden_note() + " " + note.getDate() + " " +
        			note.getHide() + ". " + card.getName() + " " + 
        			card.getAge() + " " + card.getSex() + " " + card.getIsAgain() +
        			". " + session.getResult() + ". " + diagnos.getDescription() + 
        			". " + disease.getName());
        	if(!history.equals("Svin Baca +97532642225 8c32e548bc4fbfc5dc53c89a36c812" +
        			" 0. This is test of hibernete relationship 1970-01-01 true. " +
        			"Bacilev Andrey 28 male false. true. U bolnogo otsustvuet mozg. " +
        			"Net mozga.Netu golovnogo mozga"))
        		fail();
        }
	}
}
