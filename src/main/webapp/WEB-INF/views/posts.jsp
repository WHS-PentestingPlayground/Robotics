<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<h2 class="section-title">공지사항</h2>
<ul class="post-list">
    <c:forEach var="post" items="${boards}">
        <c:if test="${post.notice}">
            <li class="post-item">
                <a href="/board/post?id=${post.id}" class="notice-title">${fn:escapeXml(post.title)}</a>
                <span class="notice-author">작성자: ${fn:escapeXml(userNamesByBoardId[post.id])}</span>
            </li>
        </c:if>
    </c:forEach>
</ul>

<h2 class="section-title">Q&A</h2>
<ul class="post-list">
    <c:forEach var="post" items="${boards}">
        <c:if test="${!post.notice}">
            <li class="post-item">
                <a href="/board/post?id=${post.id}" class="post-title">${fn:escapeXml(post.title)}</a>
                <span class="post-author">작성자: ${fn:escapeXml(userNamesByBoardId[post.id])}</span>
            </li>
        </c:if>
    </c:forEach>
</ul>

<div class="board-actions">
  <sec:authorize access="hasRole('ADMIN')">
      <a href="/admin/notice" class="main-btn">공지사항 작성</a>
  </sec:authorize>
  <a href="/board/newPost" class="main-btn">새 글 작성</a>
</div>
</body>
</html>

