<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--------------------------------------------------------------------------------------->
	<table border="1">
		<tr>
			<th colspan="5">登録従業員の一覧</th>
		</tr>
		<tr>
			<td>社員管理ID</td>
			<td>氏名</td>
			<td>メールアドレス</td>
			<td>パスワード</td>
			<td>性別</td>
		</tr>
		<c:forEach var="staffs" items="${staffsListAll }">
			<tr>
				<td>${staffs.getStaff_Id()}</td>
				<td>${staffs.getName()}</td>
				<td>${staffs.getMail()}</td>
				<td>${staffs.getPass() }</td>
				<td>${staffs.getGender()}</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<hr>
	<!---------------------------------------------------------------------------------->
	<table border="1">
		<tr>
			<th colspan="5">全本の一覧</th>
		</tr>
		<tr>
			<td>書籍ID</td>
			<td>書籍名</td>
			<td>ＪＡＮコード</td>
			<td>購入日</td>
			<td>貸出ステータス</td>
		</tr>
		<c:forEach var="books" items="${booksListAll }">
			<tr>
				<td>${books.getBook_Id()}</td>
				<td>${books.getBook_Name()}</td>
				<td>${books.getJan()}</td>
				<td>${books.getPur_Date() }</td>
				<td>${books.getRent_Check()}</td>
			</tr>
		</c:forEach>
	</table>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>


	<!---------------------------------------------------------------------------------->
	<%
		int count = 0;
	%>
	<table border="1">
		<tr>
			<th colspan="3">★人気の本ランキングＴＯＰ３</th>
		</tr>
		<tr>
			<td>人気順</td>
			<td>書籍名</td>
			<td>ＪＡＮコード</td>

		</tr>
		<c:forEach var="canRent" items="${top3List}">
			<%
				count++;
			%>
			<tr>
				<td><%=count%>位</td>
				<td>${canRent.getBook_Name()}</td>
				<td>${canRent.getJan()}</td>
			</tr>
		</c:forEach>
	</table>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

	<!---------------------------------------------------------------------------------->

	<table border="1">
		<tr>
			<th colspan="7">全貸出履歴リスト</th>
		</tr>
		<tr>
			<td>貸出履歴ID</td>
			<td>貸出日</td>
			<td>返却予定日</td>
			<td>返却日</td>
			<td>書籍管理ID</td>
			<td>社員管理ID</td>
			<td>社員名</td>
		</tr>
		<c:forEach var="rent" items="${rentlogsAll}">
			<tr>
				<td>${rent.getRentId()}</td>
				<td>${rent.getRentDate()}</td>
				<td>${rent.getSchedule()}</td>
				<td>${rent.getReturnDate() }</td>
				<td>${rent.getBookId()}</td>
				<td>${rent.getStaffId()}</td>
				<td>${rent.getName()}</td>

			</tr>
		</c:forEach>
	</table>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

	<!---------------------------------------------------------------------------------->
	<table border="1">
		<tr>
			<th colspan="7">貸出中リスト</th>
		</tr>
		<tr>
			<td>貸出履歴ID</td>
			<td>貸出日</td>
			<td>返却予定日</td>
			<td>返却日</td>
			<td>書籍管理ID</td>
			<td>社員管理ID</td>
			<td>社員名</td>
		</tr>
		<c:forEach var="rent2" items="${rentNowAll}">
			<tr>
				<td>${rent2.getRentId()}</td>
				<td>${rent2.getRentDate()}</td>
				<td>${rent2.getSchedule()}</td>
				<td>${rent2.getReturnDate() }</td>
				<td>${rent2.getBookId()}</td>
				<td>${rent2.getStaffId()}</td>
				<td>${rent2.getName()}</td>

			</tr>
		</c:forEach>
	</table>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

	<!---------------------------------------------------------------------------------->

	<table border="1">
		<tr>
			<th colspan="7">2週間以上延滞リスト</th>
		</tr>
		<tr>
			<td>貸出履歴ID</td>
			<td>貸出日</td>
			<td>返却予定日</td>
			<td>返却日</td>
			<td>書籍管理ID</td>
			<td>社員管理ID</td>
			<td>社員名</td>
		</tr>
		<c:forEach var="rent3" items="${overRentList}">
			<tr>
				<td>${rent3.getRentId()}</td>
				<td>${rent3.getRentDate()}</td>
				<td>${rent3.getSchedule()}</td>
				<td>${rent3.getReturnDate() }</td>
				<td>${rent3.getBookId()}</td>
				<td>${rent3.getStaffId()}</td>
				<td>${rent3.getName()}</td>

			</tr>
		</c:forEach>
	</table>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

	<!---------------------------------------------------------------------------------->
	<h2>書籍をあいまい検索</h2>
	<form action="/CLibrary/KuramotoServlet?target=likeSearch"
		method="post">
		キーワード：<input type="text" name="text"> <input type="submit"
			value="検索">
	</form>
	<table border="1">
		<tr>
			<th colspan="5">キーワード検索結果</th>
		</tr>
		<tr>
			<td>書籍ID</td>
			<td>JANコード</td>
			<td>書籍名</td>
			<td>購入日</td>
			<td>借りるボタン</td>
		</tr>

		<c:forEach var="searchLike" items="${searchResult}">
			<tr>
				<td>${searchLike.getBook_Id()}</td>
				<td>${searchLike.getJan()}</td>
				<td>${searchLike.getBook_Name()}</td>
				<td>${searchLike.getPur_Date()}</td>
				<td><form action="/CLibrary/MypageServlet?target=rentKuramoto"
						method="post">
						<input type="hidden" name="rentBookId"
							value="${searchLike.getBookId() }"> <input type="hidden"
							name="rentStaffId" value="${sd.getStaff_Id() }"> <input
							type="submit" value="借りる">
					</form></td>


			</tr>
		</c:forEach>
	</table>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>


	<!---------------------------------------------------------------------------------->
</body>
</html>
