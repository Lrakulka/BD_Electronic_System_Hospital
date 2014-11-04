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
@Table(name="groups")
public class Group {
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")	
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
    @JoinColumn(name="group_id")
    private Group group;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="group")
	private Set<Group> groups;
	
	public Group() {		
	}
	
	public Group(String name) {		
		this.name = name;
	}
	
	/**
	  * Project use Lazy initialization that is why class has two methods
	  * "getGroups" and "getAllGroups". First method return link to groups, 
	  * second method create session and get data from database and 
	  * return link to object ArrayList witch contains all groups of 
	  * current group. Return null if find group is current group.
	  */
	public ArrayList<Group> getAllGroups() {
		ArrayList<Group> listGroups;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		Group group = (Group) session.load(Group.class, this.id);
		Hibernate.initialize(group.groups);
		listGroups = new ArrayList<>(group.getGroups());
		session.close();
		if ( listGroups.isEmpty() || listGroups.get(0).id.equals(this.id))
			return null;
		else return listGroups;
	}
	
	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
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
		Group other = (Group) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
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

	public String getName() {
		return name;
	}

	public Group getGroup() {
		return group;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
}
