<!-- ログイン画面を出力するビュー -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
	<h2>ログインＩＤとパスワードを入力して下さい。</h2>
	<form action="/CLibrary/LoginServlet?target=login" method="post">
		ログインＩＤ：<input type="text" name="staffId" required><br>
		パスワード：<input type="password" name="pass" required><br>
		<input type="hidden" name="target" value="login">
		<input type="submit" value="ログイン">
	</form>
	<button onclick="location.href='/////////////.jsp'">トップ画面へ</button>
</body>
</html>