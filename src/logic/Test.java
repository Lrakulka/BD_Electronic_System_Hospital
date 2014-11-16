package logic;


import hibernate.User;
import static logic.OperationsWithUsers.getOperationWithUsers;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getOperationWithUsers().register(new User("admin", (short) 2, "123456", "0000"));
		getOperationWithUsers().register(new User("Vaca", (short) 2, "126766", "1111"));
		getOperationWithUsers().register(new User("Dasha", (short) 0, "675456", "2222"));
		getOperationWithUsers().register(new User("Petr", (short) 1, "6584456", "3333"));
		getOperationWithUsers().register(new User("Masha", (short) 0, "325656", "0123"));
	}

}
