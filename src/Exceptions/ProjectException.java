package exceptions;

/**Клас для виведення повідомлень про виключення*/
public class ProjectException extends Exception {
	
	/**Індефікатор класу виключень*/
	private static final long serialVersionUID = 1L;
	
	/**Супровідне повідомлення до виключення*/
	private String e;
	
	/**Створення об'єкту класу із вказанням супровідного повідомлення
	 * @param e повідомлення про виключення*/
	public ProjectException(String e) {
		this.e = e;
	}
	
	/**Виведення повідомлення*/
	public void showMessage(){
		System.out.print(e);
	}
	
	/**Виведення повідомлення та логу виключення
	 * @param message повідомлення про виключення
	 * @param e лог виключення*/
	public static void showMessage(String message, Exception e) {
		System.out.print(message + "Error log \n");
		e.printStackTrace();
	}
}
