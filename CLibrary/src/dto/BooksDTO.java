package dto;

import java.io.Serializable;

public class BooksDTO implements Serializable{
	private int book_id;
	private String jan;
	private String book_name;
	private String pur_date;
    private int rent_check;
    private String image;
    private String publisher;
    private String author;
    private String description;


	public BooksDTO() {}
	public BooksDTO(int book_id,String jan, String book_name,String pur_date, int rent_check,String image,
			String publisher,String author,String description) {
		this.book_id=book_id;
		this.jan=jan;
		this.book_name=book_name;
		this.pur_date=pur_date;
		this.rent_check=rent_check;
		this.image=image;
		this.publisher=publisher;
		this.author=author;
		this.description=description;

	}

	public BooksDTO(String book_name, String image) {
		this.book_name = book_name;
		this.image = image;
	}

	public int getBook_Id() {
		return book_id;
	}
    public String getJan(){
		return jan;
	}
    public void setJan(String jan) {
    	this.jan=jan;
    }
	public String getBook_Name() {
		return book_name;
	}
	public void setBook_Name(String book_name) {
		this.book_name=book_name;
	}
	public String getPur_Date() {
		return pur_date;
	}
	public int getRent_Check() {
		return rent_check;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image=image;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher=publisher;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author=author;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
