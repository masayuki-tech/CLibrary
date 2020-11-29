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

	// DB接続情報の設定
	final static String URL = "jdbc:mysql://localhost:3306/sampledb?serverTimezone=JST";
	final static String USER = "masayuki";
	final static String PASS = "masayuki6";

	// 登録されている本の一覧表示をするメソッド
	public static List<BooksDTO> showBooks() {

		List<BooksDTO> booksAllList = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement pstm = conn.createStatement()) {

			ResultSet bk = pstm.executeQuery(SQLSHOW);

			while (bk.next()) {

				int book_id = bk.getInt("book_id");
				String jan = bk.getString("jan");
				String book_name = bk.getString("book_name");
				String pur_date = bk.getString("pur_date");
				int rent_check = bk.getInt("rent_check");
				BooksDTO bookData = new BooksDTO(book_id, jan, book_name, pur_date, rent_check);
				booksAllList.add(bookData);

			}

			return booksAllList;
		} catch (

		SQLException e)

		{
			BooksDTO bookData = new BooksDTO(1, "12345", "あああ", "2000-01-01", 1);
			BooksDTO bookData2 = new BooksDTO(2, "22345", "いいい", "2000-02-01", 2);
			booksAllList.add(bookData);
			booksAllList.add(bookData2);
			return booksAllList;
		}
	}
}
