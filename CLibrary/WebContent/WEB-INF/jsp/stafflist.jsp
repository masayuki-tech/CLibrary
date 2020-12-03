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
<title>登録従業員の一覧</title>
</head>
<body>
	<!--------------------------------------------------------------------------------------->
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
			<th colspan="5">登録ユーザー(従業員)の一覧</th>
		</tr>
		<tr>
			<td>社員管理ID</td>
			<td>社員名</td>
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
				<td><c:if test="${staffs.getGender()==1}">
				男性
				</c:if>
				<c:if test="${staffs.getGender()==2}">
				女性
				</c:if>
				<c:if test="${staffs.getGender()==0}">
				未回答
				</c:if></td>
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
	<!--------------------------------------------------------------------------------------->
</body>
</html>