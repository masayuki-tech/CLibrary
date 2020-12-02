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
	<form action="/CLibrary/MasterServlet?target=bookinput" method="post">
		ISBN：<input type="text" name="isbn"><br> 購入日：<input
			type="text" name="pur_date" required><br> <input
			type="submit" value="登録" name="button_name">
	</form>


	<form action="/CLibrary/MasterServlet" method="get"></form>
	<a href="/CLibrary/MasterServlet?action=done">二週間以上の遅延発生者</a>
	<input type="text" name="pur_date" required>
	<br>

	<form action="/CLibrary/MasterServlet?target=staffAll" method="post">
		<input type="submit" value="登録ユーザー(従業員)の一覧">
	</form>


<form action="/CLibrary/MasterServlet?target=booksAll" method="post">
		<input type="submit" value="蔵書一覧">
	</form>

<form action="/CLibrary/MasterServlet?target=rentAll" method="post">
		<input type="submit" value="貸出履歴">
	</form>

	<form action="/CLibrary/MasterServlet?target=rentnow" method="post">
		<input type="submit" value="現在貸出中の本一覧">
	</form>



</body>
</html>