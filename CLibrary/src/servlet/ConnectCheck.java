package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectCheck {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		final String URL = "jdbc:mysql://172.16.71.116:6802/clibrary?serverTimezone=JST";
		final String USER = "team1";
		final String PASS = "CLibrary";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			System.out.println("DB接続しました");
		} catch (SQLException e) {
			System.out.println("DB接続失敗しました");
		}
	}

}
