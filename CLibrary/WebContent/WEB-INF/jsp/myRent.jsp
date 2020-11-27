<%@page import="model.MyRentDTO"%>
<%@page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>借りてる本一覧</title>
</head>
<body>
<h1>借りてる本一覧</h1>
	<form action="/CLibrary/MypageServlet?target=return" method="post">
		<table border="1">
			<tr>
				<td>本名</td>
				<td>返却期日</td>
				<td>返却ボタン</td>
			</tr>
			<c:forEach var="myRents" items="${myRentsList}">
			<tr>
				<td><c:out value="${myRents.book_name}"></c:out></td>
				<td><c:out value="${myRents.return_date}"></c:out></td>
				<td><input type="submit" value="返却する"></td>
			</tr>
		</c:forEach>
			<tr>
				<td>借り中の本</td>
				<td>返却残り期間</td>

			</tr>
		</table>
	</form>
</body>
</html>