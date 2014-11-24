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

/** ��� ���� ����������� ��� ������������ Hibernate ��� 
 * �������� ������ � �������� "cards" �� ���� ����� "hospital" */
@Entity
@Table(name="cards")
public class Card extends CommonField {
	/**���� ���� "id" ������� "cards"*/
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	/**���� ���� "name" ������� "cards"*/
	@Column(name = "name")
	private String name;
	
	/**���� ���� "age" ������� "cards"*/
	@Column(name = "age")
	private Short age;	
	
	/**���� ���� "sex" ������� "cards"*/
	@Column(name = "sex")
	@Enumerated(EnumType.STRING) 
	private Gender sex;
	
	/**���� ���� "note" ������� "cards"*/
	@Column(name = "note")
	@Type(type = "text")
	private String note;
	
	/**���� ���� "isAgain" ������� "cards"*/
	@Column(name = "isAgain")
	@Type(type = "boolean")
	private Boolean isAgain;
	
	/**���� ���� ������ ����� ��� ����������� ��� ����, �� ���������� ���� ������,
	 *  �� ��'����� ������� "sessions" �� ������������ "card_id", ���� ���� ��������� 
	 *  � ������������� "id" ���� � ������� "cards". ³�������� ���� �� ��������
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL)
    private Set<hibernate.Session> sessions;
	
	/**���� ���� ������ ����� ��� ����������� ��� �������, �� ���������� ���� ������,
	 *  �� ��'����� ������� "sessions" �� ������������ "card_id", ���� ���� ��������� � 
	 *  ������������� "id" ���� � ������� "cards". ³�������� ���� �� ��������
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="card")
    private Set<Note> notes;
	
	/**��������� ��'���� ��� ����������� ���� ���� */
	public Card() {
	}
	 
	
	/**��������� ��'���� ����� �� �������� ���� 
	 * @param name ��'� �������� ���� ������
	 * @param age �� �������� ���� ������
	 * @param sex ����� �������� ���� ������
	 * @param isAgain �� ������ �������
	 * */
	public Card(String name, Short age, Gender sex, Boolean isAgain) {
		this.age = age;
		this.name = name;
		this.sex = sex;
		this.isAgain = isAgain;
	}
	
	/**����� ������� ���������, �� ������ ����� �������, �� ���������� ���� 
	 * ������. ��� ����, �� �� ��� ��� ���������� � ���� ����� � ��������, 
	 * ������ �������� �������
	 * @return �������� ����*/
	public Set<Note> getNotes() {
		return notes;
	}
	
	/**
	  * ��� ������ ����������� "Lazy initialization" - �� ������, �� ��� �� ��'������
	  * �������(�������) �� �������������� �����������, ���� � ����� ���� ��� ������ ���
	  *  ��������� �����. ������ ����� "getNotes" ������� ��������� �����, ���� �� ���
	  *   ��� ����������, ������ ����� "getAllNotes" ������� ����� �� ���� ����� � 
	  *   ��������� � �� �������� ��� � ������� ��������� � ���� ������, �� 
	  *   ���������� ������� ������.
	  *   @return �������� �������
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
	  * ��� ������ ����������� "Lazy initialization" - �� ������, �� ��� �� ��'������
	  * �������(���) �� �������������� �����������, ���� � ����� ���� ��� ������ ��� 
	  * ��������� �����. ������ ����� "getSessions" ������� ��������� �����, ���� �� 
	  * ��� ��� ����������, ������ ����� "getAllSessions" ������� ����� �� ���� �����
	  *  � ��������� � �� �������� ��� � ������� ��������� � ���� ������, �� 
	  *  ���������� ������� ������.
	  *  @return �������� ����
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

	/**������� �� ������
	 * @param card ������ � ���� ����� ��������
	 * @return true ���� ��� ��� false ���� �*/
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
		if (isAgain == null) {
			if (other.isAgain != null)
				return false;
		} else if (!isAgain.equals(other.isAgain))
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
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (sessions == null) {
			if (other.sessions != null)
				return false;
		} else if (!sessions.equals(other.sessions))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}


	/**@return ������� ���� �����, ��� � ������������� ����� ��'���� 
	 * � ��� �����*/
	public Integer getId() {
		return id;
	}
	
	/**@return ������� ��'� �������� ������*/
	public String getName() {
		return name;
	}

	/**@return ������� �� �������� ������*/
	public Short getAge() {
		return age;
	}

	/**@return ������� ����� �������� ���� ������*/
	public Gender getSex() {
		return sex;
	}

	/**@return ������� ������ ����� �� ���� ������*/
	public String getNote() {
		return note;
	}

	/**@return ������� true, ���� ������� �� ������ � 
	 * �������� false, ���� ������ */
	public Boolean getIsAgain() {
		return isAgain;
	}

	/**����� ������� ���������, �� ������ ����� ����, �� ���������� ���� ������.
	 * ��� ����, �� �� ��� ��� ���������� � ���� ����� � ��������, 
	 * ������ �������� �������
	 * @return ������� ����� ����, �� ���������� ���� ������
	 * */
	public Set<hibernate.Session> getSessions() {
		return sessions;
	}

	/**���������� ��� ������ ��������
	 * @param sessions �������� ����*/
	public void setSessions(Set<hibernate.Session> sessions) {
		this.sessions = sessions;
	}

	/**���������� ��� ������ ��������
	 * @param id ������������� ��� ������ � ��� �����*/
	public void setId(Integer id) {
		this.id = id;
	}

	/**���������� ��� ������ ��������
	 * @param name ��'� �������� ���� ������*/
	public void setName(String name) {
		this.name = name;
	}

	/**���������� ��� ������ ��������
	 * @param age �� �������� ���� ������*/
	public void setAge(Short age) {
		this.age = age;
	}

	/**���������� ��� ������ ��������
	 * @param sex ���������� ����� �������� ���� ������*/
	public void setSex(Gender sex) {
		this.sex = sex;
	}

	/**���������� ��� ������ ��������
	 * @param note ���������� ������ �� ������*/
	public void setNote(String note) {
		this.note = note;
	}

	/**���������� ��� ������ ��������
	 * @param isAgain ���������� ������ �� �*/
	public void setIsAgain(Boolean isAgain) {
		this.isAgain = isAgain;
	}
	
	/**���������� ��� ������ ��������
	 * @param notes ���������� ����� ������� �� ������*/
	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}
}
