<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container" style="max-width:900px; margin:2rem auto;">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:1.5rem;">
        <h1>게시판</h1>
        <div>
            <sec:authorize access="hasRole('ADMIN')">
                <a href="/admin/notice" class="main-btn" style="margin-right:8px;">공지사항 작성</a>
            </sec:authorize>
            <a href="/board/newPost" class="main-btn">새 글 작성</a>
        </div>
    </div>
    <div class="card" style="padding:2rem; margin-bottom:2rem;">
        <h2>[공지사항]</h2>
        <div style="display:flex; flex-direction:column; gap:16px;">
            <c:forEach var="post" items="${boards}">
                <c:if test="${post.notice}">
                    <div style="background:#eaf3fb; border-radius:10px; box-shadow:0 1px 6px rgba(0,0,0,0.06); padding:18px 20px;">
                        <a href="/board/post?id=${post.id}" style="color:#003366; font-weight:600; text-decoration:none; font-size:1.1em;">${post.title}</a>
                        <div style="color:#4a5a6a; font-size:0.97em; margin-top:6px;">
                            작성자: ${userNamesByBoardId[post.id]}
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="card" style="padding:2rem;">
        <h2>[게시글 목록]</h2>
        <div style="display:flex; flex-direction:column; gap:16px;">
            <c:forEach var="post" items="${boards}">
                <c:if test="${!post.notice}">
                    <div style="background:#fff; border-radius:10px; box-shadow:0 1px 6px rgba(0,0,0,0.08); padding:18px 20px;">
                        <a href="/board/post?id=${post.id}" style="color:#222; text-decoration:none; font-size:1.1em;">${post.title}</a>
                        <div style="color:#4a5a6a; font-size:0.97em; margin-top:6px;">
                            작성자: ${userNamesByBoardId[post.id]}
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

