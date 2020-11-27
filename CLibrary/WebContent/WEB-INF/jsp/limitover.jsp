<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    <%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="model.rentlogsDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table border="1">
	<tr><td>貸出ID</td><td>貸出日付</td><td>スタッフID</td><td>本の名前</td></tr>
	<c:forEach var ="a" items="${ limitOver }">
	<tr><td>${a.getRent_Id() }</td><td>${a.getRent_Date() }</td><td>${a.getBook_Id() }</td><td>${a.getStaff_Id() }</td></tr>
	</c:forEach>

	</table>
</body>
</html>