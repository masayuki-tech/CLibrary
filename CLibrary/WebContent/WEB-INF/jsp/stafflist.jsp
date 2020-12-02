<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録従業員の一覧</title>
</head>
<body>
<!--------------------------------------------------------------------------------------->
	<table border="1">
		<tr>
			<th colspan="5">登録ユーザー(従業員)の一覧</th>
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
	<form action="/CLibrary/MasterServlet?target=tomaster" method="post">
		<input type="submit" value="戻る">
		</form><br>

	<button class="button"
					onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
	<!--------------------------------------------------------------------------------------->
</body>
</html>