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
<link rel="stylesheet" href="css/subLogin.css">
<title>ログイン画面</title>
</head>

<body>
	<div class="login_header">
		<div class="header_title">
			<h1>CLibrary</h1>
		</div>
	</div>
	<div class="container">
		<div class="login_message pt-4 pb-4">
			<h2>ログインＩＤ（メールアドレス）と パスワードを入力して下さい。</h2>
		</div>
		<div class="login_form">
			<form action="/CLibrary/LoginServlet?target=login" method="post">
				<div class="register mt-4 mb-4">
					<div class="keyword">メールアドレス</div>
					<input autofocus="autofocus" placeholder="例）abc@def" type="text"
						name="mail" required><br>
				</div>
				<div class="register mb-5">
					<div class="keyword">パスワード</div>
					<input placeholder="7文字以上の半角英数字" type="password" name="pass"
						required><br>
				</div>
				<div class="login_button mb-4">
					<input type="hidden" name="target" value="login"> <input
						class="button" type="submit" value="ログイン">
				</div>
			</form>
			<div class="top_button pb-4">
				<button class="button"
					onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
			</div>
		</div>
	</div>
</body>
</html>
