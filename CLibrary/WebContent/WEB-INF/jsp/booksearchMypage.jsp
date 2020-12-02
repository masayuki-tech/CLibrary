<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<link rel="stylesheet" href="subMypage.css">
<title>書籍検索モード</title>
</head>
<body>

	<!---------------------------------------------------------------------------------->
	<h2 class="bg-info">書籍キーワード検索</h2>
	<br>
	<form action="/CLibrary/BookSearchServlet?target=likeSearch"
		method="post">
		<input type="text" name="text" placeholder="キーワードを入力"> <input
			type="submit" value="検索" class="btn btn-primary btn-lg">
	</form>
	<br>
	<br>
	<!---------------------------------------------------------------------------------->
	<%
		int count = 0;
	%>
	<h2>人気ランキングＴＯＰ５</h2>
	<div class="container">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>人気順</th>
						<th>書籍名</th>
						<th>ＪＡＮコード</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="top5" items="${top5List}">
						<%
							count++;
						%>
						<tr>
							<th scope="row"><%=count%>位</th>
							<td>${top5.getBook_Name()}</td>
							<td>${top5.getJan()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<!---------------------------------------------------------------------------------->

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
<br>
	<form action="/CLibrary/LoginServlet?target=toMypage" method="post">
		<input type="hidden" name="mail" value="${sd.mail }"> <input
			type="hidden" name="pass" value="${sd.pass }"> <input
			type="submit" value="Ｍｙページへ戻る">
	</form>
	<br>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>


	<!---------------------------------------------------------------------------------->
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>
</body>
</html>