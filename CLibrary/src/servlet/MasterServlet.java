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
import dto.ForListDTO;
import dto.RentlogsDTO;
import dto.StaffsDTO;
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

		String action = request.getParameter("action");

		if (action.equals("done")) {

			// データベース処理を行うDAOを生成
			//MasterDAO MasterDAO = new MasterDAO();
			List<RentlogsDTO> limitOverList = new ArrayList<>();
			limitOverList = new MasterDAO().getSearch();
			request.getSession().setAttribute("limitOver", limitOverList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/limitover.jsp");
			dispatcher.forward(request, response);

		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/mastermain.jsp");
			rd.forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		// フォームからのデータを抽出
		request.setCharacterEncoding("UTF-8"); // URLエンコーディングの文字コードを設定
		//actionパラメーターを受け取る
		String target = request.getParameter("target");

		int book_id = 0;

		switch (target) {
		//**********************************************************
		//本の登録ボタンが押された時
		//**********************************************************
		case "bookinput":
			String jan = request.getParameter("isbn");
			String pur_date = request.getParameter("pur_date");
			//String pur_date=pur_date0.replace("/", "");
			int rent_check = 0;

			//JSPページを呼び出すためのRequestDispatcher
			RequestDispatcher ra = null;

			//gooleへ接続するため
			URL url = null;
			HttpURLConnection con = null;

			//FORMで押したボタン名の判別
			String button = request.getParameter("button_name");

			//接続URL
			String requestUrl = null;

			// もしbook_id列のデータがない場合
			if (pur_date == null || pur_date == "") {
				// エラーメッセージをセッションスコープに保存
				// 入力用フォームに再度リダイレクト
				response.sendRedirect("/error.jsp");
				ra = request.getRequestDispatcher("/error.jsp");
				ra.forward(request, response);

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
				ra = request.getRequestDispatcher("/error.jsp");
				ra.forward(request, response);
				return;
			}

			//レスポンスコードの確認
			int responseCode = con.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				//接続を切断する
				con.disconnect();

				//レスポンスコードが200以外の場合は、error.jspへフォワードする
				request.setAttribute("error", "Google Books API　へのリクエストが失敗しました。レスポンスコード：" + responseCode);
				ra = request.getRequestDispatcher("/error.jsp");
				ra.forward(request, response);
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
					ra = request.getRequestDispatcher("/no_result.jsp");
					ra.forward(request, response);
					return;
				}

				//JSON配列itemsの取得
				JSONArray jsonArray = jsonObject.getJSONArray("items");

				//検索結果データの格納
				List<BooksDTO> list = new ArrayList<BooksDTO>();

				//実際に得られるデータ数
				count = jsonArray.length();

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

				//publisherの取得**********************************************************
				String publisher = null;
				try {
					publisher = volumeInfo.getString("publisher");

				} catch (JSONException e) {
					publisher = "未登録";
				}

				//descriptionの取得**********************************************************
				String description = null;
				try {
					description = volumeInfo.getString("description");

				} catch (JSONException e) {
					description = "未登録";
				}

				//検索結果データの追加**********************************************************
				BooksDTO BooksDTO1 = new BooksDTO(book_id, jan, book_name, pur_date, rent_check, image,
						publisher, author, description);

				//disp.jspへ渡すデータを格納
				session.setAttribute("result", BooksDTO1);

			} catch (Exception e) {
				//例外発生時、error.jspへフォワードする
				request.setAttribute("error", e.toString());
				ra = request.getRequestDispatcher("/error.jsp");
				ra.forward(request, response);
				return;
			}

			//isbn_result.jspへフォワードする
			ra = request.getRequestDispatcher("/WEB-INF/jsp/confirm.jsp");
			ra.forward(request, response);
			break;

		//**********************************************************
		//登録ユーザー(従業員)の一覧
		//**********************************************************
		case "staffAll":

			//メソッドallStaffsList()を実行
			List<StaffsDTO> staffsListAll = new MasterDAO().allStaffsList();

			//全従業員の一覧をセッションスコープに保存
			session.setAttribute("staffsListAll", staffsListAll);

			//フォワードするメソッドを実行
			doForward(request, response, "/WEB-INF/jsp/stafflist.jsp");

			break;

		//**********************************************************
		//管理者ページに戻る
		//**********************************************************
		case "tomaster":
			//フォワードするメソッドを実行
			doForward(request, response, "/WEB-INF/jsp/mastermain.jsp");
			break;

		//**********************************************************
		//全本の一覧表示テスト
		//**********************************************************
		case "booksAll":

			//メソッドallStaffsList()を実行
			List<BooksDTO> booksListAll = new MasterDAO().allBooksList();

			//全従業員の一覧をセッションスコープに保存
			session.setAttribute("booksListAll", booksListAll);

			//フォワードするメソッドを実行
			doForward(request, response, "/WEB-INF/jsp/showbooksall.jsp");

			break;

		//**********************************************************
		//全貸出履歴リスト
		//**********************************************************
		case "rentAll":

			//メソッドallStaffsList()を実行
			List<ForListDTO> rentlogsAll = new MasterDAO().rentLogsAllList();

			//全従業員の一覧をセッションスコープに保存
			session.setAttribute("rentlogsAll", rentlogsAll);

			//フォワードするメソッドを実行
			doForward(request, response, "/WEB-INF/jsp/rentall.jsp");

			break;

		//**********************************************************
		//全貸出中リスト
		//**********************************************************
		case "rentnow":

			//メソッドallStaffsList()を実行
			List<ForListDTO> rentNowAll = new MasterDAO().rentNowAllList();

			//全従業員の一覧をセッションスコープに保存
			session.setAttribute("rentNowAll", rentNowAll);

			//フォワードするメソッドを実行
			doForward(request, response, "/WEB-INF/jsp/rentnow.jsp");

			break;

		//**********************************************************
		//２週間以上借りているリスト
		//**********************************************************
		case "2weeks":

			//メソッドallStaffsList()を実行
			List<ForListDTO> overRentList = new MasterDAO().overRent();

			//全従業員の一覧をセッションスコープに保存
			session.setAttribute("overRentList", overRentList);

			//フォワードするメソッドを実行
			doForward(request, response, "/WEB-INF/jsp/2week.jsp");
			break;
		}
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
