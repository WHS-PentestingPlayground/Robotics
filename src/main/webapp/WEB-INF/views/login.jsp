<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container container-login">
    <div class="card card-login">
        <h2 class="text-center">로그인</h2>
        <% if (request.getParameter("error") != null) { %>
        <div class="text-error">아이디 또는 비밀번호가 올바르지 않습니다.</div>
        <% } %>
        <!-- 여기 action만 수정 -->
        <form action="/login-process" method="post">
            <label for="username">아이디:</label>
            <input type="text" id="username" name="username" required class="input-full">

            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required class="input-full input-mb">

            <button type="submit" class="main-btn btn-full">로그인</button>
        </form>
        <p class="text-center mt-1-5">
            <a href="/register" class="link-blue">회원가입으로 이동</a>
        </p>
    </div>
</div>
</body>
</html>

