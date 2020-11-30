package servlet;

//ログイン系の補助サーブレット
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.ForListDTO;
import dto.StaffsDTO;
import model.LoginDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	//管理者の情報************************************************
	final String MM = "aaa@aaa";
	final String MM_PASS = "000";
	//************************************************************
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8"); //文字コードを指定
		String target = request.getParameter("target"); //targetパラメーターを受け取る
		String forwardPath = ""; //フォワード先のパス指定

		//切り替え*********************************************
		switch (target) {
		case "login":
			//フォワード先をログイン画面に指定（login.jsp）
			forwardPath = "/WEB-INF/jsp/login.jsp";
			break;

		case "register":
			//フォワード先を新規登録画面に指定（register.jsp）
			forwardPath = "/WEB-INF/jsp/register.jsp";
			break;
		}
		//****************************************************

		//フォワードを実行するメソッド
		doForward(request, response, forwardPath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8"); //文字コードを指定
		String target = request.getParameter("target"); //targetパラメーターを受け取る

		//切り替え*********************************************
		switch (target) {
		case "login": //ログイン
			setLogin(request, response);//ログイン画面のメソッドへ
			break;

		case "register": //新規登録
			setRegister(request, response);//新規登録のメソッドへ
			break;
		case "toMypage"://マイページに戻る
			//セッションスコープとリストを消去
			setRemove(request);
			toMypage(request, response);//マイページに戻るメソッドへ
			break;
		}
		//****************************************************
	}

	//************************************************************************************************
	//ログイン用のメインメソッド
	//************************************************************************************************
	public void setLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String forwardPath = ""; //フォワード先の指定
		String mail = request.getParameter("mail");//メールアドレス
		String pass = request.getParameter("pass");//パスワード

		//パラメータの入力値をチェック
		if (pass != null && pass.length() != 0 && mail != null && mail.length() != 0) {

			if (mail.equals(MM) && pass.equals(MM_PASS)) {
				//フォワード先を管理者ページに指定
				forwardPath = "/WEB-INF/jsp/master.jsp";
				//従業員のログイン認証を行う*******************************************
			} else {
				//DAOのインスタンスを取得
				LoginDAO ld = new LoginDAO();

				//ログイン用DAOメソッドを実行し、StaffsDTOインスタンスを取得
				StaffsDTO sd = ld.getLoginDAO(mail, pass);

				//sdがnullじゃなければログイン成功************************************************
				if (sd != null) {
					//ログインユーザーのStaffsDTOインスタンスをセッションスコープに保存
					HttpSession session = request.getSession();
					session.setAttribute("sd", sd);

					//マイページに表示する「現在借りている本の一覧」を取得するメソッドの実行
					setRentNowList(request, response, mail, pass);

					//マイページに表示する「借りられる本の一覧」を取得するメソッドの実行
					setCanRentList(request, response);

					//Myページにフォワード先を指定
					forwardPath = "/WEB-INF/jsp/mypage.jsp";

					//ログイン認証に失敗した時**********************************************
				} else {
					//ログインに失敗しましたにフォワード先を指定
					forwardPath = "/WEB-INF/jsp/loginfaile.jsp";
				}
			}
		} else {
			//ログインに失敗しましたにフォワード先を指定
			forwardPath = "/WEB-INF/jsp/loginfaile.jsp";
		}
		//フォワードを実行するメソッド
		doForward(request, response, forwardPath);
	}

	//************************************************************************************************
	//新規登録用メソッド
	//************************************************************************************************
	public void setRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String forwardPath = ""; //フォワード先の指定
		//jspからパラメーターを受け取る
		String name = request.getParameter("name");//氏名
		String mail = request.getParameter("mail");//メールアドレス
		String pass = request.getParameter("pass");//パスワード
		String pass2 = request.getParameter("pass2");//パスワード確認用
		int gender = Integer.parseInt(request.getParameter("gender"));//性別

		if (pass.equals(pass2)) {
			//パラメータの入力値をチェック
			if (name != null && name.length() != 0 && pass != null && pass.length() != 0 && mail != null
					&& mail.length() != 0) {

				//DAＯのインスタンスを取得
				LoginDAO dao = new LoginDAO();

				//データ結果をbooleanで受け取る
				boolean isRegister = dao.getRegisterDAO(name, mail, pass, gender);

				//結果がtrueなら
				if (isRegister) {
					//ユーザーの情報を取得するメソッド
					StaffsDTO sd = dao.getUserDAO(name, mail, pass, gender);

					//マイページに表示する「現在借りている本の一覧」を取得するメソッドの実行
					setRentNowList(request, response, mail, pass);

					//セッションスコープに保存
					HttpSession session = request.getSession();
					session.setAttribute("sd", sd);

					//新規登録成功のjspにフォワード
					forwardPath = "/WEB-INF/jsp/success.jsp";

				} else {
					//登録失敗画面にフォワード
					forwardPath = "/WEB-INF/jsp/registerfaile.jsp";
				}
			}
		} else {
			//登録失敗画面にフォワード
			forwardPath = "/WEB-INF/jsp/registerfaile.jsp";
		}
		//フォワードを実行するメソッド
		doForward(request, response, forwardPath);
	}

	//****************************************************************************************
	//フォワード実行のメソッド
	//****************************************************************************************
	public void doForward(HttpServletRequest request, HttpServletResponse response, String forwardPath)
			throws ServletException, IOException {
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher(forwardPath);
		dispatcherLogin.forward(request, response);
	}

	//****************************************************************************************
	//現在借りている本のリストを取得する
	//****************************************************************************************
	public void setRentNowList(HttpServletRequest request, HttpServletResponse response, String mail, String pass)
			throws ServletException, IOException {
		//マイページに表示するためのArrayListを宣言
		List<ForListDTO> rentNowList = new ArrayList<>();
		//daoインスタンスを取得
		LoginDAO dao = new LoginDAO();
		//現在借りている本リストを取得するdaoのメソッドを実行
		rentNowList = dao.getRentNowListDAO(mail, pass);
		//ログインユーザーのStaffsDTOインスタンスをセッションスコープに保存
		HttpSession session = request.getSession();
		session.setAttribute("rentNowList", rentNowList);

	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//マイページに戻るだけのメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void toMypage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//現在借りているリストの更新
		updateRentNowList(request, response);
		//マイページに表示する「借りられる本の一覧」を更新
		setCanRentList(request, response);
		//フォワードを実行
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/WEB-INF/jsp/mypage.jsp");
		dispatcherLogin.forward(request, response);
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//Myページに戻る前に、現在借りている本のリストを更新するメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void updateRentNowList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//セッションからインスタンスを取得
		HttpSession session = request.getSession();
		StaffsDTO sd = (StaffsDTO) session.getAttribute("sd");
		String mail = sd.getMail();
		String pass = sd.getPass();
		//マイページに表示する「現在借りている本の一覧」を取得するメソッドの実行
		setRentNowList(request, response, mail, pass);
	}
	//****************************************************************************************
		//借りられる本のリストを取得する
		//****************************************************************************************
		public void setCanRentList(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			//マイページに表示するためのArrayListを宣言
			List<ForListDTO> canRentList = new ArrayList<>();
			//daoインスタンスを取得
			LoginDAO dao = new LoginDAO();
			//現在借りている本リストを取得するdaoのメソッドを実行
			canRentList = dao.getCanRentListDAO();
			//ログインユーザーのStaffsDTOインスタンスをセッションスコープに保存
			HttpSession session = request.getSession();
			session.setAttribute("canRentList", canRentList);

		}
		//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		//セッションスコープを削除するメソッド
		//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		public void setRemove(HttpServletRequest request) {
			HttpSession session = request.getSession();
			session.removeAttribute("canRentList");
			session.removeAttribute("rentNowList");
//			//rentNowListの削除
//			rentNowList.clear();
//			canRentList.clear();
		}
}
