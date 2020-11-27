package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.booksDTO;
import model.rentlogsDTO;
public class masterDAO {
	// データ登録用のSQL文(プレースホルダ利用)
			final String SQL = "insert into books values(?,?,?,?)";//本の登録のSQL文
			final String SEARCH="select * from rentlogs where return_day = null";//rentlogsの返却されていない検索
			
			final String URL="jdbc:mysql://localhost:3306/project?serverTimezone=JST";
			final String USER="javauser";
			final String PASS="java08pass";


	public boolean insert(booksDTO booksDTO) {

			try(Connection conn=DriverManager.getConnection(URL,USER,PASS);
					PreparedStatement pstm = conn.prepareStatement(SQL)){

			// JavaBeans内のデータを取り出し、プレースホルダに代入
			pstm.setInt(1, booksDTO.getBook_Id());
			pstm.setString(2, booksDTO.getJan());
			pstm.setString(3, booksDTO.getBook_Name());
			pstm.setString(4, booksDTO.getPur_Date());


			// SQL文の実行(登録した行数が返される)
			int insertedRecode = pstm.executeUpdate();

			// 1行登録できたかどうかの確認
			if (insertedRecode == 1) {
				return true; // 登録できたのでtrueを返す
			} else {
				return false; // 登録できなかったのでfalseを返す
			}
			}
			catch (SQLException e) {
				// 例外が発生したので、falseを返す
				return false;
			}
	}

	public List<rentlogsDTO> getSearch() {//二週間以上借りている人の検索してリストに格納

		List<rentlogsDTO> limitOverList = new ArrayList<>();

		try(Connection conn=DriverManager.getConnection(URL,USER,PASS);
				PreparedStatement pstm = conn.prepareStatement(SEARCH)){
			
			LocalDate date1 = LocalDate.now();
			date1.minusDays(14);//現在日付から二週間前の日付を出す
			
			
			ResultSet ra = pstm.executeQuery();
			while (ra.next()) {
				String rent_date = ra.getString("rent_date");
				
				LocalDate date2 =LocalDate.parse(rent_date);//貸出日付を一行出力
				
				boolean a =date1.isBefore(date2);
				if(a) {//二週間前ならLISTにデータを一行ずつ格納
					int rent_id=ra.getInt("rent_id");
					String rent_date2=ra.getString("rent_date");
					String return_date=ra.getString("return_date");
					int staff_id=ra.getInt("staff_id");
					int book_id=ra.getInt("book_id");
					rentlogsDTO rentlogs = new rentlogsDTO( rent_id,rent_date2,return_date,staff_id,book_id);
					limitOverList.add(rentlogs);	
				}
			}
			
			return limitOverList;
		}catch (SQLException e) {
				
				return limitOverList;
			}

		}


	}


