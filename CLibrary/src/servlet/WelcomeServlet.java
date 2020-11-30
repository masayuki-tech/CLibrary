package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.BooksDTO;
import model.BooksDAO;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WelcomeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		response.getWriter().append("Served at: ").append(request.getContextPath());

		// DB接続情報の設定
		final String URL = "jdbc:mysql://172.16.71.108:3306/sampledb?serverTimezone=JST";
		final String USER = "CLibary";
		final String PASS = "CLibrary01";

		String forword = "";

		// DB接続処理は例外処理が必須
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			// 接続に成功した場合、success.jspファイルにフォワード
			//			forword = "/WEB-INF/jsp/success.jsp";
			forword = "index.jsp";
		} catch (SQLException e) {
			// 接続に失敗した場合、fall.jspファイルにフォワード
			forword = "fail.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(forword);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		doGet(request, response);

		// DB接続情報の設定
		final String URL = "jdbc:mysql:localhost:3306/sampledb?serverTimezone=JST";
		final String USER = "masayuki";
		final String PASS = "masayuki6";

		String forword = "";
		// URLエンコーディングの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// name="target"のデータを抽出(どこからの通信かをチェックする)
		String target = request.getParameter("target");

		HttpSession session = request.getSession();

		// アクセス元のページによって処理を分岐
		switch (target) {
		case "register": // target=registerの場合
			// フォワード先を指定
			forword = "/CLibrary/LoginServlet";
			break;
		case "login": // target=loginの場合
			// フォワード先を指定
			forword = "/CLibrary/LoginServlet";
			break;
		case "select": // target=serectの場合
			// フォワード先を指定
			List<BooksDTO> booksAllList =BooksDAO.showBooks();
			session.setAttribute("booksAllList", booksAllList);
			forword = "/WEB-INF/jsp/show.jsp";
			break;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(forword);
		dispatcher.forward(request, response);
	}

}
