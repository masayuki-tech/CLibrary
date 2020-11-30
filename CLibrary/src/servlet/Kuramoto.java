package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Kuramoto
 */
@WebServlet("/Kuramoto")
public class Kuramoto extends HttpServlet {
	private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public Kuramoto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		final String URL = "jdbc:mysql://172.16.71.108:3306/sampledb?serverTimezone=JST";
		final String USER = "CLibary";
		final String PASS = "CLibrary01";
		final String SQL="insert into books (jan,book_name,pur_date,rent_check) values ('7777777777','さしすせその本','20201129',1)";
		final String SQL2="insert into rentlogs (rent_date,return_date,book_id,staff_id)values('20201130',null,3,1)";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				Statement stm = conn.createStatement()) {

//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/success.jsp");
//			dispatcher.forward(request, response);

			//insert into books(jan,book_name,pur_date,check) values(?,?,?,?)";
			//プレイスホルダーに差し込む
//			pstm.setString(1, "9999999999");//jan
//			pstm.setString(2, "あいうえおの本");//本の名前
//			pstm.setString(3,"20201130");//購入日
//			pstm.setInt(4,0);//貸出ステータス

			//SQL文の実行
			stm.executeUpdate(SQL2);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/success.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fail.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
