package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	
	public Card() {
		// TODO Auto-generated constructor stub
	}
	 
	public Card(String name, Integer age, Gender sex) {
		// TODO Auto-generated constructor stub
		this.age = age;
		this.name = name;
		this.sex = sex;
	}
	
	public boolean equals(Card card) {
		if ( card.age == age && card.name.equals(name) && card.sex == sex)
			return true;
		else return false;
	}
}
