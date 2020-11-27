package dto;

import java.io.Serializable;

public class MyRentDTO implements Serializable{
	private String book_name;
	private String return_date;

	public String getBook_name() {
		return book_name;
	}
	public String getReturn_date() {
		return return_date;
	}
}
