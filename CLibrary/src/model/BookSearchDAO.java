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
	final String URL = "jdbc:mysql://172.16.71.116:3306/clibrary?serverTimezone=JST";
	final String USER = "team1";
	final String PASS = "CLibrary";

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
			List<BooksDTO> list7 = new ArrayList<>();
			list7.clear();

			//プレイスホルダーに挿入
			pstm.setString(1, '%' + text + '%');//本の名前
			pstm.setString(2, '%' + text + '%');//jan
			pstm.setString(3, '%' + text + '%');//書籍ID

			//SQL文を実行してResultSetに格納
			ResultSet rs7 = pstm.executeQuery();

			//１行ずつ取り出す
			while (rs7.next()) {
				int bookId = rs7.getInt("book_id");//書籍ＩＤ
				String jan = rs7.getString("jan");//JANコード
				String bookName = rs7.getString("book_name");//本の名前
				Date purDate = rs7.getDate("pur_date");//購入日
				int rentCheck = rs7.getInt("rent_check");//貸出ステータス

				BooksDTO bd3 = new BooksDTO(bookId, jan, bookName, purDate, rentCheck);
				//ArrayListに追加していく
				list7.add(bd3);
			}
			return list7;

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
			List<BooksDTO> list8 = new ArrayList<>();
			list8.clear();

			//プレイスホルダーに挿入
			pstm.setString(1, '%' + text + '%');//本の名前
			pstm.setString(2, '%' + text + '%');//jan
			pstm.setString(3, '%' + text + '%');//書籍ID
			pstm.setString(4, '%' + text + '%');//著者
			pstm.setString(5, '%' + text + '%');//出版社
			pstm.setString(6, '%' + text + '%');//説明文

			//SQL文を実行してResultSetに格納
			ResultSet rs8 = pstm.executeQuery();

			//１行ずつ取り出す
			while (rs8.next()) {
				int bookId = rs8.getInt("book_id");//書籍ＩＤ
				String jan = rs8.getString("jan");//JANコード
				String bookName = rs8.getString("book_name");//本の名前
				Date purDate = rs8.getDate("pur_date");//購入日
				int rentCheck = rs8.getInt("rent_check");//貸出ステータス

				BooksDTO bd4 = new BooksDTO(bookId, jan, bookName, purDate, rentCheck);
				//ArrayListに追加していく
				list8.add(bd4);
			}
			return list8;

		} catch (SQLException e) {
			return null;
		}
	}

}
