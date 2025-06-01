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
<div class="container container-register">
    <div class="card card-register">
        <h2 class="text-center">회원가입</h2>
        <c:if test="${not empty error}">
            <div class="text-error">${error}</div>
        </c:if>
        <form action="/register" method="post">
            <label for="username">아이디:</label>
            <input type="text" id="username" name="username" required class="input-full">

            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required class="input-full">

            <label for="passwordConfirm">비밀번호 확인:</label>
            <input type="password" id="passwordConfirm" name="passwordConfirm" required class="input-full">

            <label for="role">회원 유형:</label>
            <select id="role" name="role" required class="input-full input-mb">
                <option value="USER">일반</option>
                <option value="BUSINESS">기업</option>
                <option value="ADMIN">관리자</option>
            </select>

            <button type="submit" class="main-btn btn-full">회원가입</button>
        </form>
        <p class="text-center mt-1-5"><a href="/login" class="link-blue">로그인으로 돌아가기</a></p>
    </div>
</div>
</body>
</html>
