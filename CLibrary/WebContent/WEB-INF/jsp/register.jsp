<!--ログイン結果画面を出力するビュー-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- User loginUser = (User) session.getAttribute("loginUser");-->




<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
</head>
<body>
	<h2>アカウント登録フォーム</h2>
	<form action="/CLibrary/LoginServlet?target=register"
		method="post">
<input type="hidden" name="target" value="register">
		ＩＤ：<input type="number" name="staffId" required><br>
		パスワード：<input type="password" name="pass" required><br>
		氏名：<input type="text" name="name" required><br>
		性別：<input type="radio" name="gender" value="1">男性
			<input type="radio" name="gender" value="2">女性
			<input type="submit" value="登録">
	</form>

<button onclick="location.href='///////////'">トップ画面へ</button>

</body>
</html>