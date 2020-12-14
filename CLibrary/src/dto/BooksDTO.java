package dto;

import java.io.Serializable;
import java.util.Date;


public class BooksDTO implements Serializable{
	private int bookId;
	private String jan;
	private String bookName;
	private String purDate;
    private int rentCheck;
    private String image;
    private String publisher;
    private String author;
    private String description;


	public BooksDTO() {}
	public BooksDTO(int bookId,String jan, String bookName,String purDate, int rentCheck,String image,
			String publisher,String author,String description) {
		this.bookId=bookId;
		this.jan=jan;
		this.bookName=bookName;
		this.purDate=purDate;
		this.rentCheck=rentCheck;
		this.image=image;
		this.publisher=publisher;
		this.author=author;
		this.description=description;

	}
	//★人気ランキングＴＯＰ3
		public BooksDTO(String bookName,String jan,String bbb) {
			this.jan=jan;
			this.bookName=bookName;
		}

	public BooksDTO(String bookName, String image) {
		this.bookName = bookName;
		this.image = image;
	}
	public BooksDTO(int bookId, String jan, String bookName, Date purDate, int rentCheck){
		this.bookId=bookId;
		this.jan=jan;
		this.bookName=bookName;
		this.purDate=String.valueOf(purDate);
		this.rentCheck=rentCheck;
	}

	public int getBookId() {
		return bookId;
	}
    public String getJan(){
		return jan;
	}
    public void setJan(String jan) {
    	this.jan=jan;
    }
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName=bookName;
	}
	public String getPurDate() {
		return purDate;
	}
	public int getRentCheck() {
		return rentCheck;
	}
	public void setRentCheck(int rentCheck) {
		this.rentCheck=rentCheck;
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
