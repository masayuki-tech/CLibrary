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
<link rel="stylesheet" href="css/mypage.css">
<title>トップページの例</title>
</head>

<body>
	<div class="login_header">
		<div class="header_item">
			<h1>CLibrary</h1>
		</div>
		<div class="header_item">
			<button onclick="location.href='/CLibrary/MypageServlet'">借りてる本一覧</button>
		</div>
	</div>
	<div class="container">
		<div class="mypage_title mb-5">
			<h1>マイページ</h1>
			<h2>
				ようこそ
				<c:out value="${sd.name }" />
				さん
			</h2>
		</div>
		<div class="mypage_return_book mb-5">
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
							<td>
								<form action="/CLibrary/MypageServlet?target=returnKuramoto"
									method="post">
									<input type="hidden" name="returnBookId"
										value="${result.getBookId() }"> <input type="hidden"
										name="returnRentId" value="${result.getRentId() }"> <input
										type="submit" value="返却">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
		<div class="mypage_rentAll mb-5">
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
						<td>
							<form action="/CLibrary/MypageServlet?target=rentKuramoto"
								method="post">
								<input type="hidden" name="rentBookId"
									value="${result2.getBookId() }"> <input type="hidden"
									name="rentStaffId" value="${result2.getStaffId() }"> <input
									type="submit" value="借りる">
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
	<footer>
		<center>
			<font size="2"> Copyright (c) 2020 ICTエンジニア科1班 All Rights
				Reserved. </font>
		</center>
	</footer>
</body>
</html>
