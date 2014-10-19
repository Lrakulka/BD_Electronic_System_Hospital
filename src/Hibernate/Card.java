package Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.hibernate.annotations.GenericGenerator;

enum Gender {
		MALE,
		FEMALE;
		public String getGenderName(){  
			  switch (this){  
			   case MALE : return "Man";  
			   case FEMALE : return "Woman";  
			   default : return null;  
			  }  
			 }  
};

@Entity
@Table(name="card")
public class Card {
	private Integer id;
	private String name;
	private Integer age;	
	private Gender sex;
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
	public Integer getId() {
		return id;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	@Column(name="age")
	public Integer getAge() {
		return age;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="sex")  
	public Gender getSex() {
		return sex;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setSex(Gender sex) {
		this.sex = sex;
	}
}
