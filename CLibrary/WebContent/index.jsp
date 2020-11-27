<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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