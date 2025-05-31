<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 수정</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container" style="max-width:600px; margin:3rem auto;">
    <div class="card" style="padding:2rem;">
        <h2 style="text-align:center;">공지사항 수정</h2>
        <form action="/board/editNotice" method="post">
            <input type="hidden" name="id" value="${notice.id}" />
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" value="${notice.title}" required style="width:100%; padding:10px; border-radius:6px; border:1px solid #ddd; margin-bottom:1rem;">

            <label for="content">내용:</label>
            <textarea id="content" name="content" required style="width:100%; min-height:120px; border-radius:6px; border:1px solid #ddd; margin-bottom:1.5rem; padding:10px;">${notice.content}</textarea>

            <button type="submit" class="main-btn" style="width:100%;">수정</button>
        </form>
    </div>
</div>
</body>
</html> 