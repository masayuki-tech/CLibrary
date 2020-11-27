package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MypageDAO {

	// 実行するSQL文を文字列として事前に設定
	final static String SQLRENT1 = "update from books where rent_id = '1'";
	final static String SQLRETURNUPDATE = "update from lentLogs where return_date = '";

	// 返却するメソッド
	public static int rentBook() {
		// DB接続処理は例外処理が必須

		InitialContext ic;
		DataSource ds = null;

		// JNDI処理ではチェック例外に対する例外処理が必須
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:/comp/env/jdbc:db");

			try (
					Connection conn = ds.getConnection();
					PreparedStatement pstm = conn.prepareStatement(SQLRENT1)) {

				// SQL文の実行
				pstm.executeUpdate();

				return 0;
			} catch (SQLException e) {

				e.printStackTrace();
				return 1;
			}
		} catch (NamingException e) {
			// TODO: handle exception
			return 1;
		}
	}

	public static int returnBook() {
		InitialContext ic;
		DataSource ds = null;

		LocalDate returnDate = getReturnDate();

		// JNDI処理ではチェック例外に対する例外処理が必須
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:/comp/env/jdbc:db");

			try (
					Connection conn = ds.getConnection();
					PreparedStatement pstm = conn.prepareStatement(SQLRENT1 + returnDate + "'")) {

				// SQL文の実行
				pstm.executeUpdate();

				return 0;
			} catch (SQLException e) {

				e.printStackTrace();
				return 1;
			}
		} catch (NamingException e) {
			// TODO: handle exception
			return 1;
		}
	}

	private static LocalDate getReturnDate() {

		LocalDate returnDate = LocalDate.now();
		return returnDate;
	}
}
