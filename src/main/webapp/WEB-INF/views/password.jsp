<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경 - WH 로보틱스</title>
    <link rel="stylesheet" href="/css/mypage.css">
    <link rel="stylesheet" href="/css/password.css">
</head>
<body>
    <div class="password-change-container">
        <div class="mypage-header">
            <h1>비밀번호 변경</h1>
        </div>
        
        <div class="info-message">
            <strong>알림:</strong> 기업 회원 이상만 비밀번호 변경이 가능합니다.
        </div>
        
        <!-- 에러 메시지 출력 -->
        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>
        
        <!-- 성공 메시지 출력 -->
        <c:if test="${not empty success}">
            <div class="success-message">
                ${success}
            </div>
        </c:if>
        
        <form action="/mypage/password" method="post" class="password-form"> 
            <!-- IDOR 취약점을 위한 숨겨진 필드 (username 기준) -->
            <input type="hidden" name="username" value="${user.username}">
            
            <div class="form-group">
                <label for="currentPassword">현재 비밀번호:</label>
                <input type="password" id="currentPassword" name="currentPassword" required>
            </div>
            
            <div class="form-group">
                <label for="newPassword">새 비밀번호:</label>
                <input type="password" id="newPassword" name="newPassword" required>
                <div class="password-requirements">
                    ※ 보안을 위해 8자 이상의 복잡한 비밀번호를 사용하세요.
                </div>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">새 비밀번호 확인:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            
            <div class="btn-group">
                <button type="submit" class="btn btn-primary">비밀번호 변경</button>
                <a href="/mypage" class="btn btn-secondary">취소</a>
            </div>
        </form>
        
        <div class="user-info-section">
            <small class="user-info-text">
                회원 정보: <strong>${user.username}</strong> (${user.role})
                <c:if test="${not empty user.business_token}">
                    <br>기업 토큰: ${user.business_token}
                </c:if>
            </small>
        </div>
    </div>

    <script>
        // 폼 제출 전 비밀번호 확인 검증
        document.querySelector('.password-form').addEventListener('submit', function(e) {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (newPassword !== confirmPassword) {
                e.preventDefault();
                alert('새 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
                return false;
            }
            
            if (newPassword.length < 8) {
                e.preventDefault();
                alert('보안을 위해 8자 이상의 비밀번호를 사용해주세요.');
                return false;
            }
        });
        
        // 실시간 비밀번호 일치 확인 (CSS 클래스 사용)
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = this.value;
            
            // 기존 클래스 제거
            this.classList.remove('password-match', 'password-mismatch');
            
            if (confirmPassword && newPassword !== confirmPassword) {
                this.classList.add('password-mismatch');
            } else if (confirmPassword && newPassword === confirmPassword) {
                this.classList.add('password-match');
            }
        });
    </script>
</body>
</html> 