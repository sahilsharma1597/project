package Bean;

public class Employee {
	
private int id;
private String name;
private String password;
private String cpassword;

public Employee(int id, String password) {
	super();
	this.id = id;
	this.password = password;
}

//private String message="";
/*public void setMessage(String message) {
	this.message = message;
}
*/
public String getCpassword() {
	return cpassword;
}

public void setCpassword(String cpassword) {
	this.cpassword = cpassword;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public Employee() {
	super();
	// TODO Auto-generated constructor stub
}

public Employee(int id, String name, String password, String cpassword) {
	super();
	this.id = id;
	this.name = name;
	this.password = password;
	this.cpassword = cpassword;
}

@Override
public String toString() {
	return "Employee [id=" + id + ", name=" + name + ", password=" + password + ", cpassword=" + cpassword + "]";
}

public void setName(String name) {
	this.name = name;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

}