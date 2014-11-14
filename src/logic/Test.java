package logic;


import hibernate.User;
import hibernateConnect.DatabaseConnect;

import org.hibernate.Session;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OperationsWithUsers.getOperationWithUsers().
		register(new User("admin", (short) 2, "123456", "0000"));
	}

}
