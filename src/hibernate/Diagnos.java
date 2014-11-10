package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

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
@Entity
@Table(name="diagnosis")
public class Diagnos extends CommonField {
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")	
	private Integer id;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="diagnos")
	private Set<hibernate.Session> sessions;

	@ManyToOne
    @JoinColumn(name="disease")
    private Disease disease;
	
	public Diagnos() {
	}
	
	public Diagnos(String description) {
		this.description = description;
	}
	
	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	/**
	  * Project use Lazy initialization that is why class has two methods
	  * "getSessions" and "getAllSessions". First method return link to sessions, 
	  * second method create session and get data from database and 
	  * return link to object ArrayList witch contains all sessions of 
	  * current diagnose.
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
	
	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Set<hibernate.Session> getSessions() {
		return sessions;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSessions(Set<hibernate.Session> sessions) {
		this.sessions = sessions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
