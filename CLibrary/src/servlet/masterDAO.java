package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.booksDTO;
public class masterDAO {
	public boolean insert(booksDTO booksDTO) {


		// データ登録用のSQL文(プレースホルダ利用)
		final String SQL = "insert into books values(?,?,?,?)";


		   final String URL="jdbc:mysql://localhost:3306/project?serverTimezone=JST";
		   final String USER="javauser";
		   final String PASS="java08pass";


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
		}

