package model;

import java.io.Serializable;

public class rentlogsDTO implements Serializable{
	private int rent_id;
	private String rent_date;
	private String return_date;
    private int staff_id;
    private int book_id;
    
	public rentlogsDTO() {}
	public rentlogsDTO(int rent_id, String rent_date,String return_date,int staff_id,int book_id) {
		this.rent_id=rent_id;
		this.rent_date=rent_date;
		this.return_date=return_date;
		this.staff_id=staff_id;
		this.book_id=book_id;
		
	}
	public int getRent_Id() {
		return rent_id;
	}	
	public String getRent_Date() {
		return rent_date;
	}
	public String getReturn_Date(){
		return return_date;
	}
	public int getStaff_Id() {
		return staff_id;
	}
	public int getBook_Id() {
		return book_id;
	}

}
