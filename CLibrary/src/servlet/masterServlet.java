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

import model.booksDTO;
import model.rentlogsDTO;

/**
 * Servlet implementation class masterServlet
 */
@WebServlet("/masterServlet")
public class masterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public masterServlet() {
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
			masterDAO masterDAO = new masterDAO();
			List<rentlogsDTO> limitOverList=new ArrayList<>();
			limitOverList=masterDAO.getSearch();
			session.setAttribute("limitOver", limitOverList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("limitover.jsp");
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
		
		// もしbook_id列のデータがない場合
				if (book_id == null || book_id == "") {
					// エラーメッセージをセッションスコープに保存
					session.setAttribute("error", "入力されていません。再度入力してください");
					// 入力用フォームに再度リダイレクト
					response.sendRedirect("master.jsp");

				// book_id列にデータがある場合
				} else {
					// セッションスコープに保存しているエラーメッセージを削除
					session.removeAttribute("error");

					// 抽出されたデータを利用してJavaBeansを生成
					booksDTO booksDTO = new booksDTO(Integer.parseInt(book_id),jan,book_name, pur_date);

					// 生成したJavaBeansをセッションスコープに保存(JSPファイルで共有するため)
					session.setAttribute("books", booksDTO);

					// データベース処理を行うDAOを生成
					masterDAO masterDAO = new masterDAO();

					String forwardPath = "";

					// DAO内に定義されているデータ登録用のメソッドを実行し、その結果を保存
					boolean isInsert = masterDAO.insert(booksDTO);

					// メソッドの実行結果によって、切り替えるJSPファイル名を決定
					if (isInsert) {
						forwardPath = "addsuccess.jsp";
					} else {
						forwardPath ="addfailure.jsp";
					}

					// JSPファイルに処理を切り替え
					RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
					dispatcher.forward(request, response);
				}
			}

	}


