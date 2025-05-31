<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
</head>
<body>
<h2>로그인</h2>
<%-- 로그인 실패 시 에러 메시지 표시 --%>
<% if (request.getParameter("error") != null) { %>
    <div style="color:red;">아이디 또는 비밀번호가 올바르지 않습니다.</div>
<% } %>
<form action="/login" method="post">
    <label for="username">아이디:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">비밀번호:</label>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">로그인</button>
</form>
<p><a href="/register">회원가입으로 이동</a></p>
</body>
</html>
