<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
  <title>화햇 로보틱스</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<!-- 기존 텍스트/버튼 영역
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
</form> -->

<!-- 모던 메인 섹션 -->
<div class="main-hero">
    <div class="hero-content">
        <div class="hero-title">
          기술의 한계를 넘어,<br>
          더 안전하고 똑똑한 미래를 만듭니다.
        </div>
        <div class="hero-btns">
            <button class="main-btn" onclick="location.href='/about'">자세히보기 &rarr;</button>
        </div>
    </div>
    <div class="main-tabs">
        <div class="main-tab active">Brand</div>
    </div>
</div>
<!-- 필요시 아래에 추가 콘텐츠 삽입 -->
</body>
</html>
