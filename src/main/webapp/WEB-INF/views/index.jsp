<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
  <title>화햇 로보틱스</title>
</head>
<body>
<h1>메인 페이지</h1>
<p>화햇 로보틱스 메인페이지</p>
<form action="/board/posts" method="get">
  <button type="submit">게시판 가기</button>
</form>

<form action="/login" method="get">
  <button type="submit">로그인</button>
</form>
<form action="/logout" method="get">
  <button type="submit">로그아웃</button>
</form>
</body>
</html>
