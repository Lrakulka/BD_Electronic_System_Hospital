package exceptions;

/**���� ��� ��������� ���������� ��� ����������*/
public class ProjectException extends Exception {
	
	/**����������� ����� ���������*/
	private static final long serialVersionUID = 1L;
	
	/**��������� ����������� �� ����������*/
	private String e;
	
	/**��������� ��'���� ����� �� ��������� ����������� �����������
	 * @param e ����������� ��� ����������*/
	public ProjectException(String e) {
		this.e = e;
	}
	
	/**��������� �����������*/
	public void showMessage(){
		System.out.print(e);
	}
	
	/**��������� ����������� �� ���� ����������
	 * @param message ����������� ��� ����������
	 * @param e ��� ����������*/
	public static void showMessage(String message, Exception e) {
		System.out.print(message + "Error log \n");
		e.printStackTrace();
	}
}
