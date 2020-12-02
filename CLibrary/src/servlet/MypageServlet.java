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
import javax.servlet.http.HttpSession;

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

		String forword = "";

		// DB接続処理は例外処理が必須
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			// 接続に成功した場合、success.jspファイルにフォワード
			//			forword = "/WEB-INF/jsp/success.jsp";

			forword = "/WEB-INF/jsp/booksearchMypage.jsp";
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

		String forword = "";
		int result = 3;
		// URLエンコーディングの文字コードを設定
		request.setCharacterEncoding("utf-8");

		// name="target"のデータを抽出(どこからの通信かをチェックする)
		String target = request.getParameter("target");

		//MypageDAOのインスタンスを生成
		MypageDAO mydao = new MypageDAO();

		// アクセス元のページによって処理を分岐
		switch (target) {
		case "return": // target=returnの場合

			// 本を返却する自作メソッドを実行
			result = mydao.returnBook();
			if (result == 0) {
				forword = "/WEB-INF/jsp/returnOk.jsp";
			} else {
				forword = "/WEB-INF/jsp/test.jsp";
			}
			break;

		case "rent": // target=cat_keywordの場合
			// 本を借りる自作メソッドを実行
			result = mydao.rentBook();
			if (result == 0) {
				forword = "/WEB-INF/jsp/rentOk.jsp";
			} else if (result == 1) {
				forword = "/WEB-INF/jsp/test2.jsp";
			}
			break;

		case "returnKuramoto"://返却ボタンが押された時の処理
			//メソッドを実行して、フォワードまで行う
			forword = doReturn(request, response);
			break;

		case "rentKuramoto"://貸出ボタンが押された時の処理
			//貸出処理メソッドを実行
			forword = doRent(request, response);
			break;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(forword);
		dispatcher.forward(request, response);
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//返却ボタンが押された時のメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public String doReturn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//フォワード先
		String forword;
		//返却ボタンのbook_idとrent_idを取得
		int returnBookId = Integer.parseInt(request.getParameter("returnBookId"));
		int returnRentId = Integer.parseInt(request.getParameter("returnRentId"));
		//ＤＡＯのインスタンスを生成
		MypageDAO dao1 = new MypageDAO();
		//ＤＡＯのメソッドを実行してbooleanで受け取る
		boolean isReturn = dao1.setReturn(returnBookId, returnRentId);

		//booleanで処理を分岐する
		if (isReturn) {
			//成功したら返却完了のメッセージ
			forword = "/WEB-INF/jsp/returnSuccess.jsp";
		} else {
			//処理失敗の時
			forword = "/WEB-INF/jsp/fail.jsp";
		}
		return forword;
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//貸出手続きをするメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public String doRent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String forword;//フォワード先

		//返却ボタンのbook_idを取得
		int rentBookId = Integer.parseInt(request.getParameter("rentBookId"));
		HttpSession session = request.getSession();
		StaffsDTO sdRent=(StaffsDTO)session.getAttribute("sd");
		int rentStaffId = sdRent.getStaff_Id();
		//Integer.parseInt(request.getParameter("rentStaffId"));
		//ＤＡＯのインスタンスを生成
		MypageDAO dao2 = new MypageDAO();
		//ＤＡＯのメソッドを実行してbooleanで受け取る
		boolean isRent = dao2.getCanRent(rentBookId, rentStaffId);
		//booleanで処理を分岐する
		if (isRent) {
			//成功したら貸出完了のメッセージ
			forword = "/WEB-INF/jsp/rentSuccess.jsp";
		} else {
			//処理失敗の時
			forword = "/WEB-INF/jsp/fail.jsp";
		}
		return forword;
	}
}
