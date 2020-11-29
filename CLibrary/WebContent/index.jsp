<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

<title>Insert title here</title>
</head>
<body>
	<header>
		<div class="warpapr">
			<div class="header_contents">
				<div class="header_up_main">
					<div class="header"></div>
				</div>
				<div class="header_bottom_main">
					<div class="header_bottom_right_list">


						<a class="right_list_new_user" href="/CLibrary/LoginServlet?target=register">新規登録</a>
						<br>
						<a class="right_list_crrent_user" href="/CLibrary/LoginServlet?target=login">ログイン</a>

					</div>
				</div>
			</div>
		</div>
	</header>
	<div class="main_visual"></div>
</body>
</html>