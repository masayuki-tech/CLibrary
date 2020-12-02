package servlet;

//本を検索するサーブレット
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
		// TODO Auto-generated constructor stub
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

	//**********************************************************
	//あいまい検索Mypageより
	//**********************************************************
	public void toLikeSearch(String text, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookSearchDAO kd = new BookSearchDAO();
		List<BooksDTO> searchResult = new ArrayList<>();
		searchResult = kd.likeSearchDAO(text);
		HttpSession session = request.getSession();
		session.setAttribute("searchResult", searchResult);
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/WEB-INF/jsp/searchResultMypage.jsp");
		dispatcherLogin.forward(request, response);
	}

	//**********************************************************
	//あいまい検索Mainより
	//**********************************************************
	public void toLikeSearch2(String text2, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookSearchDAO kd = new BookSearchDAO();
		List<BooksDTO> searchResult2 = new ArrayList<>();
		searchResult2 = kd.likeSearchDAO2(text2);
		HttpSession session = request.getSession();
		session.setAttribute("searchResult2", searchResult2);
		RequestDispatcher dispatcherLogin = request.getRequestDispatcher("/WEB-INF/jsp/searchResultMain.jsp");
		dispatcherLogin.forward(request, response);
	}

}
