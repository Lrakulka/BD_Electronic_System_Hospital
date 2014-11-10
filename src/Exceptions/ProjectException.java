package exceptions;

public class ProjectException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String e;
	public ProjectException(String e) {
		this.e = e;
	}
	public static void sentExceptionMassege(String e){
		System.out.print(e);
	}
	
	public static void showMessage(String message, Exception e) {
		
	}
}
