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

	/**************************************************************************************
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB接続情報の設定
		final String URL = "jdbc:mysql://172.16.71.108:3306/sampledb?serverTimezone=JST";
		final String USER = "CLibary";
		final String PASS = "CLibrary01";

		// URLエンコーディングの文字コードを設定
		request.setCharacterEncoding("utf-8");

		//フォワード先を指定
		String forword = "";

		// DB接続処理は例外処理が必須
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

			forword = "index.jsp";//フォワード先をトップ画面に指定

		} catch (SQLException e) {
			forword = "fail.jsp";// 接続に失敗した場合、fall.jspファイルにフォワード
		}
		//フォワードを実行するメソッド
		doForword(request, response, forword);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB接続情報の設定
		final String URL = "jdbc:mysql://172.16.71.108:3306/sampledb?serverTimezone=JST";
		final String USER = "CLibary";
		final String PASS = "CLibrary01";

		String forword = "";//フォワード先を指定

		// URLエンコーディングの文字コードを設定
		request.setCharacterEncoding("utf-8");

		// name="target"のデータを抽出(どこからの通信かをチェックする)
		String target = request.getParameter("target");

		// アクセス元のページによって処理を分岐
		switch (target) {
		case "register": // target=registerの場合
			forword = "/CLibrary/LoginServlet";// フォワード先を指定
			break;

		case "login": // target=loginの場合
			forword = "/CLibrary/LoginServlet";// フォワード先を指定
			break;

		case "select": // target=serectの場合
			List<BooksDTO> booksAllList = BooksDAO.showBooks();
			HttpSession session = request.getSession();
			session.setAttribute("booksAllList", booksAllList);
			forword = "/WEB-INF/jsp/show.jsp";// フォワード先を指定
			break;
		}
		//フォワードを実行するメソッド
		doForword(request, response, forword);
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//フォワードを実行するメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void doForword(HttpServletRequest request, HttpServletResponse response, String forword)
			throws ServletException, IOException {
		//フォワードを実行
		RequestDispatcher dispatcher = request.getRequestDispatcher(forword);
		dispatcher.forward(request, response);
	}

}
