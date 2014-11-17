package hibernate;

public enum Gender {
	male,
	female;
	public String getGenderName() {  
		  switch (this) {  
		   case male : return "Man";  
		   case female : return "Woman";  
		   default : return null;  
		  }  
		 }  
	
	public static Gender getValue(String p) {
		switch (p) {  
		   case "Man" : return Gender.male;  
		   case "Woman" : return Gender.female;  
		   default : return null; 
		}
	}
};
