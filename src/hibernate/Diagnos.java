package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

/** ��� ���� ����������� ��� ������������ Hibernate ��� 
 * �������� ������ � �������� "diagnosis" �� ���� ����� "hospital" */
@Entity
@Table(name="diagnosis")
public class Diagnos extends CommonField {
	/**����������� ����� � ������� "diagnosis"*/
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")	
	private Integer id;
	
	/**������� �� ������� � ������� "diagnosis"*/
	@Column(name = "description")
	private String description;
	
	/**���� ���� ������ ����� ��� ����������� ��� ����, �� ���������� ������ �������,
	 *  �� ��'����� ������� "sessions" �� ������������ "diagnosis_id", ���� ���� ��������� 
	 *  � ������������� "id" ���� � ������� "diagnos". ³�������� ���� �� ��������
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="diagnos", cascade = CascadeType.ALL)
	private Set<hibernate.Session> sessions;

	/**��'����� �������� � �������� "diseases". ³�������� ������ �� ������*/
	@ManyToOne
    @JoinColumn(name="disease")
    private Disease disease;
	
	/**��������� ��'���� �����*/
	public Diagnos() {
	}
	
	/**��������� ��'���� ����� �� �����������
	 * @param description ������ �� �������*/
	public Diagnos(String description) {
		this.description = description;
	}
	
	/**@return ������� ��'��� �������*/
	public Disease getDisease() {
		return disease;
	}

	/**@param disease ������������ ������� ��� �������*/
	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	/**
	  * ��� ������ ����������� "Lazy initialization" - �� ������, �� ��� �� ��'������
	  * �������(���) �� �������������� �����������, ���� � ����� ���� ��� ������ ���
	  *  ��������� �����. ������ ����� "getSessions" ������� ��������� �����, ���� �� ���
	  *   ��� ����������, ������ ����� "getAllSessions" ������� ����� �� ���� ����� � 
	  *   ��������� � �� �������� ��� � ������� ��������� � ���� ������, �� 
	  *   ���������� ��������� �������.
	  *   @return �������� ����
	  */
	public ArrayList<hibernate.Session> getAllSessions() {
		ArrayList<hibernate.Session> listSessions;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Diagnos diagnos = (Diagnos) session.load(Diagnos.class, this.id);
		Hibernate.initialize(diagnos.sessions);
		listSessions = new ArrayList<>(diagnos.getSessions());
		session.close();
		return listSessions;
	}
	
	/**@return ������� ����������� � ��� �����*/
	public Integer getId() {
		return id;
	}

	/**@return ������� ������ �� �������*/
	public String getDescription() {
		return description;
	}

	/**@return ������� �������� ����*/
	public Set<hibernate.Session> getSessions() {
		return sessions;
	}

	/**@param id ���������� ����������� � ��� �����*/
	public void setId(Integer id) {
		this.id = id;
	}

	/**@param description ���������� ������ �� �������*/
	public void setDescription(String description) {
		this.description = description;
	}

	/**@param sessions ���������� �������� ���� ��� �������*/
	public void setSessions(Set<hibernate.Session> sessions) {
		this.sessions = sessions;
	}

	/**��������� ���-���� ������ ��'����
	 * @return ������� ���� �����, ��� � ���-�����*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**������� ��� ��'����
	 * @param obj ��'��� � ���� ����� ��������
	 * @return true ���� ��� ��� false ���� �*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diagnos other = (Diagnos) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
