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
<div class="container" style="max-width:800px; margin:2rem auto;">
    <div class="card" style="padding:2rem; margin-bottom:2rem;">
        <h2>${board.title}</h2>
        <p>${board.content}</p>
        <p style="color:#888; font-size:0.95em;">
            작성자: ${username} / ${board.createdAt}
        </p>
        <div style="margin-top:1.5rem;">
            <sec:authorize access="isAuthenticated()">
                <c:if test="${loginUserId == board.userId || loginUserRole == 'ADMIN'}">
                    <form action="/board/deletePost" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${board.id}" />
                        <button type="submit" class="main-btn" style="background:#e74c3c; color:#fff;">삭제</button>
                    </form>
                </c:if>
                <c:if test="${loginUserId == board.userId}">
                    <a href="/board/editPost?id=${board.id}" class="main-btn" style="background:#3498db; color:#fff; margin-left:8px;">수정</a>
                </c:if>
            </sec:authorize>
        </div>
    </div>
    <div class="card" style="padding:2rem;">
        <h3>댓글</h3>
        <ul style="list-style:none; padding:0;">
            <c:forEach var="comment" items="${comments}">
                <li style="margin-bottom:0.7rem;">
                    <!-- 댓글 내용+버튼 그룹 -->
                    <div id="comment-view-${comment.id}" style="display:inline;">
                        ${comment.content} (작성자: ${commentUsernames[comment.id]}, ${comment.createdAt})
                        <sec:authorize access="isAuthenticated()">
                            <c:if test="${loginUserId == comment.userId || loginUserRole == 'ADMIN'}">
                                <form action="/deleteComment" method="post" style="display:inline; margin-left:8px;">
                                    <input type="hidden" name="commentId" value="${comment.id}" />
                                    <input type="hidden" name="boardId" value="${board.id}" />
                                    <button type="submit" class="main-btn" style="background:#e67e22; color:#fff; padding:4px 12px; font-size:0.95em;">삭제</button>
                                </form>
                            </c:if>
                            <c:if test="${loginUserId == comment.userId}">
                                <button type="button" class="main-btn" style="background:#3498db; color:#fff; padding:4px 12px; font-size:0.95em; margin-left:8px;"
                                    onclick="showEditForm('${comment.id}')">수정</button>
                            </c:if>
                        </sec:authorize>
                    </div>
                    <!-- 수정 폼 (초기에는 숨김) -->
                    <form id="edit-form-${comment.id}" action="/updateComment" method="post" style="display:none; margin-left:8px;">
                        <input type="hidden" name="commentId" value="${comment.id}" />
                        <input type="hidden" name="boardId" value="${board.id}" />
                        <input type="text" name="content" id="edit-input-${comment.id}" value="${comment.content}" style="width:120px; font-size:0.95em;" />
                        <button type="submit" class="main-btn" style="background:#27ae60; color:#fff; padding:4px 12px; font-size:0.95em;">완료</button>
                        <button type="button" class="main-btn" style="background:#aaa; color:#fff; padding:4px 12px; font-size:0.95em;" onclick="hideEditForm('${comment.id}')">취소</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
        <h4 style="margin-top:2rem;">댓글 작성</h4>
        <form action="/comment" method="post" style="margin-top:1rem;">
            <input type="hidden" name="boardId" value="${board.id}" />
            <input type="hidden" name="userId" value="${loginUserId}" />
            <div style="margin-bottom:10px; color:#555; font-size:0.97em;">
                작성자: <b>${loginUsername}</b>
            </div>
            <textarea name="content" style="width:100%; min-height:60px; border-radius:6px; border:1px solid #ddd; margin-bottom:10px; padding:8px;"></textarea><br>
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

