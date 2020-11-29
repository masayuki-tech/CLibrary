package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DbConnectCheck
 */
@WebServlet("/DbConnectCheck")
public class DbConnectCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DbConnectCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

/*// 実行するSQL文を文字列として事前に設定
final static String SQLSHOW = "select * from books";*/

		// DB接続情報の設定
		final String URL = "jdbc:mysql://localhost:3306/sampledb?serverTimezone=JST";
		final String USER = "masayuki";
		final String PASS = "masayuki6";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			System.out.println("成功");

		} catch (SQLException e){
			System.out.println("失敗");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
