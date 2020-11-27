package dto;

import java.io.Serializable;

public class RentlogsDTO implements Serializable{
	private int rent_id;
	private String rent_date;
	private String return_date;
    private int book_id;
    private int staff_id;

	public RentlogsDTO() {}
	public RentlogsDTO(int rent_id, String rent_date,String return_date,int book_id,int staff_id) {
		this.rent_id=rent_id;
		this.rent_date=rent_date;
		this.return_date=return_date;
		this.book_id=book_id;
		this.staff_id=staff_id;


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
	public int getBook_Id() {
		return book_id;
	}
	public int getStaff_Id() {
		return staff_id;
	}


}
