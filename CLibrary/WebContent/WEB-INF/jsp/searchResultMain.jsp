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
<link rel="stylesheet" href="Kuramoto.css">
<title>キーワード検索結果</title>
</head>
<body>
	<!---------------------------------------------------------------------------------->
	<h2 class="bg-info">キーワード検索結果</h2>
	<div class="container">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>書籍ID</th>
						<th>JANコード</th>
						<th>書籍名</th>
						<th>購入日</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="searchLike2" items="${searchResult2}">
						<tr>
							<th scope="row">${searchLike2.getBook_Id()}</th>
							<td>${searchLike2.getJan()}</td>
							<td>${searchLike2.getBook_Name()}</td>
							<td>${searchLike2.getPur_Date()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<br>
	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
	<!--------------------------------------------------------------------------------------->
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