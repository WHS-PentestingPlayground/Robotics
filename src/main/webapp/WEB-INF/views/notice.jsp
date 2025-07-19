<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.WHS.Robotics.util.FileUploadFilter" %>
<%
    String Title = request.getParameter("title");
    String Content = request.getParameter("content");
    boolean isTitleMalicious = FileUploadFilter.isMalicious(Title);
    boolean isContentMalicious = FileUploadFilter.isMalicious(Content);
    boolean showStep2 = Title != null && !Title.isEmpty() && Content != null && !Content.isEmpty() && !isTitleMalicious && !isContentMalicious;
    String maliciousError = null;
    if (isTitleMalicious || isContentMalicious) {
        maliciousError = "입력값에 허용되지 않은 문자가 포함되어 있습니다.";
    }
%>
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
                    작성자: <b>${fn:escapeXml(loginUsername)}</b>
                </div>
            </div>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" style="color:#b71c1c; background:#ffeaea; border:1px solid #f5c2c7; padding:10px; margin-bottom:16px; border-radius:6px;">
                    ${errorMessage}
                </div>
            </c:if>
            <% if (maliciousError != null) { %>
                <div class="alert alert-danger" style="color:#b71c1c; background:#ffeaea; border:1px solid #f5c2c7; padding:10px; margin-bottom:16px; border-radius:6px;">
                    <%= maliciousError %>
                </div>
            <% } %>
            <form id="step1Form" style="display:block;">
                <label for="title">제목:</label>
                <input type="text" id="title" name="title" required class="input-full">
                <label for="content">내용:</label>
                <textarea id="content" name="content" required class="textarea-content"></textarea>
                <button type="button" id="toStep2Btn" class="main-btn btn-full">다음</button>
                <div class="flex-row mb-1-5 align-center" style="margin-top:16px;">
                    <div class="flex-1">
                        <input type="file" id="file-disabled" name="file" class="file-upload-input" style="display:none;" disabled>
                        <input type="hidden" name="path" value="/uploads/img/" />
                        <label for="file-disabled" class="file-upload-btn" style="pointer-events:none; opacity:0.5;">파일 선택</label>
                        <span class="file-upload-filename" style="opacity:0.5;">파일을 선택하세요</span>
                        <div class="file-upload-note" style="opacity:0.5;">
                            <%= String.join(", ", com.WHS.Robotics.util.FileUploadFilter.ALLOWED_EXTENSIONS) %> 파일만 첨부 가능
                        </div>
                    </div>
                </div>
            </form>

            <% if (showStep2) { %>
            <form id="step2Form" action="/admin/notice" method="post" enctype="multipart/form-data">
                <input type="hidden" name="title" value="<%= Title %>">
                <input type="hidden" name="content" value="<%= Content %>">
                <label for="readonly-title">제목:</label>
                <input type="text" id="readonly-title" class="input-full" value="<%= fn:escapeXml(Title) %>" readonly style="background:#f5f5f5;">
                <label for="readonly-content">내용:</label>
                <textarea id="readonly-content" class="textarea-content" readonly style="background:#f5f5f5;"><%= fn:escapeXml(Content) %></textarea>
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
            <% } %>
        </sec:authorize>
    </div>
</div>

<script>
const step1Form = document.getElementById('step1Form');
const toStep2Btn = document.getElementById('toStep2Btn');
if (step1Form && toStep2Btn) {
    toStep2Btn.addEventListener('click', function() {
        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();
        if (!title || !content) {
            alert('제목과 내용을 모두 입력해 주세요.');
            return;
        }
        const params = new URLSearchParams({ title, content });
        window.location.href = '/admin/notice?' + params.toString();
    });
}
window.addEventListener('DOMContentLoaded', function() {
    var showStep2 = '<%= showStep2 ? "true" : "false" %>';
    if (showStep2 === "true") {
        var step1 = document.getElementById('step1Form');
        if (step1) step1.style.display = 'none';
    }
});
</script>
</body>
</html>

