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

import model.LoginDAO;
import model.staffsDTO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
		//文字コードを指定
		request.setCharacterEncoding("UTF-8");


		//ログイン画面にフォワード（login.jsp）
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/login.jsp");
		dispatcherLogin.forward(request, response);

		//新規登録画面にフォワード（register.jsp）
		RequestDispatcher dispatcherRegister = request.getRequestDispatcher("/register.jsp");
		dispatcherRegister.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//文字コードを指定
		request.setCharacterEncoding("UTF-8");
		//targetパラメーターを受け取る
		String target = request.getParameter("target");

		//切り替え********************************
		switch (target) {
		case "login":
			setLogin(request, response);
			break;

		case "register":
			setRegister(request, response);
			break;
		}
	}

	//ログイン用メソッド***********************************************************
	public void setLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pass = request.getParameter("pass");//パスワード
		String staffId = request.getParameter("staffId");//ユーザーＩＤ

		//パラメータの入力値をチェック
		if (pass != null && pass.length() != 0 && staffId != null && staffId.length() != 0) {

			//DAOのインスタンスを取得
			LoginDAO ld = new LoginDAO();

			//作成したDTOを格納するArrayListを生成
			List<staffsDTO> loginList = new ArrayList<>();

			//ＤＡＯにセッションスコープを渡して、ArrayListを受け取る
			loginList = ld.getLoginDAO(staffId, pass);

			//ログイン成功時の処理
			if (loginList != null) {
				//セッションスコープに保存
				HttpSession session = request.getSession();
				session.setAttribute("loginList", loginList);

				//mypageにフォワード（●●●.jsp）
				RequestDispatcher dispatcher = request.getRequestDispatcher("////////////.jsp");
				dispatcher.forward(request, response);

			} else {
				//ログイン失敗（login.jspにフォワード）
				RequestDispatcher dispatcher = request.getRequestDispatcher("/fail.jsp");
				dispatcher.forward(request, response);
			}
		}
	}

	//新規登録用メソッド******************************************************************************
	public void setRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//jspからパラメーターを受け取る
		String staffId = request.getParameter("staffId");//ＩＤ
		String pass = request.getParameter("pass");//パスワード
		String name = request.getParameter("name");//氏名
		int gender = Integer.parseInt(request.getParameter("gender"));//性別

		//パラメータの入力値をチェック
		if (staffId != null && staffId.length() != 0 && pass != null && pass.length() != 0 && name != null
				&& name.length() != 0) {

			//DAＯのインスタンスを取得
			LoginDAO ld = new LoginDAO();

			//データ結果をbooleanで受け取る
			boolean ok = ld.getRegisterDAO(Integer.parseInt(staffId), name, pass, gender);

			//結果がtrueなら
			if (ok) {
				//successのjspにフォワード
				RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/success.jsp");
				dispatcherLogin.forward(request, response);

			} else {

				//失敗画面に（fail.jspにフォワード）
				RequestDispatcher dispatcher = request.getRequestDispatcher("/fail.jsp");
				dispatcher.forward(request, response);
			}
		}

	}
}
