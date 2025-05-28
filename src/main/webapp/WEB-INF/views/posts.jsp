<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>게시글 목록</h2>
<a href="/board/newPost">새 글 작성</a>
<ul>
  <c:forEach var="post" items="${boards}">
    <li>
      <a href="/board/post?id=${post.id}">
          ${post.title} (작성자 ID: ${post.userId}) / ${post.createdAt}
      </a>
    </li>
  </c:forEach>
</ul>
