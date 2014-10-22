package testJUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import hibernate.Card;
import hibernate.Gender;
import hibernate.Users;

import java.util.ArrayList;
import java.util.List;

import logic.OperationsWithCard;
import logic.OperationsWithUsers;

import org.junit.Test;


public class Tests {

	ArrayList<Users> users;
	ArrayList<Card> cards;
	
	public Tests() {
		// TODO Auto-generated constructor stub
		users = new ArrayList<>();
		users.add(new Users("Porky1",(short) 0, "134321223", "1111"));
		users.add(new Users("Porky2",(short) 1, "132135223", "0000"));
		users.add(new Users("Porky3",(short) 2, "134633432", "jsfn"));
		users.add(new Users("Porky4",(short) 2, "435446333", "sdfg"));
		users.add(new Users("Porky5",(short) 1, "123456789", "dsgd"));
		users.add(new Users("Porky6",(short) 0, "444325252", "dsfd"));
		users.add(new Users("Porky7",(short) 0, "657345434", "dsfs"));
		
		cards = new ArrayList<>();
		for(int i = 0;i < 7; i++)
			cards.add(new Card("Card" + String.valueOf(i), 30 + i,
					i % 2 == 0 ? Gender.female : Gender.male));
	}
	@Test
	public void testRegisterCard() {
		OperationsWithUsers.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUsers.register(users.get(i)))
				fail();
		for(int i = 0; i < users.size(); i++)
			if(OperationsWithUsers.isUserRegisted(users.get(i)) == null)
				fail();
	}

	@Test
	public void testDeleteUsers() {
		for(int i = 0; i < users.size(); i++)
			OperationsWithUsers.register(users.get(i));
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUsers.delete(users.get(i)))
				fail();
	}
	
	@Test
	public void testUsersFiltr() {
		OperationsWithUsers.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUsers.register(users.get(i)))
				fail();
		if(OperationsWithUsers.getAllUsersFiltr("Porky", 
				"",(short) 1,(short) 1).size() != 2)
			fail();
		if(OperationsWithUsers.getAllUsersFiltr("or", 
				"132135223",(short) 0,(short) 2).size() != 1)
			fail();
		if(OperationsWithUsers.getAllUsersFiltr("ork", 
				"",(short) 0,(short) 2).size() != 7)
			fail();
	}
	
	@Test
	public void testUpdateUser() {
		OperationsWithUsers.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUsers.register(users.get(i)))
				fail();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUsers.update(users.get(i)))
				fail();
	}
	
	@Test
	public void testGetUsers() {
		OperationsWithUsers.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!OperationsWithUsers.register(users.get(i)))
				fail();
		List<Users> usersP = OperationsWithUsers.getAllUsers();
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
		if(OperationsWithCard.getAllCardsFiltr("Card", null, 0, 40).size() != 7)
			fail();
		if(OperationsWithCard.getAllCardsFiltr("ard", 
				Gender.female, 0, 40).size() != 4)
			fail();
		if(OperationsWithCard.getAllCardsFiltr("ork", 
				null, 0, 35).size() != 0)
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
