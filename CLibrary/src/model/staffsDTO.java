package model;

import java.io.Serializable;

public class staffsDTO implements Serializable{
private int staff_id;
private String name;
private String pass;
private int gender;

public staffsDTO() {}
public staffsDTO(int staff_id, String name,String pass,int gender) {
	this.staff_id=staff_id;
	this.name=name;
	this.pass=pass;	
	this.gender=gender;
}
public int getStaff_Id() {
	return staff_id;
}	
public String getName() {
	return name;
}
public String getPass(){
	return pass;
}
public int gender() {
	return gender;
}

}
