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
import org.hibernate.annotations.Type;

/** ��� ���� ����������� ��� ������������ Hibernate ��� 
 * �������� ������ � �������� "groups" �� ���� ����� "hospital" */
@Entity
@Table(name="groups")
public class Group extends CommonField {
	/**����������� ����� � ������� "diagnosis"*/
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")	
	private Integer id;
	
	/**���� ��'� ����� � ������� "groups"*/
	@Column(name = "name")
	private String name;
	
	/**���� ������� ����� � ������� "groups"*/
	@Column(name = "isHead")
	@Type(type = "boolean")
	private Boolean isHead;
	
	/**���� ����� � ������� "groups"*/
	@ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

	/**���� ���� ������ ����� ��� ����������� ��� ����, �� ���������� ���� �����,
	 *  �� ��'����� ������� "groups" �� ������������ "group_id", ���� ���� ��������� 
	 *  � ������������� "id" ���� � ������� "groups". ³�������� ���� �� ��������
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="group")
	private Set<Group> groups;
	
	/**��������� ��'���� �����*/
	public Group() {		
	}
	
	/**��������� ��'���� ����� �� �����������
	 * @param name ����� �����
	 * @param isHead ��� ���������� � true �� ����� �������� �������,
	 * ��� false �� ������� �����*/
	public Group(String name, Boolean isHead) {		
		this.name = name;
		this.isHead = isHead;
	}
	
	/**
	  * ��� ������ ����������� "Lazy initialization" - �� ������, �� ��� �� ��'������
	  * �������(�����) �� �������������� �����������, ���� � ����� ���� ��� ������ ���
	  *  ��������� �����. ������ ����� "getGroups" ������� ��������� �����, ���� �� ���
	  *   ��� ����������, ������ ����� "getAllGroups" ������� ����� �� ���� ����� � 
	  *   ��������� � �� �������� ��� � ������� ��������� � ���� ������, �� 
	  *   ���������� ������� �����.
	  *   @return �������� ����
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

	/**@return ������� true ���� ����� � ��������, false ���� �*/
	public Boolean getIsHead() {
		return isHead;
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
		Group other = (Group) obj;
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

	/**@return ������� ����� �����*/
	public String getName() {
		return name;
	}
	
	/**@return ������� ��'��� �����*/
	public Group getGroup() {
		return group;
	}

	/**@return ������� �������� ����*/
	public Set<Group> getGroups() {
		return groups;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**@param name ������������ ����� ��� �����*/
	public void setName(String name) {
		this.name = name;
	}

	/**@param group ������������ ����� ��� ������� �����*/
	public void setGroup(Group group) {
		this.group = group;
	}

	/**@param groups ������������ �������� ����*/
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	/**@param isHead ������������ ������ �����*/
	public void setIsHead(Boolean isHead) {
		this.isHead = isHead;
	}	
}
