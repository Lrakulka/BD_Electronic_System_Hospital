package logic;

import java.util.List;

import Hibernate.Users;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Users user = new Users();
		user.setAccess_level((short) 1);
		user.setName("A");
		user.setPhone("123253463");
		user.setPwd("00000");
		//DatabaseAdminOperations.register(user);
		//DatabaseAdminOperations.delete(user);
		DatabaseAdminOperations.deleteAllUsers();
		List <Users> users = DatabaseAdminOperations.getAllUsers();
		for(Users curUser : users){
			System.out.println("Name=" + curUser.getName() + " Pwd=" +
					curUser.getPwd() + " Phone=" + curUser.getPhone() + " Eccess_level=" +
					curUser.getAccess_level().toString() + " Id=" + curUser.getId());
		}		
	}

}
