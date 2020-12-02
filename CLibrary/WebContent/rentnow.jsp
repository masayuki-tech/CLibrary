<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>貸出中リスト</title>
</head>
<body>
<!----------------------------------------------------------------------------------> --%>
	<table border="1">
		<tr>
			<th colspan="7">貸出中リスト</th>
		</tr>
		<tr>
			<td>貸出履歴ID</td>
			<td>貸出日</td>
			<td>返却予定日</td>
			<td>返却日</td>
			<td>書籍管理ID</td>
			<td>社員管理ID</td>
			<td>社員名</td>
		</tr>
		<c:forEach var="rent2" items="${rentNowAll}">
			<tr>
				<td>${rent2.getRentId()}</td>
				<td>${rent2.getRentDate()}</td>
				<td>${rent2.getSchedule()}</td>
				<td>${rent2.getReturnDate() }</td>
				<td>${rent2.getBookId()}</td>
				<td>${rent2.getStaffId()}</td>
				<td>${rent2.getName()}</td>

			</tr>
		</c:forEach>
	</table>
	<form action="/CLibrary/MasterServlet?target=tomaster" method="post">
		<input type="submit" value="戻る">
		</form><br>

	<button class="button"
		onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>

	<!---------------------------------------------------------------------------------->
</body>
</html>