package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.StaffsDTO;
import model.MypageDAO;

/**
 * Servlet implementation class MypageServlet
 */
@WebServlet("/MypageServlet")
public class MypageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MypageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ＤＢ接続用
		final String URL = "jdbc:mysql://172.16.71.116:3306/clibrary?serverTimezone=JST";
		final String USER = "team1";
		final String PASS = "CLibrary";

		// DB接続処理は例外処理が必須
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			doForward(request, response, "/WEB-INF/jsp/booksearchMypage.jsp");

		} catch (SQLException e) {
			// 接続に失敗した場合、fall.jspファイルにフォワード
			doForward(request, response,"/WEB-INF/jsp/networkfaile.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int result = 3;
		// URLエンコーディングの文字コードを設定
		request.setCharacterEncoding("utf-8");

		// name="target"のデータを抽出(どこからの通信かをチェックする)
		String target = request.getParameter("target");

		//MypageDAOのインスタンスを生成
		MypageDAO mydao = new MypageDAO();

		// アクセス元のページによって処理を分岐**********************************************************
		switch (target) {
		case "return": //本の返却
			// 本を返却する自作メソッドを実行
			result = mydao.returnBook();
			if (result == 0) {
				//フォワードを実行
				doForward(request, response, "/WEB-INF/jsp/returnOk.jsp");
			} else {
				doForward(request, response, "/WEB-INF/jsp/test.jsp");
			}
			break;

		case "rent": //本を借りる
			// 本を借りる自作メソッドを実行
			result = mydao.rentBook();
			if (result == 0) {
				//フォワードするメソッドを実行
				doForward(request, response, "/WEB-INF/jsp/rentOk.jsp");

			} else if (result == 1) {
				doForward(request, response, "/WEB-INF/jsp/test2.jsp");
			}
			break;

		case "returnKuramoto"://返却ボタンが押された時の処理
			//返却用メソッドを実行してフォワード
			doReturn(request, response);
			break;

		case "rentKuramoto"://貸出ボタンが押された時の処理
			//貸出処理メソッドを実行
			doRent(request, response);
			break;
		}
		//**********************************************************
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//返却ボタンが押された時のメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void doReturn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//返却ボタンのbook_idとrent_idを取得
		int returnBookId = Integer.parseInt(request.getParameter("returnBookId"));
		int returnRentId = Integer.parseInt(request.getParameter("returnRentId"));

		//ＤＡＯのメソッドを実行してbooleanで受け取る
		boolean isReturn = new MypageDAO().setReturn(returnBookId, returnRentId);

		if (isReturn) {
			//成功したら返却完了のページへフォワード
			doForward(request, response, "/WEB-INF/jsp/returnSuccess.jsp");

		} else {
			//処理失敗したら失敗のページへフォワード
			doForward(request, response, "/WEB-INF/jsp/networkfaile.jsp");
		}
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//貸出手続きをするメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void doRent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//返却ボタンのbook_idを取得
		int rentBookId = Integer.parseInt(request.getParameter("rentBookId"));
		StaffsDTO sdRent = (StaffsDTO) request.getSession().getAttribute("sd");
		int rentStaffId = sdRent.getStaff_Id();

		//ＤＡＯのメソッドを実行してbooleanで受け取る
		boolean isRent = new MypageDAO().getCanRent(rentBookId, rentStaffId);

		//booleanで処理を分岐する
		if (isRent) {
			//成功したら貸出完了のメッセージ
			doForward(request, response, "/WEB-INF/jsp/rentSuccess.jsp");

		} else {
			//処理失敗の時
			doForward(request, response, "/WEB-INF/jsp/networkfaile.jsp");
		}
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//フォワードを実行するメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void doForward(HttpServletRequest request, HttpServletResponse response, String forwardPath)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);

	}
}
