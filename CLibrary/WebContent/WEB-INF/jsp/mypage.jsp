<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ</title>
</head>
<body>
	<h1>マイページ</h1>
	<h2>
		ようこそ
		<c:out value="${sd.name }" />
		さん
	</h2>


	<br>
	<form action="/CLibrary/MypageServlet?target=rent" method="post">
		<table border="1">
			<tr>
				<th colspan="4">貸し出し中一覧</th>
			</tr>

			<tr>
				<td>書籍名</td>
				<td>貸出日</td>
				<td>返却予定日</td>
				<td><button onclick="location.href='/CLibrary/MypageServlet'">返却</button></td>
			</tr>
			<c:forEach var="result" items="${rentNowList }">
				<tr>
					<td>${result.bookName }</td>
					<td>${result.rentDate }</td>
					<td>${result.schedule }</td>
					<td><button onclick="location.href='/CLibrary/MypageServlet'">返却</button></td>
				</tr>
			</c:forEach>
		</table>
	</form>
	<table border="1">
		<tr>
			<td>貸し出し中の本</td>
			<td><input type="submit" value="借りる"></td>
		</tr>
	</table>

	<center>
		<font size="2"> Copyright (c) 2020 ICTエンジニア科1班 All Rights
			Reserved. </font>
	</center>

</body>
</html>
