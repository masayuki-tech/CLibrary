package model;

import java.io.Serializable;

public class booksDTO implements Serializable{
	private int book_id;
	private String book_name;
	private String jan;
    private String pur_date;
    
	public booksDTO() {}
	public booksDTO(int book_id, String book_name,String jan,String pur_date) {
		this.book_id=book_id;
		this.book_name=book_name;
		this.jan=jan;
		this.pur_date=pur_date;
		
	}
	public int getBook_Id() {
		return book_id;
	}	
	public String getBook_Name() {
		return book_name;
	}
	public String getJan(){
		return jan;
	}
	public String getPur_Date() {
		return pur_date;
	}
}
