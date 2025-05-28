<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2>게시글 작성</h2>
<form action="/board/newPost" method="post">
  제목: <input type="text" name="title"><br>
  내용: <textarea name="content"></textarea><br>
  작성자 ID: <input type="text" name="userId" value="1"><br> <!-- 세션 연동 필요 -->
  <button type="submit">작성</button>
</form>