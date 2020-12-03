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
<link rel="stylesheet" href="css/subMypage.css">
<title>貸出し手続き完了</title>
</head>
<body>
<div class="login_header">
		<div class="header_title">
			<h1>CLibrary</h1>
		</div>
	</div>
		<div class="header_item mt-3">
			<a class="right_list_crrent_user "
				href="/CLibrary/LoginServlet?target=login">ログアウト</a>
		</div>
	</div>
	<h2>貸出し手続きが完了しました</h2>
	<h3>ご利用ありがとうございました。</h3>

<form action="/CLibrary/LoginServlet?target=toMypage" method="post">
							<input type="submit" value="Ｍｙページへ戻る">
						</form>
<div class="top_button">
