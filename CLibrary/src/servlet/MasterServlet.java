package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dto.BooksDTO;
import dto.EnvSet;
import dto.RentlogsDTO;
import model.MasterDAO;

/**
 * Servlet implementation class MasterServlet
 */
@WebServlet("/MasterServlet")
public class MasterServlet extends HttpServlet implements EnvSet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//proxyの設定
		System.setProperty("https.proxyHost", J701_HTTPS_PROXY_ADDRESS);
		System.setProperty("https.proxyPort", J701_HTTPS_PROXY_PORT);
	}

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//RequestDispatcher dispatcher=("master.jsp");
		//dispatcher.forward(request,response)
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/master.jsp");
		rd.forward(request, response);

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		MasterDAO MasterDAO = new MasterDAO();
		String forwardPath = "";
		switch (action) {
		case "done":


			// データベース処理を行うDAOを生成

			List<RentlogsDTO> limitOverList = new ArrayList<>();
			limitOverList = MasterDAO.getSearch();
			session.setAttribute("limitOver", limitOverList);
			forwardPath ="/WEB-INF/jsp/limitover.jsp";
			break;
		case "ok":

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
			//if(action.equals("done")) {
			//			HttpSession session = request.getSession();
			//
			//
			//			// データベース処理を行うDAOを生成
			//			MasterDAO MasterDAO = new MasterDAO();
			//			List<RentlogsDTO> limitOverList=new ArrayList<>();
			//			limitOverList =MasterDAO.getSearch();
			//			session.setAttribute("limitOver", limitOverList);
			//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/limitover.jsp");
			//			dispatcher.forward(request, response);
			//
			//		}
			//
			//		else if(action.equals("ok")) {
			//			HttpSession session = request.getSession();
			//
			//
			//			// データベース処理を行うDAOを生成
			//			MasterDAO MasterDAO = new MasterDAO();
			//			BooksDTO bookdata=new BooksDTO();
			//			bookdata = (BooksDTO) session.getAttribute("result");
			//			String forwardPath = "";
			//
			//			// DAO内に定義されているデータ登録用のメソッドを実行し、その結果を保存
			//			boolean isInsert = MasterDAO.insert(bookdata);
			//
			//			// メソッドの実行結果によって、切り替えるJSPファイル名を決定
			//			if (isInsert) {
			//				forwardPath = "/WEB-INF/jsp/addsuccess.jsp";
			//			} else {
			//				forwardPath ="/WEB-INF/jsp/addfailure.jsp";
			//			}

			// JSPファイルに処理を切り替え


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
		HttpSession session = request.getSession();

		// フォームからのデータを抽出
		request.setCharacterEncoding("UTF-8"); // URLエンコーディングの文字コードを設定
		int book_id = 0;
		String jan = request.getParameter("isbn");
		String pur_date = request.getParameter("pur_date");
		int rent_check = 0;

		//JSPページを呼び出すためのRequestDispatcher
		RequestDispatcher rd = null;

		//gooleへ接続するため
		URL url = null;
		HttpURLConnection con = null;

		//検索結果データ格納用
		List<BooksDTO> list;

		//FORMで押したボタン名の判別
		String button = request.getParameter("button_name");

		//接続URL
		String requestUrl = null;

		// もしbook_id列のデータがない場合
		if (pur_date == null || pur_date == "") {
			// エラーメッセージをセッションスコープに保存
			// 入力用フォームに再度リダイレクト
			response.sendRedirect("/error.jsp");
			rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);

			// book_id列にデータがある場合
		} else {
			// セッションスコープに保存しているエラーメッセージを削除
			session.removeAttribute("error");

			requestUrl = GOOGLE_BOOKS_API_ISBN + jan;
		}

		//Google Books APIへの接続
		try {
			//URLConnectionの作成
			url = new URL(requestUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");//GETリクエスト
			con.setReadTimeout(10000); // 10秒
			con.setConnectTimeout(10000);// 10秒
		} catch (Exception e) {
			//例外発生時、error.jspへフォワードする
			request.setAttribute("error", e.toString());
			rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			return;
		}

		//レスポンスコードの確認
		int responseCode = con.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			//接続を切断する
			con.disconnect();

			//レスポンスコードが200以外の場合は、error.jspへフォワードする
			request.setAttribute("error", "Google Books API　へのリクエストが失敗しました。レスポンスコード：" + responseCode);
			rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			return;
		}

		//検索結果データ(レスポンス)の取得
		//ストリーム作成用
		BufferedReader responseReader = null;
		InputStreamReader isr = null;

		//レスポンス全データ取得用
		StringBuilder builder = new StringBuilder();

		//レスポンスを取得するためのストリームの作成
		isr = new InputStreamReader(con.getInputStream());
		responseReader = new BufferedReader(isr);

		//レスポンスデータを1行取得する
		String line = responseReader.readLine();

		//nullになるまでデータを１行取り出し、builderへ追加する
		while (line != null) {
			builder.append(line);
			line = responseReader.readLine();
		}

		//取得したデータを文字列へ変換する
		String responseString = builder.toString();

		//接続を切断する
		con.disconnect();

		//JSONオブジェクト作成用
		JSONObject jsonObject = null;

		try {
			//取得した文字列からJSONオブジェクトを作成
			jsonObject = new JSONObject(responseString);

			//検索データ数 totalItemsを検索結果数としています
			//実際に検索して得られるデータは最大10個のようです
			int count = jsonObject.getInt("totalItems");

			//requestへcountを格納
			request.setAttribute("count", count);

			//検索結果0の場合、no_result.jspへフォワードする
			if (count == 0) {
				rd = request.getRequestDispatcher("/no_result.jsp");
				rd.forward(request, response);
				return;
			}

			//JSON配列itemsの取得
			JSONArray jsonArray = jsonObject.getJSONArray("items");

			//検索結果データの格納
			list = new ArrayList<BooksDTO>();

			//実際に得られるデータ数
			count = jsonArray.length();

			//
			//for (int i = 0; i < count; i++) {
			//各検索結果
			JSONObject item = jsonArray.getJSONObject(0);

			//volumeInfoに関するデータの取得
			JSONObject volumeInfo = item.getJSONObject("volumeInfo");

			JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
			//titleの取得
			String book_name = volumeInfo.getString("title");

			//authorsの取得
			JSONArray authors = null;
			String author = null;
			try {
				authors = volumeInfo.getJSONArray("authors");
				author = authors.getString(0);
			} catch (JSONException e) {
				author = "未登録";
			}
			//imageLinksの取得
			//JSONArray imageLinks = null;

			String image = null;
			try {
				//imageLinks = volumeInfo.getJSONArray("imageLinks");
				image = imageLinks.getString("thumbnail");

				//image = imageLinks.getString(0);

			} catch (JSONException e) {
				image = "未登録";
			}

			//publisherの取得
			String publisher = null;
			try {
				publisher = volumeInfo.getString("publisher");

			} catch (JSONException e) {
				publisher = "未登録";
			}

			//descriptionの取得
			String description = null;
			try {
				description = volumeInfo.getString("description");

			} catch (JSONException e) {
				description = "未登録";
			}

			//検索結果データの追加
			BooksDTO BooksDTO1 = new BooksDTO(book_id, jan, book_name, pur_date, rent_check, image,
					publisher, author, "a");
			//}

			//disp.jspへ渡すデータを格納
			session.setAttribute("result", BooksDTO1);

		} catch (Exception e) {
			//例外発生時、error.jspへフォワードする
			request.setAttribute("error", e.toString());
			rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
			return;
		}

		//isbn_result.jspへフォワードする
		rd = request.getRequestDispatcher("/confirm.jsp");
		rd.forward(request, response);
	}

	// 抽出されたデータを利用してJavaBeansを生成
	//BooksDTO BooksDTO = new BooksDTO(Integer.parseInt(book_id),jan,book_name,pur_date,rent_check);

	// 生成したJavaBeansをセッションスコープに保存(JSPファイルで共有するため)
	//session.setAttribute("books", BooksDTO);

	// データベース処理を行うDAOを生成
	//MasterDAO MasterDAO = new MasterDAO();

	//String forwardPath = "";

	// DAO内に定義されているデータ登録用のメソッドを実行し、その結果を保存
	//boolean isInsert = MasterDAO.insert(BooksDTO);

	// メソッドの実行結果によって、切り替えるJSPファイル名を決定
	//if (isInsert) {
	//forwardPath = "/WEB-INF/jsp/addsuccess.jsp";
	//} else {
	//	forwardPath ="/WEB-INF/jsp/addfailure.jsp";
	//}

	// JSPファイルに処理を切り替え
	//RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
	//dispatcher.forward(request, response);
}
