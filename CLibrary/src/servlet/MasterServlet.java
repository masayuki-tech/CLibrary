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
import dto.RentlogsDTO;
import model.MasterDAO;

/**
 * Servlet implementation class MasterServlet
 */
@WebServlet("/MasterServlet")
public class MasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MasterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//RequestDispatcher dispatcher=("master.jsp");
		//dispatcher.forward(request,response)


		String action =request.getParameter("action");
		if(action.equals("done")) {
			HttpSession session = request.getSession();


			// データベース処理を行うDAOを生成
			MasterDAO MasterDAO = new MasterDAO();
			List<RentlogsDTO> limitOverList=new ArrayList<>();
			limitOverList =MasterDAO.getSearch();
			session.setAttribute("limitOver", limitOverList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/limitover.jsp");
			dispatcher.forward(request, response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();

		// フォームからのデータを抽出
		request.setCharacterEncoding("utf-8"); // URLエンコーディングの文字コードを設定
		String book_id = request.getParameter("book_id");
		String jan = request.getParameter("jan");
		String book_name = request.getParameter("book_name");
		String pur_date = request.getParameter("pur_date");
        int rent_check=0;
		// もしbook_id列のデータがない場合
				if (book_id == null || book_id == "") {
					// エラーメッセージをセッションスコープに保存
					session.setAttribute("error", "入力されていません。再度入力してください");
					// 入力用フォームに再度リダイレクト
					response.sendRedirect("/WEB-INF/jsp/master.jsp");

				// book_id列にデータがある場合
				} else {
					// セッションスコープに保存しているエラーメッセージを削除
					session.removeAttribute("error");

					// 抽出されたデータを利用してJavaBeansを生成
					BooksDTO BooksDTO = new BooksDTO(Integer.parseInt(book_id),jan,book_name, pur_date,rent_check);

					// 生成したJavaBeansをセッションスコープに保存(JSPファイルで共有するため)
					session.setAttribute("books", BooksDTO);

					// データベース処理を行うDAOを生成
					MasterDAO MasterDAO = new MasterDAO();

					String forwardPath = "";

					// DAO内に定義されているデータ登録用のメソッドを実行し、その結果を保存
					boolean isInsert = MasterDAO.insert(BooksDTO);

					// メソッドの実行結果によって、切り替えるJSPファイル名を決定
					if (isInsert) {
						forwardPath = "/WEB-INF/jsp/addsuccess.jsp";
					} else {
						forwardPath ="/WEB-INF/jsp/addfailure.jsp";
					}

					// JSPファイルに処理を切り替え
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}
			}

	}


