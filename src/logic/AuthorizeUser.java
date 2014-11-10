package logic;

import hibernate.User;

public class AuthorizeUser {
	private static User authorizeUser = null;
	
	public static boolean authorizeUser(String name, String pwd) {
		User user = new User(name, null, "", pwd);
		if (((user = OperationsWithUsers.getOperationWithUsers().isRegisted(user)) != 
				null) && (user.getAccess_level() == 2)){   // User with admin rights
				authorizeUser = user;
				return true;
			}
		else return false;
	}
	
	public static User getAuthorizeUser() {
		return authorizeUser;
	}
	
	public static boolean isAuthorizeUser() {
		if (authorizeUser == null)
			return false;
		else return true;
	}
}
