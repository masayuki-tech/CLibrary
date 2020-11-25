package servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class masterDAO {
	public boolean insert(masterDAO booksDTO) {
		InitialContext ic;
		DataSource ds=null;

		// データ登録用のSQL文(プレースホルダ利用)
		final String SQL = "insert into books values(?,?)";


		// データベース接続およびプリペアードステートメントの作成
		try {
			ic=new InitialContext();
			ds=(DataSource)ic.lookup("java:/comp/env/jdbc:db");

			try(Connection conn=ds.getConnection();
					PreparedStatement pstm = conn.prepareStatement(SQL)){

			// JavaBeans内のデータを取り出し、プレースホルダに代入
			pstm.setInt(1, booksDTO.getBooks_Id());
			pstm.setString(2, booksDTO.getBook_Name());
			pstm.setString(3, booksDTO.getJan());
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
		}catch(NamingException e) {
			return false;
		}
		}}
