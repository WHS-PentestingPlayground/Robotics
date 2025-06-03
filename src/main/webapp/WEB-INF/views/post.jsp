<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 상세</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container container-post">
    <div class="card card-post mb-2">
        <h2>${board.title}</h2>
        <p>${board.content}</p>
        <c:if test="${not empty attachedFiles}">
            <div class="mb-1-5">
                <c:forEach var="file" items="${attachedFiles}">
                    <div class="mb-1">
                        <img src="/uploads/${file.filePath}" alt="첨부 이미지" class="img-attach" />
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <p class="text-muted small">
            작성자: ${username} / ${board.createdAt}
        </p>
        <div class="mt-1-5">
            <sec:authorize access="isAuthenticated()">
                <c:if test="${loginUserId == board.userId || loginUserRole == 'ADMIN'}">
                    <form action="/board/deletePost" method="post" class="d-inline">
                        <input type="hidden" name="id" value="${board.id}" />
                        <button type="submit" class="main-btn btn-danger">삭제</button>
                    </form>
                </c:if>
                <c:if test="${loginUserId == board.userId}">
                    <c:choose>
                        <c:when test="${board.notice}">
                            <a href="/board/editNotice?id=${board.id}" class="main-btn btn-edit ml-8">수정</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/board/editPost?id=${board.id}" class="main-btn btn-edit ml-8">수정</a>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </sec:authorize>
        </div>
    </div>
    <div class="card card-post">
        <h3>댓글</h3>
        <ul class="list-unstyled">
            <c:forEach var="comment" items="${comments}">
                <li class="mb-07">
                    <!-- 댓글 내용+버튼 그룹 -->
                    <div id="comment-view-${comment.id}" class="d-inline">
                        ${comment.content} (작성자: ${commentUsernames[comment.id]}, ${comment.createdAt})
                        <sec:authorize access="isAuthenticated()">
                            <c:if test="${loginUserId == comment.userId || loginUserRole == 'ADMIN'}">
                                <form action="/deleteComment" method="post" class="d-inline ml-8">
                                    <input type="hidden" name="commentId" value="${comment.id}" />
                                    <input type="hidden" name="boardId" value="${board.id}" />
                                    <button type="submit" class="main-btn btn-warning btn-xs">삭제</button>
                                </form>
                            </c:if>
                            <c:if test="${loginUserId == comment.userId}">
                                <button type="button" class="main-btn btn-edit btn-xs ml-8"
                                    onclick="showEditForm('${comment.id}')">수정</button>
                            </c:if>
                        </sec:authorize>
                    </div>
                    <!-- 수정 폼 (초기에는 숨김) -->
                    <form id="edit-form-${comment.id}" action="/updateComment" method="post" class="d-none ml-8">
                        <input type="hidden" name="commentId" value="${comment.id}" />
                        <input type="hidden" name="boardId" value="${board.id}" />
                        <input type="text" name="content" id="edit-input-${comment.id}" value="${comment.content}" class="input-xs" />
                        <button type="submit" class="main-btn btn-success btn-xs">완료</button>
                        <button type="button" class="main-btn btn-cancel btn-xs" onclick="hideEditForm('${comment.id}')">취소</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
        <h4 class="mt-2">댓글 작성</h4>
        <form action="/comment" method="post" class="mt-1">
            <input type="hidden" name="boardId" value="${board.id}" />
            <input type="hidden" name="userId" value="${loginUserId}" />
            <div class="mb-1 text-muted small">
                작성자: <b>${loginUsername}</b>
            </div>
            <textarea name="content" class="textarea-comment"></textarea><br>
            <button type="submit" class="main-btn">댓글 작성</button>
        </form>
    </div>
</div>

<script>
function showEditForm(commentId) {
    document.getElementById('comment-view-' + commentId).style.display = 'none';
    document.getElementById('edit-form-' + commentId).style.display = 'inline';
    document.getElementById('edit-input-' + commentId).focus();
}
function hideEditForm(commentId) {
    document.getElementById('comment-view-' + commentId).style.display = 'inline';
    document.getElementById('edit-form-' + commentId).style.display = 'none';
}
</script>
</body>
</html>

