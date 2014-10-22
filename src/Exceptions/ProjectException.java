package exceptions;

public class ProjectException extends Exception {
	private String e;
	public ProjectException(String e) {
		this.e = e;
	}
	public static void sentExceptionMassege(String e){
		System.out.print(e);
	}
}
