package logic;

import hibernate.Users;

public class AuthorizeUser {
	private static Users authorizeUser = null;
	
	public static boolean authorize(Users user) {
		if (((user = OperationsWithUsers.isUserRegisted(user)) != null)
				&& (user.getAccess_level() == 2)){   // User with admin rights
				authorizeUser = user;
				return true;
			}
		else return false;
	}
	
	public static Users getAuthorizeUser() {
		return authorizeUser;
	}
	
	public static boolean isUserAuthorize() {
		if (authorizeUser == null)
			return false;
		else return true;
	}
}
