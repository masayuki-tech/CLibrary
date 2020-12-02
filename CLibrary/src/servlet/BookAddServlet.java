package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.BooksDTO;
import model.MasterDAO;

/**
 * Servlet implementation class BookAddServlet
 */
@WebServlet("/BookAddServlet")
public class BookAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookAddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getParameter("action");

		String forwardPath = "";
		switch (action) {
		case "done":
			HttpSession session = request.getSession();
			MasterDAO MasterDAO = new MasterDAO();

			// データベース処理を行うDAOを生成
			BooksDTO bookdata = new BooksDTO();
			bookdata = (BooksDTO) session.getAttribute("result");

			// DAO内に定義されているデータ登録用のメソッドを実行し、その結果を保存
			boolean isInsert = MasterDAO.insert(bookdata);

			// メソッドの実行結果によって、切り替えるJSPファイル名を決定
			if (isInsert) {
				forwardPath = "/WEB-INF/jsp/addsuccess.jsp";
			} else {
				forwardPath = "/WEB-INF/jsp/addfailure.jsp";

			}
break;

					}
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}