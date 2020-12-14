package servlet;

//本を検索するサーブレット
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BooksDTO;
import model.BookSearchDAO;

/**
 * Servlet implementation class BookSearchServlet
 */
@WebServlet("/BookSearchServlet")
public class BookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookSearchServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//文字コードを指定
		String target = request.getParameter("target"); //targetパラメーターを受け取る

		switch (target) {
		case "likeSearch"://あいまい検索Mypageより
			String text = request.getParameter("text"); //textパラメーターを受け取る
			toLikeSearch(text, request, response);
			break;

		case "likeSearchMain"://あいまい検索Mainより
			String text2 = request.getParameter("text"); //textパラメーターを受け取る
			toLikeSearch2(text2, request, response);
			break;
		}

	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//あいまい検索Mypageより
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void toLikeSearch(String text, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//BookSearchDAOのメソッドを実行してArrayListを取得
		List<BooksDTO> searchResult = new BookSearchDAO().likeSearchDAO(text);

		//セッションスコープに保存
		request.getSession().setAttribute("searchResult", searchResult);

		//フォワードするメソッドを実行（searchResultMypage.jspへ）
		doForward(request, response, "/WEB-INF/jsp/searchResultMypage.jsp");

	}

	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	//あいまい検索Mainより
	//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
	public void toLikeSearch2(String text2, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//likeSearchDAO2を実行し、ArrayListを取得
		List<BooksDTO> searchResult2 = new BookSearchDAO().likeSearchDAO2(text2);

		//セッションスコープに保存
		request.getSession().setAttribute("searchResult2", searchResult2);

		//フォワードするメソッドを実行（searchResultMain.jspへ）
		doForward(request, response, "/WEB-INF/jsp/searchResultMain.jsp");
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
