<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="css/master.css">
    <title>書籍登録画面</title>
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
        <div class="login_message pt-4 pb-4">
            <h2>ISBN番号と購入日を入力して下さい。</h2>
        </div>
        <div class="login_form">
            <form action="/CLibrary/MasterServlet?target=bookinput" method="post">
                <div class="register mt-4 mb-4">
                    <div class="keyword">ISBN</div><input autofocus="autofocus" placeholder="例）123A456B78" type="text" name="isbn" required><br>
                </div>
                <div class="register mb-5">
                    <div class="keyword">購入日</div><input placeholder="例）2020-03-05" type="date" name="pur_date" required><br>
                </div>
                <div class="login_button mb-4">
                    <input type="hidden" name="target" value="bookinput">
                    <input class="button" type="submit" value="登録する">
                </div>
            </form>
            <div class="top_button pb-4">
                <button class="button" onclick="location.href='/CLibrary/WelcomeServlet'">トップ画面へ</button>
            </div>
        </div>
    </div>

</body></html>
