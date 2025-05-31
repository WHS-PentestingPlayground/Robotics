<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container" style="max-width:480px; margin:3rem auto;">
    <div class="card" style="padding:2rem;">
        <h2 style="text-align:center;">회원가입</h2>
        <c:if test="${not empty error}">
            <div style="color:#e74c3c; margin-bottom:1rem; text-align:center;">${error}</div>
        </c:if>
        <form action="/register" method="post">
            <label for="username">아이디:</label>
            <input type="text" id="username" name="username" required style="width:100%; padding:10px; border-radius:6px; border:1px solid #ddd; margin-bottom:1rem;">

            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required style="width:100%; padding:10px; border-radius:6px; border:1px solid #ddd; margin-bottom:1rem;">

            <label for="passwordConfirm">비밀번호 확인:</label>
            <input type="password" id="passwordConfirm" name="passwordConfirm" required style="width:100%; padding:10px; border-radius:6px; border:1px solid #ddd; margin-bottom:1rem;">

            <label for="role">회원 유형:</label>
            <select id="role" name="role" required style="width:100%; padding:10px; border-radius:6px; border:1px solid #ddd; margin-bottom:1.5rem;">
                <option value="USER">일반</option>
                <option value="BUSINESS">기업</option>
                <option value="ADMIN">관리자</option>
            </select>

            <button type="submit" class="main-btn" style="width:100%;">회원가입</button>
        </form>
        <p style="text-align:center; margin-top:1.5rem;"><a href="/login" style="color:#003366; text-decoration:underline;">로그인으로 돌아가기</a></p>
    </div>
</div>
</body>
</html>
