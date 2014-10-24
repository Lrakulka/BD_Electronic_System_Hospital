package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="card")
public class Card {
	private Integer id;
	private String name;  
	private Short age;	
	private Gender sex;
	@Type(type = "text")
	private String note;
	@Type(type = "boolean")
	private boolean isAgain;
	
	@Column(name = "note")
	public String getNote() {
		return note;
	}
	@Column(name = "isAgain")
	public boolean isAgain() {
		return isAgain;
	}
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
	public Short getAge() {
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
	public void setAge(Short age) {
		this.age = age;
	}
	public void setSex(Gender sex) {
		this.sex = sex;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setAgain(boolean isAgain) {
		this.isAgain = isAgain;
	}
	public Card() {
		// TODO Auto-generated constructor stub
	}
	 
	public Card(String name, Short age, Gender sex, boolean isAgain) {
		// TODO Auto-generated constructor stub
		this.age = age;
		this.name = name;
		this.sex = sex;
		this.isAgain = isAgain;
	}
	/*
	public boolean equals(Card card) {
		if ( card.age == age && card.name.equals(name) && card.sex == sex &&
				card.isAgain == this.isAgain && ( card.age == this.age ||
				card.note.equals(this.note)))
			return true;
		else return false;
	}*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isAgain ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAgain != other.isAgain)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}
}
