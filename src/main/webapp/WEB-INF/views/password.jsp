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
            <input type="hidden" name="username" value="${user.username}">
            
            <div class="form-group">
                <label for="currentPassword">현재 비밀번호:</label>
                <input type="password" id="currentPassword" name="currentPassword" required>
            </div>
            
            <div class="form-group">
                <label for="newPassword">새 비밀번호:</label>
                <input type="password" id="newPassword" name="newPassword" required>
                <div class="password-requirements">
                    ※ 8~16자리, 영문 대소문자/숫자/특수문자를 모두 포함해야 합니다.
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
            </small>
        </div>
    </div>

    <script>
        // 에러 메시지를 화면에 표시하는 함수
        function showError(message) {
            // 기존 에러 메시지 제거
            const existingError = document.querySelector('.client-error-message');
            if (existingError) {
                existingError.remove();
            }
            
            // 새 에러 메시지 생성
            const errorDiv = document.createElement('div');
            errorDiv.className = 'error-message client-error-message';
            errorDiv.textContent = message;
            
            // 폼 바로 앞에 삽입 (서버 에러/성공 메시지 다음 위치)
            const form = document.querySelector('.password-form');
            form.parentNode.insertBefore(errorDiv, form);
            
            // 에러 메시지 위치로 스크롤
            errorDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
        
        // 에러 메시지 제거 함수
        function clearError() {
            const existingError = document.querySelector('.client-error-message');
            if (existingError) {
                existingError.remove();
            }
        }
        
        // 폼 제출 전 비밀번호 확인 검증
        document.querySelector('.password-form').addEventListener('submit', function(e) {
            clearError();
        });
        
        // 실시간 새 비밀번호 유효성 검사
        document.getElementById('newPassword').addEventListener('input', function() {
            clearError(); // 기존 에러 메시지 제거
            
            const newPassword = this.value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const requirementsElement = document.querySelector('.password-requirements');
            const confirmElement = document.getElementById('confirmPassword');
            
            // 기존 클래스 제거
            this.classList.remove('password-valid', 'password-invalid');
            requirementsElement.classList.remove('requirements-valid', 'requirements-invalid');
            
            if (newPassword.length > 0) {
                // 비밀번호 규칙 검사: 8~16자리, 영문 대소문자/숫자/특수문자 모두 포함
                let isValid = true;
                
                if (newPassword.length < 8 || newPassword.length > 16) {
                    isValid = false;
                }
                
                let hasLetter = /[a-zA-Z]/.test(newPassword);
                let hasNumber = /[0-9]/.test(newPassword);
                let hasSpecial = /[^a-zA-Z0-9]/.test(newPassword);
                
                if (!hasLetter || !hasNumber || !hasSpecial) {
                    isValid = false;
                }
                
                // 시각적 피드백 적용 (입력 필드 + 요구사항 메시지)
                if (isValid) {
                    this.classList.add('password-valid');
                    requirementsElement.classList.add('requirements-valid');
                } else {
                    this.classList.add('password-invalid');
                    requirementsElement.classList.add('requirements-invalid');
                }
            }
            
            // 확인 비밀번호와의 일치 검사 (confirmPassword에 값이 있을 때만)
            if (confirmPassword.length > 0) {
                confirmElement.classList.remove('password-match', 'password-mismatch');
                
                if (newPassword === confirmPassword) {
                    confirmElement.classList.add('password-match');
                } else {
                    confirmElement.classList.add('password-mismatch');
                }
            }
        });
        
        // 입력 시 에러 메시지 제거
        document.getElementById('confirmPassword').addEventListener('input', clearError);
        document.getElementById('currentPassword').addEventListener('input', clearError);
        
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