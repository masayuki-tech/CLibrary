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





	<button onclick="location.href='/WEB-INF/jsp/mypage.jsp'">Ｍｙページへ</button>
	<button onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
</body>
</html>