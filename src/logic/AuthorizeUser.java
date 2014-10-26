package logic;

import hibernate.User;

public class AuthorizeUser {
	private static User authorizeUser = null;
	
	public static boolean authorize(User user) {
		if (((user = OperationsWithUsers.isUserRegisted(user)) != null)
				&& (user.getAccess_level() == 2)){   // User with admin rights
				authorizeUser = user;
				return true;
			}
		else return false;
	}
	
	public static User getAuthorizeUser() {
		return authorizeUser;
	}
	
	public static boolean isUserAuthorize() {
		if (authorizeUser == null)
			return false;
		else return true;
	}
}
