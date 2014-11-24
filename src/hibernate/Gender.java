package hibernate;

/**��������� ����-������������ ��� ������������ ����*/
public enum Gender {
	male,
	female;
	
	/**@return ������, ��� � ����������� �������� ��������� ��'����*/
	public String getGenderName() {  
		  switch (this) {  
		   case male : return "Man";  
		   case female : return "Woman";  
		   default : return null;  
		  }  
		 }  
	/**@param p ��������� ��'���
	 * @return ������, ��� � ����������� �������� ���������� ��'����*/
	public static Gender getValue(String p) {
		switch (p) {  
		   case "Man" : return Gender.male;  
		   case "Woman" : return Gender.female;  
		   default : return null; 
		}
	}
};
