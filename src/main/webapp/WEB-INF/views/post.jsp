<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <c:choose>
                <c:when test="${board.notice}">
                    <sec:authorize access="hasRole('ADMIN')">
                        <form action="/board/deletePost" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${board.id}" />
                            <button type="submit" class="main-btn" style="background:#e74c3c; color:#fff;">게시글 삭제</button>
                        </form>
                    </sec:authorize>
                </c:when>
                <c:otherwise>
                    <form action="/board/deletePost" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${board.id}" />
                        <button type="submit" class="main-btn" style="background:#e74c3c; color:#fff;">게시글 삭제</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="card" style="padding:2rem;">
        <h3>댓글</h3>
        <ul style="list-style:none; padding:0;">
            <c:forEach var="comment" items="${comments}">
                <li style="margin-bottom:0.7rem;">
                    ${comment.content} (작성자: ${commentUsernames[comment.id]}, ${comment.createdAt})
                    <form action="/deleteComment" method="post" style="display:inline; margin-left:8px;">
                        <input type="hidden" name="commentId" value="${comment.id}" />
                        <input type="hidden" name="boardId" value="${board.id}" />
                        <button type="submit" class="main-btn" style="background:#e67e22; color:#fff; padding:4px 12px; font-size:0.95em;">삭제</button>
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
</body>
</html>

