<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/stafflist.css">
<title>書籍一覧</title>
</head>
<body>
	<!---------------------------------------------------------------------------------->
	<div class="login_header">
		<div class="header_item">
			<h1>CLibrary</h1>
		</div>
		<div class="header_item">
			<h1>マスターページ</h1>
		</div>
		<div class="header_item mt-3">
			<a class="right_list_crrent_user "
				href="/CLibrary/LoginServlet?target=login">ログアウト</a>
		</div>
	</div>
	<table border="1">
		<tr>
			<th colspan="5">書籍一覧</th>
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
	<form action="/CLibrary/MasterServlet?target=tomaster" method="post">
		<input type="submit" value="戻る">
	</form>
	<br>

	<div class="top_button">
		<button class="button"
			onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
	</div>


	<!---------------------------------------------------------------------------------->
</body>
</html>