package Hibernate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class Users {
	private Integer id;    
    private String name;
    private Short access_level;
    private String phone;
    private String pwd;
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
    @Column(name="access_level")
	public Short getAccess_level() {
		return access_level;
	}
    @Column(name="phone")
	public String getPhone() {
		return phone;
	}
    @Column(name="pwd")
	public String getPwd() {
		return pwd;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAccess_level(Short access_level) {
		this.access_level = access_level;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setPwd(String pwd) {
		MessageDigest c = null;
		try {
			c = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		/*byte[] h = String.valueOf(c.digest(pwd.getBytes())).getBytes();
		StringBuffer pass = new StringBuffer();
		for(int i = 0; i < h.length; ++i)
			pass.append(Integer.toHexString(h[i]));
		this.pwd = pass.toString();*/
		this.pwd = pwd;
	}

	public Users( String name,  Short access_level,
			String phone, String pwd) {
	    this.name = name;
	    this.access_level = access_level;
	    this.phone = phone;
	    this.setPwd(pwd);
	}
	public Users() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals(Users user) {
		if(this.access_level.equals(user.getAccess_level()) && 
				this.name.equals(user.getName()) &&
				this.phone.equals(user.getPhone()) &&
				this.pwd.equals(user.getPwd()))
			return true;
		return false;
	}
}
