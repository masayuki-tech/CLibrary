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

	<c:if test="${rentNowList !=null}">

		<br>
		<!-- <form action="/CLibrary/MypageServlet?target=rent" method="post"> -->
		<table border="1">
			<tr>
				<th colspan="4">貸し出し中一覧</th>
			</tr>

			<tr>
				<td>書籍名</td>
				<td>貸出日</td>
				<td>返却予定日</td>
				<td></td>
			</tr>
			<c:forEach var="result" items="${rentNowList }">
				<tr>
					<td>${result.getBookName() }</td>
					<td>${result.getRentDate() }</td>
					<td>${result.getSchedule() }</td>
					<td><form action="/CLibrary/MypageServlet?target=returnKuramoto" method="post">
							<input type="hidden" name="returnBookId" value="${result.getBookId() }">
							<input type="hidden" name="returnRentId" value="${result.getRentId() }">
							<input type="submit" value="返却">
						</form></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


	<table border="1">
			<tr>
				<th colspan="3">貸出可能な本一覧</th>
			</tr>

			<tr>
				<td>書籍名</td>
				<td>ＪＡＮコード</td>
				<td></td>

			</tr>
			<c:forEach var="result2" items="${canRentList }">
				<tr>
					<td>${result2.getBookName() }</td>
					<td>${result2.getJan() }</td>
					<td><form action="/CLibrary/MypageServlet?target=rentKuramoto" method="post">
							<input type="hidden" name="rentBookId" value="${result2.getBookId() }">
							<input type="hidden" name="rentStaffId" value="${result2.getStaffId() }">
							<input type="submit" value="借りる">
						</form></td>
				</tr>
			</c:forEach>
		</table>

	<button onclick="location.href='/CLibrary/MypageServlet'">借りてる本一覧</button>

	<center>
		<font size="2"> Copyright (c) 2020 ICTエンジニア科1班 All Rights
			Reserved. </font>
	</center>
</body>
</html>
