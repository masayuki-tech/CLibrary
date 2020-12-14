package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.BooksDTO;

public class BookSearchDAO {

	//ＤＢ接続用
	final String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	final String USER = "javauser";
	final String PASS = "java06pass";

	//あいまい検索Mypage
	final String SQL7 = "select * from books where book_name like ? || jan like ? || book_id like ? && rent_check=0";
	//あいまい検索Main
	final String SQL8 = "select * from books where book_name like ? || jan like ? || book_id like ? || author like ? || publisher like ? || description like ?";

	//**********************************************************
	//あいまい検索Mypageのメソッド
	//**********************************************************
	public List<BooksDTO> likeSearchDAO(String text) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(SQL7)) {
			//ArrayListを準備
			List<BooksDTO> list = new ArrayList<>();

			//プレイスホルダーに挿入
			pstm.setString(1, '%' + text + '%');//本の名前
			pstm.setString(2, '%' + text + '%');//jan
			pstm.setString(3, '%' + text + '%');//書籍ID

			//SQL文を実行してResultSetに格納
			ResultSet rs = pstm.executeQuery();

			//１行ずつ取り出す
			while (rs.next()) {
				int bookId = rs.getInt("book_id");//書籍ＩＤ
				String jan = rs.getString("jan");//JANコード
				String bookName = rs.getString("book_name");//本の名前
				Date purDate = rs.getDate("pur_date");//購入日
				int rentCheck = rs.getInt("rent_check");//貸出ステータス

				BooksDTO bd3 = new BooksDTO(bookId, jan, bookName, purDate, rentCheck);
				//ArrayListに追加していく
				list.add(bd3);
			}
			return list;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//あいまい検索Mainのメソッド
	//**********************************************************
	public List<BooksDTO> likeSearchDAO2(String text) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(SQL8)) {
			//ArrayListを準備
			List<BooksDTO> list = new ArrayList<>();

			//プレイスホルダーに挿入
			pstm.setString(1, '%' + text + '%');//本の名前
			pstm.setString(2, '%' + text + '%');//jan
			pstm.setString(3, '%' + text + '%');//書籍ID
			pstm.setString(4, '%' + text + '%');//著者
			pstm.setString(5, '%' + text + '%');//出版社
			pstm.setString(6, '%' + text + '%');//説明文

			//SQL文を実行してResultSetに格納
			ResultSet rs = pstm.executeQuery();

			//１行ずつ取り出す
			while (rs.next()) {
				int bookId = rs.getInt("book_id");//書籍ＩＤ
				String jan = rs.getString("jan");//JANコード
				String bookName = rs.getString("book_name");//本の名前
				Date purDate = rs.getDate("pur_date");//購入日
				int rentCheck = rs.getInt("rent_check");//貸出ステータス

				BooksDTO bd4 = new BooksDTO(bookId, jan, bookName, purDate, rentCheck);
				//ArrayListに追加していく
				list.add(bd4);
			}
			return list;

		} catch (SQLException e) {
			return null;
		}
	}

}
