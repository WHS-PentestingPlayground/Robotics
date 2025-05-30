<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/css/header.css">
<header class="header">
    <nav class="header-nav">
        <!-- 왼쪽: 로고 -->
        <div class="header-logo">
            <a href="/" class="header-logo-link">
                <span class="header-logo-emoji">🤖</span>
                <span class="header-logo-text">WH 화햇로보틱스</span>
            </a>
        </div>
        <!-- 가운데: 메인 메뉴 -->
        <div class="header-main-menu">
            <a href="/about" class="header-menu-link">사업소개</a>
            <a href="/board/posts" class="header-menu-link">게시판</a>
        </div>
        <!-- 오른쪽: 유저 메뉴 -->
        <div class="header-user-menu">
            <% if (session.getAttribute("username") != null) { %>
                <span class="header-username"><%= session.getAttribute("username") %>님</span>
                <a href="/mypage" class="header-user-link">마이페이지</a>
                <form action="/logout" method="post" class="header-logout-form">
                    <button type="submit" class="header-logout-btn">로그아웃</button>
                </form>
            <% } else { %>
                <a href="/login" class="header-user-link">로그인</a>
                <a href="/register" class="header-user-link">회원가입</a>
            <% } %>
        </div>
    </nav>
</header>
