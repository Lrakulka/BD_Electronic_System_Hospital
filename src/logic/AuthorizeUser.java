package logic;

import hibernate.User;

/**���� ����������� ��� ����������� ����������� � ������*/
public class AuthorizeUser {
	/**������������ ����������*/
	private static User authorizeUser = null;
	
	/**����� ������ ��������� �����������
	 * @param name ��'� �����������
	 * @param pwd ������ �����������
	 * @return ������� true ���� ���������� ������� �����������, ������� false ���� �*/
	public static boolean authorizeUser(String name, String pwd) {
		User user = new User(name, null, "", pwd);
		if (((user = OperationsWithUsers.getOperationWithUsers().isRegisted(user)) != 
				null) && (user.getAccess_level() == 2)){   // User with admin rights
				authorizeUser = user;
				return true;
			}
		else return false;
	}
	
	/**@return ������� ��'��� �������������� �����������*/
	public static User getAuthorizeUser() {
		return authorizeUser;
	}
	
	/**@return ������� true ���� ���������� ������� �����������, ������ false*/
	public static boolean isAuthorizeUser() {
		if (authorizeUser == null)
			return false;
		else return true;
	}
	
	/**����� ����������, �� ���������� ������ �� ��������*/
	public static void setUserLogOut() {
		authorizeUser = null;
	}
}
