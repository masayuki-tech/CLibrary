package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dto.BooksDTO;
import dto.ForListDTO;
import dto.StaffsDTO;

public class KuramotoDAO {

	//	//ＤＢ接続情報の設定
	//	final String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	//	final String USER = "javauser";
	//	final String PASS = "java06pass";

//	//ＤＢ接続用
//	final String URL = "jdbc:mysql://172.16.71.108:3306/sampledb?serverTimezone=JST";
//	final String USER = "CLibary";
//	final String PASS = "CLibrary01";

	//ＤＢ接続用
	final String URL = "jdbc:mysql://172.16.71.116:3306/clibrary?serverTimezone=JST";
	final String USER = "team1";
	final String PASS = "CLibrary";

	//本の登録ＳＱＬ文
	final String SQL1 = "insert into books (jan,book_name,pur_date,rent_check) values (?,?,?,?)";
	//全従業員一覧のＳＱＬ文
	final String SQL2 = "select * from staffs";
	//全本の一覧のＳＱＬ文
	final String SQL3 = "select * from books";
	//人気の本ランキングＴＯＰ３
	final String SQL4 = "select book_name,jan,count(*) from rentlogs left join books on rentlogs.book_id=books.book_id group by jan order by count(*) desc limit 3";
	//全貸出履歴の一覧
	final String SQL5 = "select * from rentlogs left join staffs on rentlogs.staff_id=staffs.staff_id";
	//貸出中の本リスト（返却期限が迫っている順）
	final String SQL6 = "select * from rentlogs left join staffs on rentlogs.staff_id=staffs.staff_id where return_date is null order by rent_date";
	//あいまい検索
	final String SQL7 = "select * from books where book_name like ? || jan like ? || book_id like ? && rent_check=0";

	//**********************************************************
	//本の登録テスト
	//**********************************************************
	public boolean newBookInsert() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(SQL1)) {

			//プレイスホルダーに差し込む
			pstm.setString(1, "9999999999");//jan
			pstm.setString(2, "あいうえおの本");//本の名前
			pstm.setString(3, "20201130");//購入日
			pstm.setInt(4, 0);//貸出ステータス

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
	//全従業員一覧表示テスト
	//**********************************************************
	public List<StaffsDTO> allStaffsList() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

			//ArrayListを準備
			List<StaffsDTO> list1 = new ArrayList<>();
			list1.clear();

			//SQL文を実行してResultSetに格納
			ResultSet rs1 = stm.executeQuery(SQL2);

			//１行ずつ取り出す
			while (rs1.next()) {
				int staffId = rs1.getInt("staff_id");//社員ＩＤ
				String name = rs1.getString("name");//名前
				String mail = rs1.getString("mail");//メール
				int gender = rs1.getInt("gender");//性別

				//StaffsDTOコンストラクタに引数を渡す
				StaffsDTO sd1 = new StaffsDTO(staffId, mail, "*******", name, gender);
				//ArrayListに追加していく
				list1.add(sd1);
			}
			return list1;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//全本の一覧表示テスト
	//**********************************************************
	public List<BooksDTO> allBooksList() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

			//ArrayListを準備
			List<BooksDTO> list2 = new ArrayList<>();
			list2.clear();

			//SQL文を実行してResultSetに格納
			ResultSet rs2 = stm.executeQuery(SQL3);

			//１行ずつ取り出す
			while (rs2.next()) {
				int bookId = rs2.getInt("book_id");//書籍ＩＤ
				String bookName = rs2.getString("book_name");//本の名前
				String jan = rs2.getString("jan");//ＪＡＮ
				String purDate = rs2.getString("pur_date");//購入日
				int rentCheck = rs2.getInt("rent_check");//貸出ステータス

				//BooksDTOコンストラクタに引数を渡す
				BooksDTO bd1 = new BooksDTO(bookId, jan, bookName, purDate, rentCheck);
				//ArrayListに追加していく
				list2.add(bd1);
			}
			return list2;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//★人気の本ランキングＴＯＰ3
	//**********************************************************
	public List<BooksDTO> getTop3() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

			//ArrayListを準備
			List<BooksDTO> list3 = new ArrayList<>();
			list3.clear();

			//SQL文を実行してResultSetに格納
			ResultSet rs3 = stm.executeQuery(SQL4);

			//１行ずつ取り出す
			while (rs3.next()) {
				String bookName = rs3.getString("book_name");//本の名前
				String jan = rs3.getString("jan");//ＪＡＮ
				//BooksDTOコンストラクタに引数を渡す
				BooksDTO bd2 = new BooksDTO(bookName, jan);
				//ArrayListに追加していく
				list3.add(bd2);
			}
			return list3;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//全貸出履歴リスト
	//**********************************************************
	public List<ForListDTO> rentLogsAllList() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

			//ArrayListを準備
			List<ForListDTO> list4 = new ArrayList<>();
			list4.clear();

			//SQL文を実行してResultSetに格納
			ResultSet rs4 = stm.executeQuery(SQL5);

			//１行ずつ取り出す
			while (rs4.next()) {
				int rentId = rs4.getInt("rent_id");//貸出管理ＩＤ
				Date rentDate = rs4.getDate("rent_date");//貸出日
				Date returnDate = rs4.getDate("return_date");//返却日
				int bookId = rs4.getInt("book_id");//書籍ＩＤ
				int staffId = rs4.getInt("rentlogs.staff_id");//社員ＩＤ
				String name = rs4.getString("name");//氏名
				int staffId2 = rs4.getInt("staffs.staff_id");//いらんけど取得

				//BooksDTOコンストラクタに引数を渡す
				ForListDTO fld2 = new ForListDTO(rentId, rentDate, returnDate, bookId, staffId, name, staffId2);
				//ArrayListに追加していく
				list4.add(fld2);
			}
			return list4;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//全貸出中のリスト
	//**********************************************************
	public List<ForListDTO> rentNowAllList() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

			//ArrayListを準備
			List<ForListDTO> list5 = new ArrayList<>();
			list5.clear();

			//SQL文を実行してResultSetに格納
			ResultSet rs5 = stm.executeQuery(SQL6);

			//１行ずつ取り出す
			while (rs5.next()) {
				int rentId = rs5.getInt("rent_id");//貸出管理ＩＤ
				Date rentDate = rs5.getDate("rent_date");//貸出日
				Date returnDate = rs5.getDate("return_date");//返却日
				int bookId = rs5.getInt("book_id");//書籍ＩＤ
				int staffId = rs5.getInt("rentlogs.staff_id");//社員ＩＤ
				String name = rs5.getString("name");//氏名
				int staffId2 = rs5.getInt("staffs.staff_id");//いらんけど取得

				//BooksDTOコンストラクタに引数を渡す
				ForListDTO fld3 = new ForListDTO(rentId, rentDate, returnDate, bookId, staffId, name, staffId2);
				//ArrayListに追加していく
				list5.add(fld3);
			}
			return list5;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//２週間以上借りているリスト
	//**********************************************************
	public List<ForListDTO> overRent() {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {
			//ArrayListを準備
			List<ForListDTO> list6 = new ArrayList<>();
			list6.clear();

			//SQL文を実行してResultSetに格納
			ResultSet rs6 = stm.executeQuery(SQL6);

			//１行ずつ取り出す
			while (rs6.next()) {
				int rentId = rs6.getInt("rent_id");//貸出管理ＩＤ
				Date rentDate = rs6.getDate("rent_date");//貸出日
				Date returnDate = rs6.getDate("return_date");//返却日
				int bookId = rs6.getInt("book_id");//書籍ＩＤ
				int staffId = rs6.getInt("rentlogs.staff_id");//社員ＩＤ
				String name = rs6.getString("name");//氏名
				int staffId2 = rs6.getInt("staffs.staff_id");//いらんけど取得

				//今日の日付と比較
				ForListDTO fld4 = new ForListDTO(rentDate);

				String xday = String.valueOf(fld4.getSchedule());
				//返却予定日
				LocalDate schedule = LocalDate.parse(xday);

				if (schedule.isBefore(getToday())) {
					//BooksDTOコンストラクタに引数を渡す
					fld4 = new ForListDTO(rentId, rentDate, returnDate, bookId, staffId, name, staffId2);
					//ArrayListに追加していく
					list6.add(fld4);
				}
			}
			return list6;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//今日の日付を取得するメソッド
	//**********************************************************
	public LocalDate getToday() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		return LocalDate.parse(dtf.format(now));
	}

	//**********************************************************
	//あいまい検索のメソッド
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
}
