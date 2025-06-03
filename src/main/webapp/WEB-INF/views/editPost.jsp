<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container container-edit">
    <div class="card card-edit">
        <h2 class="text-center">게시글 수정</h2>
        <form action="/board/editPost" method="post">
            <input type="hidden" name="id" value="${board.id}" />
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" value="${board.title}" required class="input-full">

            <label for="content">내용:</label>
            <textarea id="content" name="content" required class="textarea-content">${board.content}</textarea>

            <input type="hidden" id="userId" name="userId" value="${loginUserId}" />
            <div class="mb-1-5 text-muted small">
                작성자: <b>${loginUsername}</b>
            </div>

            <button type="submit" class="main-btn btn-full">수정 완료</button>
        </form>
    </div>
</div>
</body>
</html> 