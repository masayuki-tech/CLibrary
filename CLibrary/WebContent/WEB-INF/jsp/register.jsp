<!--ログイン結果画面を出力するビュー-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
</head>
<body>
	<h2>アカウント登録フォーム</h2>
	<form action="/CLibrary/LoginServlet?target=register" method="post">
		<input type="hidden" name="target" value="register">
		氏名：<input type="text" name="name" required><br>
		メールアドレス：<input type="text" name="mail" required><br>
		パスワード：<input type="password" name="pass" required><br>
		パスワード確認：<input type="password" name="pass2" required><br>
		性別：<input type="radio" name="gender" value="1">男性
		 <input type="radio" name="gender" value="2">女性
		 <input type="radio" name="gender" value="0" required>未回答
		 <br>
		 <input type="submit" value="登録">
	</form>

	<button onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

</body>
</html>