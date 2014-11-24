package hibernate;

/**Допоміжний клас-перечислення для встановлення статі*/
public enum Gender {
	male,
	female;
	
	/**@return строку, яка є еквівалентом значення поточного об'єкту*/
	public String getGenderName() {  
		  switch (this) {  
		   case male : return "Man";  
		   case female : return "Woman";  
		   default : return null;  
		  }  
		 }  
	/**@param p переданий об'єкт
	 * @return строку, яка є еквівалентом значенню переданого об'єкту*/
	public static Gender getValue(String p) {
		switch (p) {  
		   case "Man" : return Gender.male;  
		   case "Woman" : return Gender.female;  
		   default : return null; 
		}
	}
};
