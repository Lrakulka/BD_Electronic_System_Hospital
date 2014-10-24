package testJUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import hibernate.Card;
import hibernate.Gender;
import hibernate.Note;
import hibernate.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.OperationsWithCard;
import logic.OperationsWithUser;

import org.junit.Test;


public class Tests {

	ArrayList<User> users;
	ArrayList<Card> cards;
	ArrayList<Note> notes;
	
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
		if(OperationsWithCard.getAllCardsFiltr("Card", null, (short) 0, 
				(short) 40).size() != 7)
			fail();
		if(OperationsWithCard.getAllCardsFiltr("ard", 
				Gender.female, (short) 0, (short) 40).size() != 4)
			fail();
		if(OperationsWithCard.getAllCardsFiltr("ork", 
				null, (short) 0, (short) 35).size() != 0)
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

}
