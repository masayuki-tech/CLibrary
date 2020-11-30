package dto;

import java.io.Serializable;

public class StaffsDTO implements Serializable{
private int staff_id;
private String mail;
private String pass;
private String name;
private int gender;

public StaffsDTO() {}
public StaffsDTO(int staff_id,String mail,String pass, String name,int gender) {
	this.staff_id=staff_id;
	this.mail=mail;
	this.pass=pass;
	this.name=name;
	this.gender=gender;
}
public int getStaff_Id() {
	return staff_id;
}
public String getMail() {
	return mail;
}
public String getPass(){
	return pass;
}
public String getName() {
	return name;
}
public int getGender() {
	return gender;
}

}
