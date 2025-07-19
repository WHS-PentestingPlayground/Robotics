<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>마이페이지 - 화햇 로보틱스</title>
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" href="/css/mypage.css">
</head>
<body class="mypage-body">
    <%@ include file="header.jsp" %>
    <div class="container">
        <div class="mypage-container">
            <div class="mypage-header">
                <h1>마이페이지</h1>
            </div>
            <div class="mypage-content">
                <% if (request.getAttribute("error") != null) { %>
                    <div class="error-message">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                <div class="user-info">
                    <h2>회원 정보</h2>
                    <div class="info-item">
                        <span class="label">아이디:</span>
                        <span class="value">${user.username}</span>
                    </div>
                    <div class="info-item">
                        <span class="label">회원 등급:</span>
                        <span class="value">${user.role}</span>
                    </div>
                    <div class="info-item">
                        <span class="label">가입일:</span>
                        <span class="value">${user.createdAt}</span>
                    </div>
                </div>
                <div class="mypage-actions">
                    <button class="action-btn" id="passwordChangeBtn">비밀번호 변경</button>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
document.addEventListener('DOMContentLoaded', function() {
    var btn = document.getElementById('passwordChangeBtn');
    if (btn) {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            fetch('/mypage/password', {
                method: 'GET',
                credentials: 'same-origin'
            })
            .then(function(response) {
                if (response.status === 403) {
                    alert('권한이 없습니다');
                } else if (response.redirected) {
                    window.location.href = response.url;
                } else {
                    response.text().then(function(html) {
                        document.open();
                        document.write(html);
                        document.close();
                    });
                }
            });
        });
    }
});
</script>
</html> 