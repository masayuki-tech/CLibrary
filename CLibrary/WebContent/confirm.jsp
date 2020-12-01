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
 <table border="1">
        <tr align="center">
            <th>書籍名</th><th>著者名</th><th>出版社</th><th>発刊日</th><th>表紙</th>
        </tr>

        <%-- ArrayListデータを取り出して表示する --%>

       <%--  <c:forEach var="obj" items="$ { result }">
            <tr>
                <td><c:out value="${obj.book_name}"/></td>
                <td><c:out value="${obj.}"/></td>
                <td><c:out value="${obj.getPublisher()}"/></td>
                <td style="width:600px;border:1px solid #000;word-break: break-all;">
                <img src="<c:out value="${obj. getImage()}"/>"></td>
            </tr>
        </c:forEach> --%>
        <tr>
                <td><c:out value="${result.getBook_Name()}"/></td>
                <td><c:out value="${result. getAuthor()}"/></td>
                <td><c:out value="${result.getPublisher()}"/></td>
                <td style="width:600px;border:1px solid #000;word-break: break-all;">
                <img src="<c:out value="${result. getImage()}"/>"></td>
            </tr>
    </table>
   	<a href="/CLibrary/masterServlet?action=ok">上記の情報で登録してもいいですか？</a>
</body>
</html>