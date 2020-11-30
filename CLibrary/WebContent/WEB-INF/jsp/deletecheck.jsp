<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>削除確認</title>
<style>
div {background-color:lightgray;}
</style>
</head>
<body>
	<h1>削除します。よろしいですか？</h1>
	<hr>
	<table border="1">
		<tr>
			<th><div class="nocolor">●●</div></th>
			<th><div class="namecolor">●●</div></th>
		</tr>
		<tr>
			<th><c:out value="${●●}" /></th>
			<th><c:out value="${●●}" /></th>
		</tr>
	</table>
	<br>
	<button onclick="location.href='/jdbcWeb/DbController?result=insert'">確定</button>
	<button onclick="location.href='/jdbcWeb/categorysForm.jsp'">戻る</button>

</body>
</html>
