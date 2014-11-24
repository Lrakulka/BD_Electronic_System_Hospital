package hibernate;

/**����, �� ������ ������ ���� ��� ��� �������*/
public abstract class CommonField {
	/**����������� � �������*/	
	private Integer id;

	/**@return ����������� ����� � �������*/
	public Integer getId() {
		return id;
	}

	/**@param id ���������� ����������� ��� ����� � �������*/
	public void setId(Integer id) {
		this.id = id;
	}  
	
	/**��������� ���-���� ������ ��'����
	 * @return ������� ���� �����, ��� � ���-�����*/
	abstract public int hashCode();
	
	/**������� ��� �������
	 * @param obj ������ � ���� ����� ��������
	 * @return true ���� ��� ��� false ���� �*/
	abstract public boolean equals(Object obj);
}
