<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.WHS.Robotics.util.FileUploadFilter" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 작성</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container container-notice">
    <div class="card card-notice">
        <sec:authorize access="hasRole('ADMIN')">
            <h2 class="text-center">공지사항 작성</h2>
            <div class="flex-1 author-box-right">
                <div class="text-muted small">
                    작성자: <b>${loginUsername}</b>
                </div>
            </div>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" style="color:#b71c1c; background:#ffeaea; border:1px solid #f5c2c7; padding:10px; margin-bottom:16px; border-radius:6px;">
                    ${errorMessage}
                </div>
            </c:if>
            <form action="/admin/notice" method="post" enctype="multipart/form-data">
                <label for="title">제목:</label>
                <input type="text" id="title" name="title" required class="input-full" value="${title}">

                <label for="content">내용:</label>
                <textarea id="content" name="content" required class="textarea-content">${content}</textarea>

                <div class="flex-row mb-1-5 align-center">
                    <div class="flex-1">
                        <input type="file" id="file" name="file" class="file-upload-input" style="display:none;" onchange="document.getElementById('file-upload-filename').textContent = this.files[0] ? this.files[0].name : '파일을 선택하세요';">
                        <input type="hidden" name="path" value="/uploads/img/" />
                        <label for="file" class="file-upload-btn">파일 선택</label>
                        <span id="file-upload-filename" class="file-upload-filename">파일을 선택하세요</span>
                        <div class="file-upload-note">
                            <%= String.join(", ", com.WHS.Robotics.util.FileUploadFilter.ALLOWED_EXTENSIONS) %> 파일만 첨부 가능
                        </div>
                    </div>
                </div>
                <input type="hidden" id="userId" name="userId" value="${loginUserId}" />
                <button type="submit" class="main-btn btn-full">등록</button>
            </form>
        </sec:authorize>
    </div>
</div>
</body>
</html>

