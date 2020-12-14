package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.BooksDTO;

public class BooksDAO {

	// 実行するSQL文を文字列として事前に設定
	final static String SQLSHOW = "select * from books";

	//ＤＢ接続用
	final static String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	final static String USER = "javauser";
	final static String PASS = "java06pass";

	// 登録されている本の一覧表示をするメソッド
	public static List<BooksDTO> showBooks() {

		List<BooksDTO> booksAllList = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement pstm = conn.createStatement()) {

			ResultSet bk = pstm.executeQuery(SQLSHOW);

			while (bk.next()) {

				String book_name = bk.getString("book_name");
				String image = bk.getString("image");
				BooksDTO bookData = new BooksDTO(book_name, image);
				booksAllList.add(bookData);

			}

			return booksAllList;
		} catch (

		SQLException e)

		{
			BooksDTO bookData = new BooksDTO("Java参考書", "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=2&edge=curl&source=gbs_api");
			BooksDTO bookData2 = new BooksDTO("PHP参考書", "bbbbbb");
			booksAllList.add(bookData);
			booksAllList.add(bookData2);
			return booksAllList;
		}
	}
}
