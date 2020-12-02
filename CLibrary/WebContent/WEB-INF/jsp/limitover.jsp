<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="dto.RentlogsDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/stafflist.css">
<title>Insert title here</title>
</head>
<body>
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
			<td>貸出ID</td>
			<td>貸出日付</td>
			<td>スタッフID</td>
			<td>本の名前</td>
		</tr>
		<c:forEach var="a" items="${ limitOver }">
			<tr>
				<td>${a.getRent_Id() }</td>
				<td>${a.getRent_Date() }</td>
				<td>${a.getBook_Id() }</td>
				<td>${a.getStaff_Id() }</td>
			</tr>
		</c:forEach>
	</table>
	<button class="button"
					onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
</body>
</html>