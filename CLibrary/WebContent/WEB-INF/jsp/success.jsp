<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント新規登録成功</title>
</head>
<body>
<h2>新規登録</h2>
<p>ようこそ<c:out value="${sd.name }"/>さん</p>





	<form action="/CLibrary/LoginServlet?target=toMypage" method="post">
		<input type="hidden" name="mail" value="${sd.mail }"> <input
			type="hidden" name="pass" value="${sd.pass }"> <input
			type="submit" value="Ｍｙページへ">
	</form>
	<button onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
</body>
</html>