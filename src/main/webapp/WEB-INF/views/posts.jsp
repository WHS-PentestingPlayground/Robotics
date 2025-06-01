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
<div class="container container-posts">
    <div class="flex-row justify-between align-center mb-1-5">
        <h1>게시판</h1>
        <div>
            <sec:authorize access="hasRole('ADMIN')">
                <a href="/admin/notice" class="main-btn mr-8">공지사항 작성</a>
            </sec:authorize>
            <a href="/board/newPost" class="main-btn">새 글 작성</a>
        </div>
    </div>
    <div class="card card-posts mb-2">
        <h2>[공지사항]</h2>
        <div class="flex-col gap-16">
            <c:forEach var="post" items="${boards}">
                <c:if test="${post.notice}">
                    <div class="notice-box">
                        <a href="/board/post?id=${post.id}" class="link-blue notice-title">${post.title}</a>
                        <div class="notice-author">작성자: ${userNamesByBoardId[post.id]}</div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="card card-posts">
        <h2>[게시글 목록]</h2>
        <div class="flex-col gap-16">
            <c:forEach var="post" items="${boards}">
                <c:if test="${!post.notice}">
                    <div class="post-box">
                        <a href="/board/post?id=${post.id}" class="post-title">${post.title}</a>
                        <div class="post-author">작성자: ${userNamesByBoardId[post.id]}</div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

