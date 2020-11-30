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
<link rel="stylesheet" href="css/index.css">
<title>一覧 例</title>
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
				<li class="nav-item mt-1"><form mame="show" method="post"
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
		<div class="d-flex books mt-4 flex-wrap">
			<c:forEach var="bookData" items="${ booksAllList }">
				<div class="bookContent mb-3 text-white bg-dark overflow-sc" style="
    padding: 0;
    width: 355px;
    margin-right: 15px;
">
					<div class="bookTitle bg-info p-2">
						<c:out value="${bookData.getBook_Name()}" />
					</div>
					<div class="bookImage">
						<c:out value="${bookData.getPur_Date()}" />
						<!-- <img alt=""
							src="https://kimetsu.com/anime/assets/img/special/degicon/wall_sp.jpg"> -->
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>