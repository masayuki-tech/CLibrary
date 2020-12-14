package servlet;

//ログイン系の補助サーブレット
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.BooksDTO;
import dto.ForListDTO;
import dto.StaffsDTO;
import model.LoginDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	//管理者の情報************************************************
	final String MM = "admin@com";
	final String MM_PASS = "0000000";
	//************************************************************
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
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
		case "login"://ログイン画面へ
			forwardPath = "/WEB-INF/jsp/login.jsp";
			break;

		case "register"://新規登録画面へ
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
			toLogin(request, response);//ログイン画面のメソッドへ
			break;

		case "register": //新規登録
			toRegister(request, response);//新規登録のメソッドへ
			break;

		case "toMypage"://マイページに戻る
			toMypage(request, response);//マイページに戻るメソッドへ
			break;
		}
		//****************************************************
	}

	//*******************************************************************
	//ログインするメソッド
	//*******************************************************************
	public void toLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String forwardPath = ""; //フォワード先の指定
		String mail = request.getParameter("mail");//メールアドレス
		String pass = request.getParameter("pass");//パスワード

		//パラメータの入力値をチェック
		if (pass != null && pass.length() != 0 && mail != null && mail.length() != 0) {
			//管理者のログインなら管理者ページにフォワード先を指定
			if (mail.equals(MM) && pass.equals(MM_PASS)) {
				//フォワード先を管理者ページに指定
				forwardPath = "/WEB-INF/jsp/mastermain.jsp";

				//ユーザーのログイン認証を行う*******************************************
			} else {
				//ログイン認証できるかを確認
				boolean connect = new LoginDAO().connectLogin(mail, pass);

				//ログイン成功！************************************************
				if (connect) {
					//ユーザー情報を取得する
					StaffsDTO sd = new LoginDAO().getLoginUser(mail, pass);

					//ログインユーザーのStaffsDTOインスタンスをセッションスコープに保存
					request.getSession().setAttribute("sd", sd);

					//マイページに表示する「借りている本の一覧」を取得するメソッドの実行
					setRentNowList(request, response, mail, pass);

					//マイページに表示する「借りられる本の一覧」を取得するメソッドの実行
					setCanRentList(request, response);

					//人気の本TOP5
					top5(request, response);

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
	public void toRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//フォワード先の指定（初期値をregisterfaile.jspに設定）
		String forwardPath = "/WEB-INF/jsp/registerfaile.jsp";

		//パラメーターを受け取る
		String name = request.getParameter("name");//氏名
		String mail = request.getParameter("mail");//メールアドレス
		String pass = request.getParameter("pass");//パスワード
		String pass2 = request.getParameter("pass2");//パスワード確認用
		int gender = Integer.parseInt(request.getParameter("gender"));//性別

		if (pass.equals(pass2) && pass.length() >= 7) {
			//パラメータの入力値をチェック
			if (name != null && name.length() != 0 && pass != null && pass.length() != 0 && mail != null
					&& mail.length() != 0) {

				//データ結果をbooleanで受け取る
				boolean isRegister = new LoginDAO().getRegisterDAO(name, mail, pass, gender);

				//結果がtrueなら
				if (isRegister) {
					//ユーザーの情報を取得するメソッド
					StaffsDTO sd = new LoginDAO().getUserDAO(name, mail, pass, gender);

					//セッションスコープに保存
					request.getSession().setAttribute("sd", sd);

					//マイページに表示する「現在借りている本の一覧」を取得するメソッドの実行
					setRentNowList(request, response, mail, pass);

					//マイページに表示する「借りられる本の一覧」を取得するメソッドの実行
					setCanRentList(request, response);

					//人気の本TOP5
					top5(request, response);

					//新規登録成功にフォワード
					forwardPath = "/WEB-INF/jsp/success.jsp";

				} else {
					//登録失敗画面にフォワード（初期値のまま）
				}
			} else {
				//登録失敗画面にフォワード（初期値のまま）
			}
		} else {
			//登録失敗画面にフォワード（初期値のまま）
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

		//現在借りている本リストを取得するdaoのメソッドを実行
		List<ForListDTO> rentNowList = new LoginDAO().getRentNowListDAO(mail, pass);

		//借りている本のリストをセッションスコープに保存
		HttpSession session = request.getSession();
		session.setAttribute("rentNowList", rentNowList);
	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//マイページに戻るだけのメソッド
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void toMypage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//現在借りているリストの取得メソッドを実行
		updateRentNowList(request, response);

		//借りられる本のリストを取得メソッドを実行
		setCanRentList(request, response);

		//人気の本TOP5メソッドを実行
		top5(request, response);

		//フォワードするメソッドを実行してmypage.jspへ
		doForward(request, response, "/WEB-INF/jsp/mypage.jsp");
	}

	//**********************************************************
	//Myページに戻る前に、現在借りている本のリストを取得するメソッド
	//**********************************************************
	public void updateRentNowList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッションからsdインスタンスを取得
		StaffsDTO sd = (StaffsDTO) request.getSession().getAttribute("sd");
		String mail = sd.getMail();//メールアドレス
		String pass = sd.getPass();//パスワード

		//マイページに表示する「現在借りている本の一覧」を取得するメソッドの実行
		setRentNowList(request, response, mail, pass);
	}

	//****************************************************************************************
	//借りられる本のリストを取得する
	//****************************************************************************************
	public void setCanRentList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//現在借りている本リストを取得するdaoのメソッドを実行
		List<ForListDTO>canRentList = new LoginDAO().getCanRentListDAO();

		//ログインユーザーのStaffsDTOインスタンスをセッションスコープに保存
		request.getSession().setAttribute("canRentList", canRentList);
	}

	//**********************************************************
	//セッションスコープを削除するメソッド
	//**********************************************************
	public void setRemove(HttpServletRequest request) {
		request.getSession().removeAttribute("sd");
		request.getSession().removeAttribute("canRentList");
		request.getSession().removeAttribute("rentNowList");
	}

	//**********************************************************
	//人気の本ランキングＴＯＰ5を取得するメソッド
	//**********************************************************
	public void top5(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//daoのメソッドを実行
		List<BooksDTO>top5List = new LoginDAO().getTop5();

		//人気ランキングTop5の一覧をセッションスコープに保存
		request.getSession().setAttribute("top5List", top5List);
	}

}
