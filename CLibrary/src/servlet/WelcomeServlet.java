package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// URLエンコーディングの文字コードを設定
		request.setCharacterEncoding("utf-8");
		//フォワードを実行するメソッド（トップ画面へ）
		doForword(request, response,"index.jsp");
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// URLエンコーディングの文字コードを設定
		request.setCharacterEncoding("utf-8");

		//targetパラメータを取得
		String target = request.getParameter("target");

		// アクセス元のページによって処理を分岐
		switch (target) {
		case "register": //新規登録画面にフォワード
			doForword(request, response,"/CLibrary/LoginServlet");
			break;

		case "login": // ログイン画面にフォワード
			doForword(request, response, "/CLibrary/LoginServlet");
			break;

		case "select": // target=serectの場合
			List<BooksDTO> booksAllList = BooksDAO.showBooks();
			request.getSession().setAttribute("booksAllList", booksAllList);
			doForword(request, response, "/WEB-INF/jsp/show.jsp");
			break;
		}
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
