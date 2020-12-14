package dto;

import java.sql.Date;

public class ForListDTO implements java.io.Serializable {
	private int bookId;
	private String jan;
	private String bookName;
	private Date purDate;
	private int rentCheck;
	private int rentId;
	private Date rentDate;
	private Date returnDate;
	private int staffId;
	private String mail;
	private String pass;
	private String name;
	private int gender;
	private Date schedule;

	public ForListDTO() {
	}

	//（全て）
	public ForListDTO(int bookId, String jan, String bookName, Date purDate, int rentCheck, int rentId, Date rentDate,
			Date returnDate,
			int staffId, String mail, String pass, String name, int gender) {
		this.bookId = bookId;
		this.jan = jan;
		this.bookName = bookName;
		this.purDate = purDate;
		this.rentCheck = rentCheck;
		this.rentId = rentId;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.staffId = staffId;
		this.mail = mail;
		this.pass = pass;
		this.name = name;
		this.gender = gender;
		//貸し出し日に+14日して、返却予定日を計算
		schedule = java.sql.Date.valueOf(rentDate.toLocalDate().plusDays(14));
	}

	//（６）
	public ForListDTO(String bookName, int rentId, Date rentDate, Date returnDate, int bookId, int staffId) {
		this.bookName = bookName;
		this.rentId = rentId;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.bookId = bookId;
		this.staffId = staffId;
		//貸し出し日に+14日して、返却予定日を計算
		schedule = java.sql.Date.valueOf(rentDate.toLocalDate().plusDays(14));
	}

	//借りられる本の情報を取得するメソッド（５）
	public ForListDTO(int staffId, int bookId, String jan, String bookName, int rentCheck) {
		this.staffId = staffId;
		this.bookId = bookId;
		this.jan = jan;
		this.bookName = bookName;
		this.rentCheck = rentCheck;

	}

	//全貸出履歴（7）
	public ForListDTO(int rentId, Date rentDate, Date returnDate, int bookId, int staffId, String name, int staffId2) {
		this.rentId = rentId;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.bookId = bookId;
		this.staffId = staffId;
		this.name = name;
		//貸し出し日に+14日して、返却予定日を計算
		schedule = java.sql.Date.valueOf(rentDate.toLocalDate().plusDays(14));

	}

	//借りられる本のリスト（4）
	public ForListDTO(int bookId, String jan, String bookName, int rentCheck) {
		this.bookId = bookId;
		this.jan = jan;
		this.bookName = bookName;
		this.rentCheck = rentCheck;
	}

	//２週間以上超えている人のリスト用
	public ForListDTO(Date rentDate) {
		//貸し出し日に+14日して、返却予定日を計算
		schedule = java.sql.Date.valueOf(rentDate.toLocalDate().plusDays(14));
	}

	//**********************************************************
	//getter
	//**********************************************************
	public int getBookId() {
		return bookId;
	}

	public String getJan() {
		return jan;
	}

	public String getBookName() {
		return bookName;
	}

	public Date getPurDate() {
		return purDate;
	}

	public int getRentCheck() {
		return rentCheck;
	}

	public int getRentId() {
		return rentId;
	}

	public Date getRentDate() {
		return rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public int getStaffId() {
		return staffId;
	}

	public String getMail() {
		return mail;
	}

	public String getPass() {
		return pass;
	}

	public String getName() {
		return name;
	}

	public int getGender() {
		return gender;
	}

	public Date getSchedule() {
		return schedule;
	}

	//**********************************************************
	//setter
	//**********************************************************
	public void setBookId() {
		this.bookId = bookId;
	}

	public void setJan() {
		this.jan = jan;
	}

	public void setBookName() {
		this.bookName = bookName;
	}

	public void setPurDate() {
		this.purDate = purDate;
	}

	public void setRentCheck() {
		this.rentCheck = rentCheck;
	}

	public void setRentId() {
		this.rentId = rentId;
	}

	public void setRentDate() {
		this.rentDate = rentDate;
	}

	public void setReturnDate() {
		this.returnDate = returnDate;
	}

	public void setStaffId() {
		this.staffId = staffId;
	}

	public void setMail() {
		this.mail = mail;
	}

	public void setPass() {
		this.pass = pass;
	}

	public void setName() {
		this.name = name;
	}

	public void setGender() {
		this.gender = gender;
	}

	public void setSchedule() {
		//貸し出し日に+14日して、返却予定日を計算
		schedule = java.sql.Date.valueOf(rentDate.toLocalDate().plusDays(14));
	}
}
