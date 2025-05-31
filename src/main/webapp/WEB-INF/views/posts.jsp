<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>




<sec:authorize access="hasRole('ADMIN')">
<a href="/admin/notice">공지사항 작성</a><br>
</sec:authorize>
<a href="/board/newPost">새 글 작성</a>

<h2>[공지사항]</h2>
<ul>
  <c:forEach var="post" items="${boards}">
    <c:if test="${post.notice}">
      <li>
        <a href="/board/post?id=${post.id}">
          ${post.title} (작성자 ID: ${post.userId}) / ${post.createdAt}
        </a>
      </li>
    </c:if>
  </c:forEach>
</ul>

<h2>[게시글 목록]</h2>
<ul>
  <c:forEach var="post" items="${boards}">
    <c:if test="${!post.notice}">
      <li>
        <a href="/board/post?id=${post.id}">
          ${post.title} (작성자 ID: ${post.userId}) / ${post.createdAt}
        </a>
      </li>
    </c:if>
  </c:forEach>
</ul>

