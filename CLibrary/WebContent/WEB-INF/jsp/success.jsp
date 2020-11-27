<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <%@ page import="model.staffsDTO,java.util.List" %>
<% List<staffsDTO> lstd=(List<staffsDTO>) session.getAttribute("loginList"); %>
<% staffsDTO sd=new staffsDTO(); %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント新規登録成功</title>
</head>
<body>
<h2>新規登録</h2>
<p>ようこそ<% %>さん</p>





	<button onclick="location.href='//////////'">Ｍｙページへ</button>
	<button onclick="location.href='//////////'">トップ画面へ</button>
</body>
</html>