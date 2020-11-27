package model;

import java.io.Serializable;

public class booksDTO implements Serializable{
	private int book_id;
	private String jan;
	private String book_name;
	private String pur_date;
  private int rent_check;

	public booksDTO() {}
	public booksDTO(int book_id,String jan, String book_name,String pur_date, int rent_check) {
		this.book_id=book_id;
		this.jan=jan;
		this.book_name=book_name;
		this.pur_date=pur_date;
		this.rent_check=rent_check;

	}
	public int getBook_Id() {
		return book_id;
	}

	public String getJan(){
		return jan;
	}
	public String getBook_Name() {
		return book_name;
	}
	public String getPur_Date() {
		return pur_date;
	}
	public int getRent_Check() {
		return rent_check;
	}
}
