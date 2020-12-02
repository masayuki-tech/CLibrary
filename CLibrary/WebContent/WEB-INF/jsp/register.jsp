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
<link rel="stylesheet" href="css/subRegister.css">
<title>新規登録画面</title>
</head>

<body>
	<div class="login_header">
		<div class="header_title">
			<h1>CLibrary</h1>
		</div>
	</div>
	<div class="container">
		<div class="login_message pt-4 pb-4">
			<div class="title mb-4">
				<h2>新規登録</h2>
			</div>
			<h2>氏名、ログインＩＤ（メールアドレス）、パスワード、性別を入力して下さい。</h2>
		</div>
		<div class="login_form">
			<form action="/CLibrary/LoginServlet?target=login" method="post">
				<input type="hidden" name="target" value="register">
				<div class="register">
					<div class="keyword">氏名</div>
					<input class="data"autofocus="autofocus" type="text" name="name" required><br>
				</div>
				<div class="register mt-4 mb-4">
					<div class="keyword">メールアドレス</div>
					<input class="data" placeholder="例）abc@def" type="text" name="mail" required><br>
				</div>
				<div class="register mb-4">
					<div class="keyword">パスワード</div>
					<input class="data" placeholder="7文字以上の半角英数字" type="password" name="pass"
						required><br>
				</div>
				<div class="register mb-4">
					<div class="keyword">確認用パスワード</div>
					<input class="data" placeholder="7文字以上の半角英数字" type="password" name="pass"
						required><br>
				</div>
				<div class="register">
					<div class="gender_text">性別</div>
					<div class="gender_radio">
						<input class="gender_data" type="radio" name="gender" value="1">男性
						<input class="gender_data" type="radio" name="gender" value="2">女性
						<input class="gender_data" type="radio" name="gender" value="0" required>未回答
					</div>
				</div>
				<br>
				<div class="register_button mb-4">
					<input type="hidden" name="target" value="register"> <input
						class="button" type="submit" value="新規登録">
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
