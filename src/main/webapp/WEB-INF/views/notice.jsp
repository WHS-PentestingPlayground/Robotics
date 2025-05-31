<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<sec:authorize access="hasRole('ADMIN')">
    <h2>공지사항 작성</h2>
    <form action="/admin/notice" method="post" enctype="multipart/form-data">
        제목: <input type="text" name="title"><br>
        내용: <textarea name="content"></textarea><br>
        파일: <input type="file" name="file"><br>
        작성자 ID: <input type="text" name="userId" value="1"><br> <!-- 세션 연동 필요 -->
        <button type="submit">작성</button>
    </form>
</sec:authorize>

