<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="/css/header.css">
<header class="header">
    <nav class="header-nav">
        <!-- 왼쪽: 로고 -->
        <div class="header-logo">
            <a href="/" class="header-logo-link">
                <img src="/img/daegari.svg" alt="로고" class="header-logo-emoji">
                <span class="header-logo-text">WH 로보틱스</span>
            </a>
        </div>
        <!-- 가운데: 메인 메뉴 -->
        <div class="header-main-menu">
            <a href="/about" class="header-menu-link">사업소개</a>
            <a href="/products" class="header-menu-link">제품소개</a>
            <a href="/board/posts" class="header-menu-link">게시판</a>
        </div>
        <!-- 오른쪽: 유저 메뉴 (Spring Security 기반 동적 표시) -->
        <div class="header-user-menu">
            <!-- 로그인된 사용자에게만 보이는 메뉴 -->
            <sec:authorize access="isAuthenticated()">
                <span class="header-username"
                id="loginUser" data-userid="<sec:authentication property='principal.user.id'/>">
                    <sec:authentication property="principal.username" />님
                </span>
                <a href="/mypage?id=<sec:authentication property='principal.user.id'/>" class="header-user-link">마이페이지</a>
                <form action="/logout" method="post" class="header-logout-form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="header-logout-btn">로그아웃</button>
                </form>
            </sec:authorize>
            
            <!-- 로그인하지 않은 사용자에게만 보이는 메뉴 -->
            <sec:authorize access="!isAuthenticated()">
                <a href="/login" class="header-user-link">로그인</a>
                <a href="/register" class="header-user-link">회원가입</a>
            </sec:authorize>
        </div>
    </nav>
</header>

<!-- ② header.jsp 맨 끝에 스크립트 삽입 -->
<script>
document.addEventListener('DOMContentLoaded', () => {
    const boardLink = document.querySelector('.header-main-menu a[href="/board/posts"]');
    if (!boardLink) return;

    boardLink.addEventListener('click', evt => {
    evt.preventDefault();                       // 메뉴 기본 이동 막기

    fetch('/board/posts', { credentials: 'include' })
        .then(res => {
        if (res.status === 403) {               // 권한 없을 때
            alert('게시판은 기업 회원만 이용할 수 있습니다.');

            // 로그인 상태면 마이페이지, 아니면 로그인 페이지
            const userSpan = document.getElementById('loginUser');
            if (userSpan) {
            const id = userSpan.dataset.userid;
            window.location.href = '/mypage?id=' + id;
            } else {
            window.location.href = '/login';
            }
        } else {                                // 200 OK → 정상 이동
            window.location.href = '/board/posts';
        }
        })
        .catch(() => {                            // 네트워크 오류 등
        window.location.href = '/board/posts';
        });
    });
});
</script>
