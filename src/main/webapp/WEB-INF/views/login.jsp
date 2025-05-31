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
<div class="container" style="max-width:400px; margin:3rem auto;">
    <div class="card" style="padding:2rem;">
        <h2 style="text-align:center;">로그인</h2>
        <% if (request.getParameter("error") != null) { %>
            <div style="color:#e74c3c; margin-bottom:1rem; text-align:center;">아이디 또는 비밀번호가 올바르지 않습니다.</div>
        <% } %>
        <form action="/login" method="post">
            <label for="username">아이디:</label>
            <input type="text" id="username" name="username" required style="width:100%; padding:10px; border-radius:6px; border:1px solid #ddd; margin-bottom:1rem;">

            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required style="width:100%; padding:10px; border-radius:6px; border:1px solid #ddd; margin-bottom:1.5rem;">

            <button type="submit" class="main-btn" style="width:100%;">로그인</button>
        </form>
        <p style="text-align:center; margin-top:1.5rem;"><a href="/register" style="color:#003366; text-decoration:underline;">회원가입으로 이동</a></p>
    </div>
</div>
</body>
</html>
