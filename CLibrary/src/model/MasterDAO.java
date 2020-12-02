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


	final String URL = "jdbc:mysql://172.16.71.116:3306/clibrary?serverTimezone=JST";
	final String USER = "team1";
	final String PASS = "CLibrary";

	public boolean insert(BooksDTO bookdata) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pstm = conn.prepareStatement(SQL)) {




//			ResultSet rs=pstm.executeQuery(SQL);

			//if(rs.next()) {
			//	return true;
			//}else {
			//	return false;
			//}

			// JavaBeans内のデータを取り出し、プレースホルダに代入
//		pstm.setString(1, "1");
//		pstm.setString(2,"1");
//		pstm.setString(3, "20201020");
//		pstm.setInt(4,1);
//			pstm.setString(5, "1");
//			pstm.setString(6, "1");
//			pstm.setString(7, "1");
//	pstm.setString(8, "1");
			//pstm.setInt(1, bookdata.getBook_Id());
			pstm.setString(1, bookdata.getJan());
		    pstm.setString(2, bookdata.getBook_Name());
			pstm.setString(3, bookdata.getPur_Date());
			pstm.setInt(4, bookdata.getRent_Check());
			pstm.setString(5, bookdata.getImage());
 		    pstm.setString(6, bookdata.getPublisher());
 		    pstm.setString(7, bookdata.getAuthor());
			pstm.setString(8, bookdata.getDescription());

			// SQL文の実行(登録した行数が返される)
			int insertedRecode = pstm.executeUpdate();

			// 1行登録できたかどうかの確認
			if ( insertedRecode== 1) {
				return true; // 登録できたのでtrueを返す
			} else {
				return false; // 登録できなかったのでfalseを返す
			}
		} catch (SQLException e) {
			System.out.println(e);
			// 例外が発生したので、falseを返す
			return false;
		}
	}

	public List<RentlogsDTO> getSearch() {//二週間以上借りている人の検索してリストに格納

		List<RentlogsDTO> limitOverList = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement pstm = conn.createStatement()) {

			LocalDate date1 = LocalDate.now();
			LocalDate date2 = date1.minusDays(14);//現在日付から二週間前の日付を出す

			ResultSet ra = pstm.executeQuery(SEARCH);
			while (ra.next()) {
				String rent_date = ra.getString("rent_date");//貸出日付を一行出力

				LocalDate date3 = LocalDate.parse(rent_date);

				boolean a = date2.isAfter(date3);
				if (a) {//二週間前ならLISTにデータを一行ずつ格納
					int rent_id = ra.getInt("rent_id");
					String return_date = ra.getString("return_date");
					int book_id = ra.getInt("book_id");
					int staff_id = ra.getInt("staff_id");
					RentlogsDTO rentlogs = new RentlogsDTO(rent_id, rent_date, return_date, book_id, staff_id);
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
					Date purDate = rs2.getDate("pur_date");//購入日
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



}