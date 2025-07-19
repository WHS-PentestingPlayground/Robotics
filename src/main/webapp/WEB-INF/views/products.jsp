<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.WHS.Robotics.util.SqlInjectionFilter" %>

<%
    String search = request.getParameter("search");
    boolean isSqlMalicious = SqlInjectionFilter.isMalicious(search);
    String sqlMaliciousError = null;
    if (isSqlMalicious) {
        sqlMaliciousError = "입력값에 허용되지 않은 문자가 포함되어 있습니다.";
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Our Robots - 대표 제품</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container">
    <h1 class="text-center mb-2">대표 로봇 제품</h1>
    <% if (sqlMaliciousError != null) { %>
        <div class="alert alert-danger" style="color:#b71c1c; background:#ffeaea; border:1px solid #f5c2c7; padding:10px; margin-bottom:16px; border-radius:6px;">
            <%= sqlMaliciousError %>
        </div>
    <% } %>
    <form method="get" action="/products" class="product-search-form">
        <input type="text" name="search" value="${search}" placeholder="제품명 검색" class="product-search-input">
        <button type="submit" class="product-search-btn">검색</button>
    </form>
    <div class="product-cards flex-row flex-wrap justify-center gap-32">
        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach var="product" items="${products}" varStatus="status">
                    <c:set var="reverseIndex" value="${fn:length(products) - status.index - 1}" />
                    <c:set var="product" value="${products[reverseIndex]}" />
                    <div class="product-card card-product">
                        <img src="/product-image?filename=${product.imagePath}" alt="${product.name} 이미지" class="img-product">
                        <div class="robot-name name-product">${product.name}</div>
                        <div class="robot-desc desc-product">
                            <c:out value="${product.description}" default="제품 설명이 없습니다."/>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div>등록된 제품이 없습니다.</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>

