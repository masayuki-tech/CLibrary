package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.BooksDTO;
import dto.ForListDTO;
import dto.StaffsDTO;

public class LoginDAO {

	//ＤＢ接続情報の設定
	final String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	final String USER = "javauser";
	final String PASS = "java06pass";

	//ＳＱＬ文
	final String SQL = "select * from rentlogs join books on rentlogs.book_id=books.book_id join staffs on rentlogs.staff_id=staffs.staff_id";
	final String LOGIN_SQL = "select staff_id,mail,pass,name,gender from staffs where mail=? && pass=?";//ログイン用
	final String REGISTER_SQL = "insert into staffs(mail,pass,name,gender) values(?,?,?,?)";//新規登録用
	final String PROFILE_SQL = "select staff_id,mail,pass,name,gender from staffs where name=? && mail=? && pass=? && gender=?";
	final String MYPAGE_WHERE = " where mail=? && pass=? && rent_check=1 &&  return_date is null";
	final String CANRENT_SQL = "select * from books where rent_check=0";
	//人気の本ランキングＴＯＰ5
	final String SQL4 = "select book_name,jan,count(*) from rentlogs join books on rentlogs.book_id=books.book_id group by jan order by count(*) desc limit 5";

	//*************************************************************
	//ログイン認証
	//*************************************************************
	public boolean connectLogin(String mail, String pass) {
		//DBに接続して、ログイン可能かどうかのチェック
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(LOGIN_SQL)) {

			//？に差し込む
			pstm.setString(1, mail);//メール
			pstm.setString(2, pass);//パスワード

			//SQL文の実行(ResultSetの取得)
			ResultSet rs = pstm.executeQuery();

			//ResultSetのフェッチ処理
			if (rs.next()) {
				//各列のデータをDTOにセッターを使って保存
				String mail1 = rs.getString("mail");//メールアドレス
				String pass1 = rs.getString("pass");//パスワード
				if (mail.equals(mail1) && pass.equals(pass1)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	//**********************************************************
	//ログインしたユーザーの情報を取得する
	//**********************************************************
	public StaffsDTO getLoginUser(String mail, String pass) {

		//DBに接続して、ログイン可能かどうかのチェック
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(LOGIN_SQL)) {

			//staffsDTOのインスタンスを作成
			StaffsDTO sd2 = new StaffsDTO();

			//？に差し込む
			pstm.setString(1, mail);//メール
			pstm.setString(2, pass);//パスワード

			//SQL文の実行(ResultSetの取得)
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				//各列のデータをDTOにセッターを使って保存
				int staff_id = rs.getInt("staff_id"); //ＩＤ
				String mail1 = rs.getString("mail");//メールアドレス
				String pass1 = rs.getString("pass");//パスワード
				String name = rs.getString("name");//名前
				int gender = rs.getInt("gender");//性別

				//取り出したレコードを保存するためのDTOオブジェクトの生成
				sd2 = new StaffsDTO(staff_id, mail1, pass1, name, gender);
				return sd2;

			} else {
				return null;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	//********************************************************************************
	//新規登録処理
	//********************************************************************************
	public boolean getRegisterDAO(String name, String mail, String pass, int gender) {
		//DBに接続して、新規登録する
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(REGISTER_SQL)) {

			//？に差し込む
			pstm.setString(1, mail);//メールアドレス
			pstm.setString(2, pass);//パスワード
			pstm.setString(3, name);//名前
			pstm.setInt(4, gender);//性別

			//SQL文の実行
			if (pstm.executeUpdate() != 1) {
				return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			return false;
		}
	}

	//**********************************************************
	//新規登録したアカウントの情報を取得
	//**********************************************************
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

	//********************************************************************
	//ユーザーが現在借りている本リストを取得するメソッド
	//********************************************************************
	public List<ForListDTO> getRentNowListDAO(String mail, String pass) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(SQL + MYPAGE_WHERE)) {

			//プレイスホルダーに差し込む
			pstm.setString(1, mail);//メール
			pstm.setString(2, pass);//パスワード

			//SQL文の実行(ResultSetの取得)
			ResultSet rs = pstm.executeQuery();

			//ArrayListの宣言
			List<ForListDTO> bookList = new ArrayList<>();
			bookList.clear();

			//ResultSetのフェッチ処理
			while (rs.next()) {
				//各列のデータをDTOにセッターを使って保存
				String bookName = rs.getString("book_name"); //本の名前
				int rentId = rs.getInt("rent_id");//貸し出し履歴ID
				Date rentDate = rs.getDate("rentlogs.rent_date");//貸出日
				Date returnDate = rs.getDate("rentlogs.return_date");//返却日
				int bookId = rs.getInt("rentlogs.book_id");//書籍ID
				int staffId = rs.getInt("rentlogs.staff_id");//社員ID

				//取り出したレコードを保存するためのDTOオブジェクトの生成
				ForListDTO fld = new ForListDTO(bookName, rentId, rentDate, returnDate, bookId, staffId);
				bookList.add(fld);
			}
			return bookList;

		} catch (SQLException e) {
			return null;
		}
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//借りられる本の情報を取得するメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public List<ForListDTO> getCanRentListDAO() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(CANRENT_SQL)) {

			//SQL文の実行(ResultSetの取得)
			ResultSet rs = pstm.executeQuery();

			//staffsDTOのインスタンスを生成
			ForListDTO fld = new ForListDTO();

			//ArrayListの宣言
			List<ForListDTO> bookList1 = new ArrayList<>();
			bookList1.clear();

			//ResultSetのフェッチ処理
			while (rs.next()) {
				//各列のデータをDTOにセッターを使って保存
				//				int staffId = rs4.getInt("staffs.staff_id"); //社員ＩＤ
				int bookId = rs.getInt("books.book_id");//書籍ＩＤ
				String jan = rs.getString("jan");//ＪＡＮ
				String bookName = rs.getString("book_name");//本の名前
				int rentCheck = rs.getInt("rent_check");//貸出ステータス

				//取り出したレコードを保存するためのDTOオブジェクトの生成
				fld = new ForListDTO(bookId, jan, bookName, rentCheck);
				bookList1.add(fld);
			}
			return bookList1;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//人気の本ランキングＴＯＰ5
	//**********************************************************
	public List<BooksDTO> getTop5() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

			//ArrayListを準備
			List<BooksDTO> list = new ArrayList<>();
			list.clear();

			//SQL文を実行してResultSetに格納
			ResultSet rs = stm.executeQuery(SQL4);

			//１行ずつ取り出す
			while (rs.next()) {
				String bookName = rs.getString("book_name");//本の名前
				String jan = rs.getString("jan");//ＪＡＮ
				String bbb = null;
				//BooksDTOコンストラクタに引数を渡す
				BooksDTO bd2 = new BooksDTO(bookName, jan, bbb);
				//ArrayListに追加していく
				list.add(bd2);
			}
			return list;

		} catch (SQLException e) {
			return null;
		}
	}
}
