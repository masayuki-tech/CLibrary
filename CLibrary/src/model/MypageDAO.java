package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MypageDAO {

	// 実行するSQL文を文字列として事前に設定
	final String SQLRENT1 = "update set rent_check = 1 from books where id = '1'";
	final String SQLRETURNUPDATE = "update from lentlogs where return_date = '";
	final String RETURN_SQL1 = "update rentlogs set return_date=? where rent_id=? ";
	final String RETURN_SQL2 = "update books set rent_check=0 where book_id=?";
	final String RENT_SQL1 = "insert into rentlogs (rent_date,book_id,staff_id) values(?,?,?)";
	final String RENT_SQL2 = "update books set rent_check=1 where book_id=?";

	//ＤＢ接続用
	final String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	final String USER = "javauser";
	final String PASS = "java06pass";

	//**********************************************************
	//返却するメソッド？
	//**********************************************************
	public int rentBook() {
		// DB接続処理は例外処理が必須
		InitialContext ic;
		DataSource ds = null;

		// JNDI処理ではチェック例外に対する例外処理が必須
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:/comp/env/jdbc:db");

			try (Connection conn = ds.getConnection();
					PreparedStatement pstm = conn.prepareStatement(SQLRENT1)) {
				// SQL文の実行
				pstm.executeUpdate();
				return 0;

			} catch (SQLException e) {
				return 1;
			}
		} catch (NamingException e) {
			return 1;
		}
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public int returnBook() {
		InitialContext ic;
		DataSource ds = null;

		LocalDate returnDate = getReturnDate();

		// JNDI処理ではチェック例外に対する例外処理が必須
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:/comp/env/jdbc:db");

			try (Connection conn = ds.getConnection();
					PreparedStatement pstm = conn.prepareStatement(SQLRENT1 + returnDate + "'")) {

				// SQL文の実行
				pstm.executeUpdate();

				return 0;
			} catch (SQLException e) {

				e.printStackTrace();
				return 1;
			}
		} catch (NamingException e) {
			return 1;
		}
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//今日の日付を取得するメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	private LocalDate getReturnDate() {

		LocalDate returnDate = LocalDate.now();
		return returnDate;
	}

	//**********************************************************
	//返却ボタンが押された時の処理
	//**********************************************************
	public boolean setReturn(int delBookId, int delRentId) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm1 = conn.prepareStatement(RETURN_SQL1);
				PreparedStatement pstm2 = conn.prepareStatement(RETURN_SQL2)) {

			//プレイスホルダーに差し込む
			pstm1.setString(1, getToday());//返却日
			pstm1.setInt(2, delRentId);//貸出ＩＤ
			pstm2.setInt(1, delBookId);//書籍id

			//SQL文１の実行
			if (pstm1.executeUpdate() == 1 && pstm2.executeUpdate() == 1) {
				return true;

			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//今日の日付を取得
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public String getToday() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return dtf.format(LocalDateTime.now());
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//借りるボタンを押した時の処理
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public boolean getCanRent(int rentBookId, int rentStaffId) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm1 = conn.prepareStatement(RENT_SQL1);
				PreparedStatement pstm2 = conn.prepareStatement(RENT_SQL2)) {

			//プレイスホルダーに差し込む
			pstm1.setString(1, getToday());//貸出日
			pstm1.setInt(2, rentBookId);//書籍ＩＤ
			pstm1.setInt(3, rentStaffId);//社員ＩＤ
			pstm2.setInt(1, rentBookId);//書籍id

			//SQL文１の実行
			if (pstm1.executeUpdate() == 1 && pstm2.executeUpdate() == 1) {
				return true;

			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
}
