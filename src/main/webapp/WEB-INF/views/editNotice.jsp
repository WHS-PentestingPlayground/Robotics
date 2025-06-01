<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 수정</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container container-notice">
    <div class="card card-notice">
        <h2 class="text-center">공지사항 수정</h2>
        <form action="/board/editNotice" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${notice.id}" />
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" value="${notice.title}" required class="input-full">

            <label for="content">내용:</label>
            <textarea id="content" name="content" required class="textarea-content mb-1-5">${notice.content}</textarea>

            <c:if test="${not empty attachedFiles}">
                <div class="mb-1">
                    <b>기존 첨부파일:</b>
                    <c:forEach var="file" items="${attachedFiles}">
                        <a href="/uploads/${file.filePath}" target="_blank">${file.fileName}</a>
                    </c:forEach>
                </div>
            </c:if>
            <div class="mb-1-5">
                <label for="file">첨부파일 교체:</label>
                <input type="file" id="file" name="file">
                <div class="text-muted small">새 파일을 첨부하지 않으면 기존 파일이 유지됩니다.</div>
            </div>

            <button type="submit" class="main-btn btn-full">수정</button>
        </form>
    </div>
</div>
</body>
</html> 