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
import dto.RentlogsDTO;
import dto.StaffsDTO;

public class MasterDAO {
	// データ登録用のSQL文(プレースホルダ利用)
	//final String SQL = "select * from books";
	final String SQL = "insert into books (jan,book_name,pur_date,rent_check,image,publisher,author,description) values(?,?,?,?,?,?,?,?)";//本の登録のSQL文
	final String SEARCH = "select * from rentlogs";//rentlogsの返却されていない検索
	//全従業員一覧のＳＱＬ文
	final String SQL2 = "select * from staffs";
	//全本の一覧のＳＱＬ文
	final String SQL3 = "select * from books";
	//全貸出履歴の一覧
	final String SQL5 = "select * from rentlogs left join staffs on rentlogs.staff_id=staffs.staff_id";
	//貸出中の本リスト（返却期限が迫っている順）
	final String SQL6 = "select * from rentlogs left join staffs on rentlogs.staff_id=staffs.staff_id where return_date is null order by rent_date";

	//ＤＢ接続用
	final String URL = "jdbc:mysql://localhost:3306/project?serverTimezone=JST";
	final String USER = "javauser";
	final String PASS = "java06pass";

	//**********************************************************
	//本の追加
	//**********************************************************
	public boolean insert(BooksDTO bookdata) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(SQL)) {

			pstm.setString(1, bookdata.getJan());
			pstm.setString(2, bookdata.getBookName());
			pstm.setString(3, bookdata.getPurDate());
			pstm.setInt(4, bookdata.getRentCheck());
			pstm.setString(5, bookdata.getImage());
			pstm.setString(6, bookdata.getPublisher());
			pstm.setString(7, bookdata.getAuthor());
			pstm.setString(8, bookdata.getDescription());

			// 1行登録できたかどうかの確認
			if (pstm.executeUpdate() == 1) {
				return true; // 登録できたのでtrueを返す
			} else {
				return false; // 登録できなかったのでfalseを返す
			}
		} catch (SQLException e) {
			// 例外が発生したので、falseを返す
			return false;
		}
	}

	//**********************************************************
	//二週間以上借りている人を検索してリストに格納
	//**********************************************************
	public List<RentlogsDTO> getSearch() {

		//ArrayListを宣言
		List<RentlogsDTO> limitOverList = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement pstm = conn.createStatement()) {

			//今日の２週間前の日付を出す
			LocalDate date = LocalDate.now().minusDays(14);

			//ＳＱＬ文を実行
			ResultSet rs = pstm.executeQuery(SEARCH);

			while (rs.next()) {
				//結果から貸出日を取得
				String rent_date = rs.getString("rent_date");
				//データ型をLocalDateに変換
				LocalDate date3 = LocalDate.parse(rent_date);
				//二週間前ならLISTにデータを一行ずつ格納
				if (date.isAfter(date3)) {
					int rentId = rs.getInt("rent_id");
					String returnDate = rs.getString("return_date");
					int bookId = rs.getInt("book_id");
					int staffId = rs.getInt("staff_id");
					//コンストラクタに引数として渡す
					RentlogsDTO rentlogs = new RentlogsDTO(rentId, rent_date, returnDate, bookId, staffId);
					//リストに格納
					limitOverList.add(rentlogs);
				}
			}
			return limitOverList;

		} catch (SQLException e) {
			return limitOverList;
		}
	}

	//**********************************************************
	//全従業員一覧表示テスト
	//**********************************************************
	public List<StaffsDTO> allStaffsList() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

			//ArrayListを準備
			List<StaffsDTO> list = new ArrayList<>();

			//SQL文を実行してResultSetに格納
			ResultSet rs = stm.executeQuery(SQL2);

			//１行ずつ取り出す
			while (rs.next()) {
				int staffId = rs.getInt("staff_id");//社員ＩＤ
				String name = rs.getString("name");//名前
				String mail = rs.getString("mail");//メール
				int gender = rs.getInt("gender");//性別

				//StaffsDTOコンストラクタに引数を渡す
				StaffsDTO sd1 = new StaffsDTO(staffId, mail, "*******", name, gender);
				//ArrayListに追加していく
				list.add(sd1);
			}
			return list;

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
			List<BooksDTO> list = new ArrayList<>();

			//SQL文を実行してResultSetに格納
			ResultSet rs = stm.executeQuery(SQL3);

			//１行ずつ取り出す
			while (rs.next()) {
				int bookId = rs.getInt("book_id");//書籍ＩＤ
				String bookName = rs.getString("book_name");//本の名前
				String jan = rs.getString("jan");//ＪＡＮ
				Date purDate = rs.getDate("pur_date");//購入日
				int rentCheck = rs.getInt("rent_check");//貸出ステータス

				//BooksDTOコンストラクタに引数を渡す
				BooksDTO bd1 = new BooksDTO(bookId, jan, bookName, purDate, rentCheck);
				//ArrayListに追加していく
				list.add(bd1);
			}
			return list;

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
			List<ForListDTO> list = new ArrayList<>();

			//SQL文を実行してResultSetに格納
			ResultSet rs = stm.executeQuery(SQL5);

			//１行ずつ取り出す
			while (rs.next()) {
				int rentId = rs.getInt("rent_id");//貸出管理ＩＤ
				Date rentDate = rs.getDate("rent_date");//貸出日
				Date returnDate = rs.getDate("return_date");//返却日
				int bookId = rs.getInt("book_id");//書籍ＩＤ
				int staffId = rs.getInt("rentlogs.staff_id");//社員ＩＤ
				String name = rs.getString("name");//氏名
				int staffId2 = rs.getInt("staffs.staff_id");//いらんけど取得

				//BooksDTOコンストラクタに引数を渡す
				ForListDTO fld2 = new ForListDTO(rentId, rentDate, returnDate, bookId, staffId, name, staffId2);
				//ArrayListに追加していく
				list.add(fld2);
			}
			return list;

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
			List<ForListDTO> list = new ArrayList<>();

			//SQL文を実行してResultSetに格納
			ResultSet rs = stm.executeQuery(SQL6);

			//１行ずつ取り出す
			while (rs.next()) {
				int rentId = rs.getInt("rent_id");//貸出管理ＩＤ
				Date rentDate = rs.getDate("rent_date");//貸出日
				Date returnDate = rs.getDate("return_date");//返却日
				int bookId = rs.getInt("book_id");//書籍ＩＤ
				int staffId = rs.getInt("rentlogs.staff_id");//社員ＩＤ
				String name = rs.getString("name");//氏名
				int staffId2 = rs.getInt("staffs.staff_id");//いらんけど取得

				//BooksDTOコンストラクタに引数を渡す
				ForListDTO fld3 = new ForListDTO(rentId, rentDate, returnDate, bookId, staffId, name, staffId2);
				//ArrayListに追加していく
				list.add(fld3);
			}
			return list;

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
			List<ForListDTO> list = new ArrayList<>();

			//SQL文を実行してResultSetに格納
			ResultSet rs = stm.executeQuery(SQL6);

			//１行ずつ取り出す
			while (rs.next()) {
				int rentId = rs.getInt("rent_id");//貸出管理ＩＤ
				Date rentDate = rs.getDate("rent_date");//貸出日
				Date returnDate = rs.getDate("return_date");//返却日
				int bookId = rs.getInt("book_id");//書籍ＩＤ
				int staffId = rs.getInt("rentlogs.staff_id");//社員ＩＤ
				String name = rs.getString("name");//氏名
				int staffId2 = rs.getInt("staffs.staff_id");//いらんけど取得

				//今日の日付と比較
				ForListDTO fld4 = new ForListDTO(rentDate);
				//返却予定日を取得
				String xday = String.valueOf(fld4.getSchedule());
				//取得した返却予定日をLocalDate型に変換
				LocalDate schedule = LocalDate.parse(xday);

				if (schedule.isBefore(getToday())) {
					//BooksDTOコンストラクタに引数を渡す
					fld4 = new ForListDTO(rentId, rentDate, returnDate, bookId, staffId, name, staffId2);
					//ArrayListに追加していく
					list.add(fld4);
				}
			}
			return list;

		} catch (SQLException e) {
			return null;
		}
	}

	//**********************************************************
	//今日の日付を取得するメソッド
	//**********************************************************
	public LocalDate getToday() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dtf.format(LocalDateTime.now()));
	}

}