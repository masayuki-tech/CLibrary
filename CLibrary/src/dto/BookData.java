package dto;

public class BookData {
	 //title
    String title;

    //authors
    String author;

    //publishedDate
    String publishedDate;

    //publisher
    String publisher;

    //description
    String description;


    //コンストラクタ
    public BookData(String title, String author, String publishedDate, String publisher, String description) {
		this.title = title;
		this.author = author;
		this.publishedDate = publishedDate;
		this.publisher = publisher;
		this.description = description;
	}

    //getter &setter **********************************************************
	public String getTitle() {
		return title;
	}

	public void setTitle(String booktitle) {
		this.title = booktitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
