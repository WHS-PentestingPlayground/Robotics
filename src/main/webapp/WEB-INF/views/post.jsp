<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>${board.title}</h2>
<p>${board.content}</p>
<p>작성자 ID: ${board.userId} / ${board.createdAt}</p>

<form action="/board/deletePost" method="post">
  <input type="hidden" name="id" value="${board.id}" />
  <button type="submit">게시글 삭제</button>
</form>

<h3>댓글</h3>
<ul>
  <c:forEach var="comment" items="${comments}">
    <li>${comment.content} (작성자: ${comment.userId}, ${comment.createdAt})
      <form action="/deleteComment" method="post" style="display:inline;">
        <input type="hidden" name="commentId" value="${comment.id}" />
        <input type="hidden" name="boardId" value="${board.id}" />
        <button type="submit">삭제</button>
      </form>
    </li>
  </c:forEach>
</ul>

<h4>댓글 작성</h4>
<form action="/comment" method="post">
  <input type="hidden" name="boardId" value="${board.id}" />
  <input type="hidden" name="userId" value="1" /> <!-- 세션 기반으로 수정 가능 -->
  <textarea name="content"></textarea><br>
  <button type="submit">댓글 작성</button>
</form>

