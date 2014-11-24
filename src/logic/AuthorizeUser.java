package logic;

import hibernate.User;

/**Клас призначений для авторизації користувача в системі*/
public class AuthorizeUser {
	/**Авторизовани користувач*/
	private static User authorizeUser = null;
	
	/**Метод виконує процедуру авторизації
	 * @param name ім'я користувача
	 * @param pwd пароль користувача
	 * @return повертає true якщо користувач пройшов авторизацію, повертає false якщо ні*/
	public static boolean authorizeUser(String name, String pwd) {
		User user = new User(name, null, "", pwd);
		if (((user = OperationsWithUsers.getOperationWithUsers().isRegisted(user)) != 
				null) && (user.getAccess_level() == 2)){   // User with admin rights
				authorizeUser = user;
				return true;
			}
		else return false;
	}
	
	/**@return повертає об'єкт авторизованого користувача*/
	public static User getAuthorizeUser() {
		return authorizeUser;
	}
	
	/**@return повертає true якщо користувач пройшов авторизацію, інекше false*/
	public static boolean isAuthorizeUser() {
		if (authorizeUser == null)
			return false;
		else return true;
	}
	
	/**Метод встановлює, що користувач вийшов із сиситеми*/
	public static void setUserLogOut() {
		authorizeUser = null;
	}
}
