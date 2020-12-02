<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="css/confirm.css">
    <title>書籍登録確認画面</title>
</head>

<body>
    <div class="login_header">
        <div class="header_item">
            <h1>CLibrary</h1>
        </div>
        <div class="header_item">
            <h1>マスターページ</h1>
        </div>
        <div class="header_item mt-3">
            <a class="right_list_crrent_user " href="/CLibrary/LoginServlet?target=login">ログアウト</a>
        </div>
    </div>
    <div class="container">
        <table border="1">
            <tr align="center">
                <th>書籍名</th>
                <th>著者名</th>
                <th>出版社</th>
                <th>画像</th>
                <th>内容</th>
            </tr>
            <tr>
                <td>
                    <c:out value="${result.getBook_Name()}" />
                </td>
                <td>
                    <c:out value="${result. getAuthor()}" />
                </td>
                <td>
                    <c:out value="${result.getPublisher()}" />
                </td>
                <!--                <td style="width:600px;border:1px solid #000;word-break: break-all;">-->
                <td>
                    <img src="<c:out value=" ${result. getImage()}" />">
                </td>
                <td>
                    <c:out value="${result.getDescription()}" />
                </td>
            </tr>
        </table><br>
        <h2>上記の情報で登録してよいですか</h2>
        <div class="result">
            <div>
                <a href="/CLibrary/BookAddServlet?action=done">はい</a>
            </div>
            <div>
                <a href="/CLibrary/ToMaster">いいえ</a>
            </div>
        </div>
    </div>
</body></html>
