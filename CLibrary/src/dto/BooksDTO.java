package dto;

import java.io.Serializable;
import java.sql.Date;

public class BooksDTO implements Serializable{
	private int book_id;
	private String jan;
	private String book_name;
	private String pur_date;
  private int rent_check;

	public BooksDTO() {}
	public BooksDTO(int book_id,String jan, String book_name,String pur_date, int rent_check) {
		this.book_id=book_id;
		this.jan=jan;
		this.book_name=book_name;
		this.pur_date=pur_date;
		this.rent_check=rent_check;

	}
	//★人気ランキングＴＯＰ3
	public BooksDTO(String book_name,String jan) {
		this.jan=jan;
		this.book_name=book_name;
	}

	public BooksDTO(int book_id, String jan, String book_name, Date pur_date, int rent_check){
		this.book_id=book_id;
		this.jan=jan;
		this.book_name=book_name;
		this.pur_date=String.valueOf(pur_date);
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
