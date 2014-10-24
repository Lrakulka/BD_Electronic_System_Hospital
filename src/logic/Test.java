package logic;

import hibernate.Note;
import hibernate.User;

import java.sql.Date;
import java.util.List;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setAccess_level((short) 1);
		user.setName("A");
		user.setPhone("123253463");
		user.setPwd("00000");
		//DatabaseAdminOperations.register(user);
		//DatabaseAdminOperations.delete(user);
		//OperationsWithUser.deleteAllUsers();
		
		List <User> users = OperationsWithUser.getAllUsers();
		for(User curUser : users){
			System.out.println("Name=" + curUser.getName() + " Pwd=" +
					curUser.getPwd() + " Phone=" + curUser.getPhone() + " Eccess_level=" +
					curUser.getAccess_level().toString() + " Id=" + curUser.getId());
		}	
		OperationWithNote.register(new Note(0, 0, false, "qwe", new Date(100)));
		OperationWithNote.register(new Note(0, 1, false, "qwe", new Date(100)));
		System.out.println(users.get(0).getNotes().toString());
	}

}
