package logic;


import java.util.ArrayList;

import testJUnit.Tests;
import hibernate.Card;
import hibernate.Gender;
import hibernate.User;
import static logic.OperationsWithUsers.getOperationWithUsers;
import static logic.OperationsWithCards.getOperationWithCard;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tests testRelat = new Tests();
		testRelat.cleanAllTables();
		testRelat.testRelationship();
		getOperationWithUsers().register(new User("admin", (short) 2, "123456", "0000"));
		getOperationWithUsers().register(new User("Vaca", (short) 2, "126766", "1111"));
		getOperationWithUsers().register(new User("Dasha", (short) 0, "675456", "2222"));
		getOperationWithUsers().register(new User("Petr", (short) 1, "6584456", "3333"));
		getOperationWithUsers().register(new User("Masha", (short) 0, "325656", "0123"));
		for(int i = 0;i < 7; i++)
			getOperationWithCard().register(new Card("Card" + String.valueOf(i), (short) (30 + i),
					i % 2 == 0 ? Gender.female : Gender.male, false));
	}

}
