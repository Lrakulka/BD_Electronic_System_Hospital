package tests;

import static logic.OperationsWithCards.getOperationWithCard;
import static logic.OperationsWithUsers.getOperationWithUsers;
import hibernate.Card;
import hibernate.Gender;
import hibernate.User;

/**�������� ���� ��� ����� ������������� �������*/
public class Test {

	/**����� �������� ���� �����*/
	public static void main(String[] args) {
		Tests testRelat = new Tests();
		testRelat.cleanAllTables();
		testRelat.testRelationship();
		getOperationWithUsers().register(new User("����������� ������ ����������", (short) 2, "+38095230294", "0000"));
		getOperationWithUsers().register(new User("������� ����� ��������", (short) 2, "+38095345961", "1111"));
		getOperationWithUsers().register(new User("��������� ���� ³�������", (short) 0, "+38092241526", "2222"));
		getOperationWithUsers().register(new User("������ ����� ���������", (short) 1, "+38034536367", "3333"));
		getOperationWithUsers().register(new User("ֳ��� ���� ���������", (short) 0, "+380889214546", "4444"));
		for(int i = 0;i < 7; i++)
			getOperationWithCard().register(new Card("����� " + String.valueOf(i), (short) (30 + i),
					i % 2 == 0 ? Gender.female : Gender.male, false));
	}

}
