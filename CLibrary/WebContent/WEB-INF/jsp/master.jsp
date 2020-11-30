<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a>マスターメニュー</a>
	<br>
	<a>本の登録</a>
	<form action="/CLibrary/masterServlet" method="post">
		本のID<input type="text" name="book_id"><br>
		ISBN：<inputtype="text" name="isbn"><br>
		購入日：<input type="text"name="pur_date"><br>
			<input type="submit" value="登録">
		<form action="/CLibrary/masterServlet" method="get"></form>
		<a href="/CLibrary/masterServlet?action=done">二週間以上の遅延発生者</a>
</body>
</html>