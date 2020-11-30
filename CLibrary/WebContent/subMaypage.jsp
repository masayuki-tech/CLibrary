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
	<nav class="navbar navbar-expand-lg navbar-light">
		<h2>CLibrary</h2>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item px-5 active"><a class="nav-link" href="/"><h4>Home</h4></a>
				</li>
				<li class="nav-item mt-1"><form name="show" method="post"
						action="/CLibrary/WelcomeServlet?target=select">
						<a class="nav-link" href="javascript:show.submit()"><h5>一覧を表示する</h5></a>
					</form></li>
			</ul>
			<div class="form-inline my-2 my-lg-0">
				<a class="right_list_new_user"
					href="/CLibrary/LoginServlet?target=register">新規登録</a>
			</div>
			<br>
			<div class="form-inline my-2 my-lg-0">
				<a class="right_list_crrent_user "
					href="/CLibrary/LoginServlet?target=login">ログイン</a>
			</div>
			<%-- <%= form_with(url: search_posts_path, local: true, method: :get, class: "form-inline my-2 my-lg-0") do |form| %>
			<%= form.text_field :keyword, placeholder: "投稿を検索する", class: "form-control mr-sm-2" %>
			<%= form.submit "検索する", class: "btn btn-outline-success my-2 my-sm-0" %>
			<% end %> --%>
		</div>
	</nav>
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
							<td><form
									action="/CLibrary/MypageServlet?target=returnKuramoto"
									method="post">
									<input type="hidden" name="returnBookId"
										value="${result.getBookId() }"> <input type="hidden"
										name="returnRentId" value="${result.getRentId() }"> <input
										type="submit" value="返却">
								</form></td>
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
						<td><form
								action="/CLibrary/MypageServlet?target=rentKuramoto"
								method="post">
								<input type="hidden" name="rentBookId"
									value="${result2.getBookId() }"> <input type="hidden"
									name="rentStaffId" value="${result2.getStaffId() }"> <input
									type="submit" value="借りる">
							</form></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<button onclick="location.href='/CLibrary/MypageServlet'">借りてる本一覧</button>
	</div>
	<footer>
		<center>
			<font size="2"> Copyright (c) 2020 ICTエンジニア科1班 All Rights
				Reserved. </font>
		</center>
	</footer>
</body>
</html>