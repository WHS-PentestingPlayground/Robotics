<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>새 글 작성</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container container-edit">
    <div class="card card-edit">
        <h2 class="text-center">새 글 작성</h2>
        <div class="flex-1 author-box-right">
            <div class="text-muted small">
                작성자: <b>${loginUsername}</b>
            </div>
        </div>
        <form action="/board/newPost" method="post">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" required class="input-full">

            <label for="content">내용:</label>
            <textarea id="content" name="content" required class="textarea-content"></textarea>
            <input type="hidden" id="userId" name="userId" value="${loginUserId}" />
            <button type="submit" class="main-btn btn-full">등록</button>
        </form>
    </div>
</div>
</body>
</html>