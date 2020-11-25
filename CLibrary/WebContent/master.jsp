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
<a>本の登録</a>
<form action="/servlet/masterServlet" method="post">
本のID<input type="text" name="book_id"><br>
jan：<input type="text" name="jan"><br>
本の名前：<input type="text" name="book_name"><br>
購入日：<input type="number" name="pur_date"><br>
<input type="submit" value="登録">
</body>
</html>