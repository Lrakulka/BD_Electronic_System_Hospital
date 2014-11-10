package hibernate;

import hibernateConnect.DatabaseConnect;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="diseases")
public class Disease extends CommonField {
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")	
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="disease")
    private Set<Diagnos> diagnosis;
	
	public Disease() {
		
	}
	
	public Disease(String name) {
		this.name = name;
	}
	/**
	  * Project use Lazy initialization that is why class has two methods
	  * "getDiagnosis" and "getAllDiagnosis". First method return link to diagnosis, 
	  * second method create session and get data from database and 
	  * return link to object ArrayList witch contains all diagnosis of 
	  * current disease.
	  */
	public ArrayList<Diagnos> getAllDiagnosis() {
		ArrayList<Diagnos> listDiagnosis;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Disease disease = (Disease) session.load(Disease.class, this.id);
		Hibernate.initialize(disease.diagnosis);
		listDiagnosis = new ArrayList<>(disease.getDiagnosis());
		session.close();
		return listDiagnosis;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<Diagnos> getDiagnosis() {
		return diagnosis;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDiagnosis(Set<Diagnos> diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Disease other = (Disease) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
