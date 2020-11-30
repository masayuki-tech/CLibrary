package dto;

public interface EnvSet {
	//googleの検索URL、PROXY等定義
    String GOOGLE_BOOKS_API_ISBN = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    String GOOGLE_BOOKS_API_TITLE = "https://www.googleapis.com/books/v1/volumes?q=intitle:";

    String J701_HTTPS_PROXY_ADDRESS = "172.16.71.20";
    String J701_HTTPS_PROXY_PORT = "3128";
}
