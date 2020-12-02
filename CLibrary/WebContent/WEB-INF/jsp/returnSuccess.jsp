<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>返却手続き完了</title>
</head>
<body>
	<h2>返却手続きが完了しました</h2>

<form action="/CLibrary/LoginServlet?target=login" method="post">
<input type="hidden" name="mail" value="${sd.mail }">
<input type="hidden" name="pass" value="${sd.pass }">
							<input type="submit" value="Ｍｙページへ戻る">
						</form>

	<button onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

	<center>
		<font size="2"> Copyright (c) 2020 ICTエンジニア科1班 All Rights
			Reserved. </font>
	</center>
</body>
</html>