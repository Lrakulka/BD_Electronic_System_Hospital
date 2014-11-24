package hibernate;
import hibernateConnect.DatabaseConnect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
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

/** ��� ���� ����������� ��� ������������ Hibernate ��� 
 * �������� ������ � �������� "users" �� ���� ����� "hospital" */
@Entity
@Table(name="users")
public class User extends CommonField {
	/**����������� ����� � ������� "users"*/
	@Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")	
	private Integer id;   
	
	/**���� ������ ��'� �����������*/
	@Column(name = "name")
    private String name;
	
	/**���� ������ ����� ������� �����������*/
	@Column(name = "access_level")
    private Short access_level;
	
	/**���� ������ ������� �����������*/
	@Column(name = "phone")
    private String phone;
	
	/**���� ������ ������ �����������*/
	@Column(name = "pwd")
    private String pwd;
    
	/**���� ���� ������ ����� ��� ����������� ��� �������, �� ���������� ���� �����,
	 *  �� ��'����� ������� "notes" �� ������������ "user_id", ���� ���� ��������� 
	 *  � ������������� "id" ���� � ������� "user". ³�������� ���� �� ��������
	 *  */
	@OneToMany(fetch = FetchType.LAZY, mappedBy="user", cascade = CascadeType.ALL)
    private Set<Note> notes;
	
	/**@param pwd ���������� ������. ����� ���������� MD5*/
	public void setPwd(String pwd) {
		MessageDigest c = null;
		try {
			c = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	
		byte[] h = c.digest(pwd.getBytes());
		StringBuffer pass = new StringBuffer();
		
		for(int i = 0; i < h.length; ++i)
			pass.append(Integer.toHexString(0xFF & h[i]));
		this.pwd = pass.toString();
	}

	/**��������� ��'���� ����� �� �����������
	 * @param name ��'� �����������
	 * @param access_level ����� ������� �����������
	 * @param phone ������� �����������
	 * @param pwd ������ �����������*/
	public User( String name,  Short access_level,
			String phone, String pwd) {
	    this.name = name;
	    this.access_level = access_level;
	    this.phone = phone;
	    this.setPwd(pwd);
	}
	
	/**��������� ��'���� �����*/
	public User() {
	}

	public Integer getId() {
		return id;
	}

	/**@return ������� ��'� �����������*/
	public String getName() {
		return name;
	}

	/**@return ������� ����� ������� �����������*/
	public Short getAccess_level() {
		return access_level;
	}

	/**@return ������� ������� �����������*/
	public String getPhone() {
		return phone;
	}

	/**@return ������� ������ �����������*/
	public String getPwd() {
		return pwd;
	}
	
	/**@return ������� �������� �������*/
	public Set<Note> getNotes() {
		return notes;
	}
	
	/**
	  * ��� ������ ����������� "Lazy initialization" - �� ������, �� ��� �� ��'������
	  * �������(�������) �� �������������� �����������, ���� � ����� ���� ��� ������ ���
	  *  ��������� �����. ������ ����� "getNotes" ������� ��������� �����, ���� �� ���
	  *   ��� ����������, ������ ����� "getAllNotes" ������� ����� �� ���� ����� � 
	  *   ��������� � �� �������� ��� � ������� ��������� � ���� ������, �� 
	  *   ���������� ��������� �����������.
	  *   @return �������� �������
	  */
	public ArrayList<Note> getAllNotes() {
		ArrayList<Note> listNotes;
		Session session = DatabaseConnect.getSessionFactory().openSession();
		User user = (User) session.load(User.class, this.id);
		Hibernate.initialize(user.notes);
		listNotes = new ArrayList<>(user.getNotes());
		session.close();
		return listNotes;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	/**@param name ������������ ��'� �����������*/
	public void setName(String name) {
		this.name = name;
	}

	/**@param access_level ������������ ����� ������� �����������*/
	public void setAccess_level(Short access_level) {
		this.access_level = access_level;
	}

	/**@param phone ������������ ������� �����������*/
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**@param notes ������������ �������� �������*/
	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((access_level == null) ? 0 : access_level.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
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
		User user = (User) obj;
		if(this.access_level.equals(user.access_level) && 
				this.name.equals(user.name) &&
				this.phone.equals(user.phone) &&
				this.pwd.equals(user.pwd))
			return true;
		return false;
	}
}
