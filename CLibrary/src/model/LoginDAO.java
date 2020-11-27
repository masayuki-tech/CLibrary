package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.StaffsDTO;

public class LoginDAO {

	//ＤＢ接続情報の設定
	//final String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	// 	final String USER="javauser";
	//	final String PASS="java06pass";

	final String URL = "jdbc:mysql://172.16.71.108:3306/sampledb?serverTimezone=JST";
	final String USER = "CLibary";
	final String PASS = "CLibrary01";

	//ＳＱＬ文
	final String LOGIN_SQL = "select staff_id,mail,pass,name,gender from staffs where mail=? && pass=?";//ログイン用
	final String REGISTER_SQL = "insert into staffs(mail,pass,name,gender) values(?,?,?,?)";//新規登録用
	final String PROFILE_SQL = "select staff_id,mail,pass,name,gender from staffs where name=? && mail=? && pass=? && gender=?";
	final String MYPAGE_SQL="select books.book_name,rentlogs.rent_date,rentlogs.return_date from books join rentlogs on books.book_id=rentlogs.book_id where rentlogs.staff_id=? && books.rent_check=1";
	//ログイン*****************************************************************
	public StaffsDTO getLoginDAO(String mail, String pass) {

		//DBに接続して、ログイン可能かどうかのチェック
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(LOGIN_SQL)) {

			//staffsDTOのインスタンスを作成
			StaffsDTO sd = new StaffsDTO();

			//？に差し込む
			pstm.setString(1, mail);//メール
			pstm.setString(2, pass);//パスワード

			//SQL文の実行(ResultSetの取得)
			ResultSet rs = pstm.executeQuery();

			//ResultSetのフェッチ処理（取り出すデータがある間、繰り返す)
			while (rs.next()) {
				//各列のデータをDTOにセッターを使って保存
				int staff_id = rs.getInt("staff_id"); //ＩＤ
				mail = rs.getString("mail");//メールアドレス
				pass = rs.getString("pass");//パスワード
				String name = rs.getString("name");//名前
				int gender = rs.getInt("gender");//性別

				//取り出したレコードを保存するためのDTOオブジェクトの生成
				sd = new StaffsDTO(staff_id, mail, pass, name, gender);
			}
			return sd;

		} catch (SQLException e) {
			return null;
		}
	}

	//新規登録******************************************************************
	public boolean getRegisterDAO(String name, String mail, String pass, int gender) {
		//DBに接続して、新規登録する
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(REGISTER_SQL)) {

			//取り出したレコードを保存するためのDTOオブジェクトの生成
			StaffsDTO sd = new StaffsDTO();

			//？に差し込む
			pstm.setString(1, mail);//メールアドレス
			pstm.setString(2, pass);//パスワード
			pstm.setString(3, name);//名前
			pstm.setInt(4, gender);//性別

			//SQL文の実行(ResultSetの取得)
			if (pstm.executeUpdate() != 1) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			return false;
		}
	}

	//新規登録したアカウントの情報を取得****************************************************
	public StaffsDTO getUserDAO(String name, String mail, String pass, int gender) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(PROFILE_SQL)) {

			//？に差し込む
			pstm.setString(1, name);//名前
			pstm.setString(2, mail);//メール
			pstm.setString(3, pass);//パスワード
			pstm.setInt(4, gender);//性別

			//SQL文の実行(ResultSetの取得)
			ResultSet rs = pstm.executeQuery();

			//staffsDTOのインスタンスを生成
			StaffsDTO sd = new StaffsDTO();

			//ResultSetのフェッチ処理
			while (rs.next()) {
				//各列のデータをDTOにセッターを使って保存
				int staff_id = rs.getInt("staff_id"); //ＩＤ
				mail = rs.getString("mail");//メールアドレス
				pass = rs.getString("pass");//パスワード
				name = rs.getString("name");//名前
				gender = rs.getInt("gender");//性別

				//取り出したレコードを保存するためのDTOオブジェクトの生成
				sd = new StaffsDTO(staff_id, mail, pass, name, gender);
			}
			return sd;

		} catch (SQLException e) {
			return null;
		}
	}
}