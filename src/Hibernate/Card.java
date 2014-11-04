package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.Type;

@Entity
@Table(name="card")
public class Card {
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private Short age;	
	
	@Column(name = "sex")
	@Enumerated(EnumType.STRING) 
	private Gender sex;
	
	@Column(name = "note")
	@Type(type = "text")
	private String note;
	
	@Column(name = "isAgain")
	@Type(type = "boolean")
	private Boolean isAgain;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="card")
    private Set<hibernate.Session> sessions;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="card")
    private Set<Note> notes;
	
	public Card() {
		// TODO Auto-generated constructor stub
	}
	 
	public Set<Note> getNotes() {
		return notes;
	}
	
	/**
	  * Project use Lazy initialization that is why class has two methods
	  * "getNotes" and "getAllNotes". First method return link to notes, 
	  * second method create session and get data from database and 
	  * return link to object ArrayList witch contains all notes of 
	  * current card.
	  */
	public ArrayList<Note> getAllNotes() {
		ArrayList<Note> listNotes;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Card card = (Card) session.load(Card.class, this.id);
		Hibernate.initialize(card.notes);
		listNotes = new ArrayList<>(card.getNotes());
		session.close();
		return listNotes;
	}
	
	/**
	  * Project use Lazy initialization that is why class has two methods
	  * "getSessions" and "getAllSessions". First method return link to sessions, 
	  * second method create session and get data from database and 
	  * return link to object ArrayList witch contains all sessions of 
	  * current card.
	  */
	public ArrayList<hibernate.Session> getAllSessions() {
		ArrayList<hibernate.Session> listSessions;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Card card = (Card) session.load(Card.class, this.id);
		Hibernate.initialize(card.sessions);
		listSessions = new ArrayList<>(card.getSessions());
		session.close();
		return listSessions;
	}
	
	public Card(String name, Short age, Gender sex, Boolean isAgain) {
		// TODO Auto-generated constructor stub
		this.age = age;
		this.name = name;
		this.sex = sex;
		this.isAgain = isAgain;
	}

	public boolean equals(Card card) {
		if ( card.age.equals(age) && card.name.equals(name) && card.sex == sex 
				&& card.isAgain.equals(isAgain) && ((note == null && note == card.note)
						|| card.note.equals(note)))
			return true;
		else return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result
				+ ((sessions == null) ? 0 : sessions.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAgain == null) ? 0 : isAgain.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Short getAge() {
		return age;
	}

	public Gender getSex() {
		return sex;
	}

	public String getNote() {
		return note;
	}

	public Boolean getIsAgain() {
		return isAgain;
	}

	public Set<hibernate.Session> getSessions() {
		return sessions;
	}

	public void setSessions(Set<hibernate.Session> sessions) {
		this.sessions = sessions;
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

	public void setIsAgain(Boolean isAgain) {
		this.isAgain = isAgain;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}
}
