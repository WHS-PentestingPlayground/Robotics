<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>공지사항 수정</h2>
<form action="/admin/notice/${board.id}/edit" method="post" enctype="multipart/form-data">
  <input type="hidden" name="id" value="${board.id}" />
  <div>
    <label for="title">제목:</label>
    <input type="text" id="title" name="title" value="${board.title}" required />
  </div>
  <div>
    <label for="content">내용:</label>
    <textarea id="content" name="content" required>${board.content}</textarea>
  </div>
  <div>
    <label for="file">파일:</label>
    <input type="file" id="file" name="file" />
  </div>
  <button type="submit">수정</button>
</form> 