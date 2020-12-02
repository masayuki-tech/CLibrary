package servlet;

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

import dto.BooksDTO;
import dto.ForListDTO;
import dto.StaffsDTO;
import model.KuramotoDAO;

/**
 * Servlet implementation class KuramotoServlet
 */
@WebServlet("/KuramotoServlet")
public class KuramotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KuramotoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//文字コードを指定

		//**********************************************************
		//全従業員一覧表示テスト
		//**********************************************************

		//ＤＡＯのインスタンスを取得
		KuramotoDAO kdao1 = new KuramotoDAO();

		//ArrayListを作成して、クリア処理
		List<StaffsDTO> staffsListAll = new ArrayList<>();
		staffsListAll.clear();

		//メソッドallStaffsList()を実行
		staffsListAll = kdao1.allStaffsList();

		//全従業員の一覧をセッションスコープに保存
		HttpSession session = request.getSession();
		session.setAttribute("staffsListAll", staffsListAll);

		//**********************************************************
		//全本の一覧表示テスト
		//**********************************************************

		//ＤＡＯのインスタンスを取得
		KuramotoDAO kdao2 = new KuramotoDAO();

		//ArrayListを作成して、クリア処理
		List<BooksDTO> booksListAll = new ArrayList<>();
		booksListAll.clear();

		//メソッドallStaffsList()を実行
		booksListAll = kdao2.allBooksList();

		//全従業員の一覧をセッションスコープに保存
		//HttpSession session = request.getSession();
		session.setAttribute("booksListAll", booksListAll);

		//**********************************************************
		//★人気の本ランキングＴＯＰ3
		//**********************************************************
		//ＤＡＯのインスタンスを取得
		KuramotoDAO kdao3 = new KuramotoDAO();

		//ArrayListを作成して、クリア処理
		List<BooksDTO> top3List = new ArrayList<>();
		top3List.clear();

		//daoのメソッドを実行
		top3List = kdao3.getTop3();

		//人気ランキングTop10の一覧をセッションスコープに保存
		//HttpSession session = request.getSession();
		session.setAttribute("top3List", top3List);

		//**********************************************************
		//全貸出履歴リスト
		//**********************************************************
		//ＤＡＯのインスタンスを取得
		KuramotoDAO kdao4 = new KuramotoDAO();

		//ArrayListを作成して、クリア処理
		List<ForListDTO> rentlogsAll = new ArrayList<>();
		rentlogsAll.clear();

		//メソッドallStaffsList()を実行
		rentlogsAll = kdao4.rentLogsAllList();

		//全従業員の一覧をセッションスコープに保存
		//HttpSession session = request.getSession();
		session.setAttribute("rentlogsAll", rentlogsAll);

		//**********************************************************
		//全貸出中リスト
		//**********************************************************
		//ＤＡＯのインスタンスを取得
		KuramotoDAO kdao5 = new KuramotoDAO();

		//ArrayListを作成して、クリア処理
		List<ForListDTO> rentNowAll = new ArrayList<>();
		rentNowAll.clear();

		//メソッドallStaffsList()を実行
		rentNowAll = kdao5.rentNowAllList();

		//全従業員の一覧をセッションスコープに保存
		//HttpSession session = request.getSession();
		session.setAttribute("rentNowAll", rentNowAll);

		//**********************************************************
		//２週間以上借りているリスト
		//**********************************************************
		//ＤＡＯのインスタンスを取得
		KuramotoDAO kdao6 = new KuramotoDAO();

		//ArrayListを作成して、クリア処理
		List<ForListDTO> overRentList = new ArrayList<>();
		overRentList.clear();

		//メソッドallStaffsList()を実行
		overRentList = kdao6.overRent();

		//全従業員の一覧をセッションスコープに保存
		//HttpSession session = request.getSession();
		session.setAttribute("overRentList", overRentList);

		//**********************************************************
		//フォワードを実行
		//**********************************************************
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/Kuramoto.jsp");
		dispatcherLogin.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//文字コードを指定
		String target = request.getParameter("target"); //targetパラメーターを受け取る

		switch (target) {
		case "likeSearch"://あいまい検索
			String text = request.getParameter("text"); //textパラメーターを受け取る
			toLikeSearch(text,request,response);
			break;
		}

		//		//**********************************************************
		//		//パスワードの変更ボタンが押された時
		//		//**********************************************************
		//		switch(target) {
		//		case "changePass":
		//			String staffId= request.getParameter("staffId"); //社員ID
		//			String mail=request.getParameter("mail");//メールアドレス
		//			String pass=request.getParameter("pass");//パスワード
		//			break;
		//		}

		//**********************************************************
		//フォワードを実行
		//**********************************************************
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/Kuramoto.jsp");
		dispatcherLogin.forward(request, response);
	}

	//**********************************************************
	//あいまい検索
	//**********************************************************
	public void toLikeSearch(String text,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		KuramotoDAO kd=new KuramotoDAO();
		List<BooksDTO> searchResult=new ArrayList<>();
		searchResult=kd.likeSearchDAO(text);
		HttpSession session = request.getSession();
		session.setAttribute("searchResult", searchResult);
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/Kuramoto.jsp");
		dispatcherLogin.forward(request, response);
	}

}
