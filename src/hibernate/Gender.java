package hibernate;

public enum Gender {
	male,
	female;
	public String getGenderName(){  
		  switch (this){  
		   case male : return "Man";  
		   case female : return "Woman";  
		   default : return null;  
		  }  
		 }  
};
