<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍一覧</title>
</head>
<body>
<!---------------------------------------------------------------------------------->
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
		</form><br>

	<button class="button"
					onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>


	<!---------------------------------------------------------------------------------->
</body>
</html>