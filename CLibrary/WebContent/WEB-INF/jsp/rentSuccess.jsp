<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>貸出し手続き完了</title>
</head>
<body>
	<h2>貸出し手続きが完了しました</h2>
	<h3>ご利用ありがとうございました。</h3>

<form action="/CLibrary/LoginServlet?target=toMypage" method="post">
							<input type="submit" value="Ｍｙページへ戻る">
						</form>

	<button onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

	<center>
		<font size="2"> Copyright (c) 2020 ICTエンジニア科1班 All Rights
			Reserved. </font>
	</center>
</body>
</html>