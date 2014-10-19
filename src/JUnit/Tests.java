package JUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import logic.DatabaseAdminOperations;

import org.junit.Test;

import Hibernate.Users;

public class Tests {

	ArrayList<Users> users;
	
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
	}
	@Test
	public void testRegister() {
		DatabaseAdminOperations.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!DatabaseAdminOperations.register(users.get(i)))
				fail();
		for(int i = 0; i < users.size(); i++)
			if(DatabaseAdminOperations.isUserRegisted(users.get(i)) == null)
				fail();
	}

	@Test
	public void testDeleteUsers() {
		for(int i = 0; i < users.size(); i++)
			DatabaseAdminOperations.register(users.get(i));
		for(int i = 0; i < users.size(); i++)
			if(!DatabaseAdminOperations.delete(users.get(i)))
				fail();
	}
	
	@Test
	public void testFiltr() {
		DatabaseAdminOperations.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!DatabaseAdminOperations.register(users.get(i)))
				fail();
		if(DatabaseAdminOperations.getAllUsersFiltr("Porky", 
				"",(short) 1,(short) 1).size() != 2)
			fail();
		if(DatabaseAdminOperations.getAllUsersFiltr("or", 
				"132135223",(short) 0,(short) 2).size() != 1)
			fail();
		if(DatabaseAdminOperations.getAllUsersFiltr("ork", 
				"",(short) 0,(short) 2).size() != 7)
			fail();
	}
	
	@Test
	public void testUpdate() {
		DatabaseAdminOperations.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!DatabaseAdminOperations.register(users.get(i)))
				fail();
		for(int i = 0; i < users.size(); i++)
			if(!DatabaseAdminOperations.update(users.get(i)))
				fail();
	}
	
	@Test
	public void testGetUsers() {
		DatabaseAdminOperations.deleteAllUsers();
		for(int i = 0; i < users.size(); i++)
			if(!DatabaseAdminOperations.register(users.get(i)))
				fail();
		List<Users> usersP = DatabaseAdminOperations.getAllUsers();
		for(int i = 0; i < users.size(); ++i)
			assertTrue(usersP.get(i).equals(users.get(i)));
	}
}
