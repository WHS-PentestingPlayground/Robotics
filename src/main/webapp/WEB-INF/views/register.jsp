<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
<h2>회원가입</h2>

<%-- 에러 메시지 출력 --%>
<c:if test="${not empty error}">
    <div style="color:red;">${error}</div>
</c:if>

<form action="/register" method="post">
    <label for="username">아이디:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">비밀번호:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="role">회원 유형:</label>
    <select id="role" name="role" required>
        <option value="USER">일반</option>
        <option value="BUSINESS">기업</option>
        <option value="ADMIN">관리자</option>
    </select><br><br>

    <button type="submit">회원가입</button>
</form>

<p><a href="/login">로그인으로 돌아가기</a></p>
</body>
</html>
