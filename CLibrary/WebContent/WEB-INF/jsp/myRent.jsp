<%@page import="model.rentlogsDTO"%>
<%@page import="java.util.*"%>
<%@page import="model.booksDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<booksDTO> booksAll = (List<booksDTO>) request.getAttribute("categorysList");
%>
<%
	List<rentlogsDTO> rentlogsAll = (List<rentlogsDTO>) request.getAttribute("categorysList");
%>
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
			<%
				for (booksDTO books : booksAll) {
			%>
			<tr>
				<td><%=books.getBook_Name()%></td>
				<td><%=%></td>
				<td><%=books.getBook_Name()%></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td>借り中の本</td>
				<td>返却残り期間</td>
				<td><input type="submit" value="返却する"></td>
			</tr>
		</table>
	</form>
</body>
</html>