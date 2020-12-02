package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dto.BooksDTO;
import dto.RentlogsDTO;

public class MasterDAO {
	// データ登録用のSQL文(プレースホルダ利用)
	final String SQL = "select * from books";
//	final String SQL = "insert into books (jan,book_name,pur_date,rent_check,image,publisher,author,description) values(?,?,?,?,?,?,?,?)";//本の登録のSQL文
	final String SEARCH = "select * from rentlogs";//rentlogsの返却されていない検索

	final String URL = "jdbc:mysql://172.16.71.116:3306/clibrary?serverTimezone=JST";
	final String USER = "team1";
	final String PASS = "CLibrary";

	public boolean insert(BooksDTO bookdata) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement pstm = conn.createStatement()) {

//			HttpSession session = request.getSession();
//			BooksDTO bookdata = (BooksDTO) session.getAttribute("result");

			ResultSet rs=pstm.executeQuery(SQL);

			if(rs.next()) {
				return true;
			}else {
				return false;
			}

			// JavaBeans内のデータを取り出し、プレースホルダに代入
//			pstm.setString(1, "1");
//			pstm.setString(2,"1");
//		pstm.setString(3, "20201020");
//		pstm.setInt(4,1);
//			pstm.setString(5, "1");
//			pstm.setString(6, "1");
//			pstm.setString(7, "1");
//		pstm.setString(8, "1");
			//pstm.setInt(1, bookdata.getBook_Id());
//			pstm.setString(1, bookdata.getJan());
//			pstm.setString(2, bookdata.getBook_Name());
//			pstm.setString(3, bookdata.getPur_Date());
//			pstm.setInt(4, bookdata.getRent_Check());
//			pstm.setString(5, bookdata.getImage());
//			pstm.setString(6, bookdata.getPublisher());
//			pstm.setString(7, bookdata.getAuthor());
//			pstm.setString(8, bookdata.getDescription());

			// SQL文の実行(登録した行数が返される)
//			int insertedRecode = pstm.executeUpdate();

//			// 1行登録できたかどうかの確認
//			if (pstm.executeUpdate() == 1) {
//				return true; // 登録できたのでtrueを返す
//			} else {
//				return false; // 登録できなかったのでfalseを返す
//			}
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

}
