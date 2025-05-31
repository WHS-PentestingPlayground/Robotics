<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Our Robots - 대표 제품</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<div class="container">
    <h1 style="text-align:center; margin-bottom:2rem;">대표 로봇 제품</h1>
    <div class="product-cards" style="display:flex; gap:32px; justify-content:center; flex-wrap:wrap;">
        <div class="product-card card" style="flex:1 1 300px; max-width:340px; min-width:260px; background:#fff; border-radius:12px; box-shadow:0 2px 12px rgba(0,0,0,0.08); padding:32px 18px; text-align:center;">
            <img src="https://placehold.co/180x180?text=AlphaBot" alt="AlphaBot 이미지" style="width:180px; height:180px; object-fit:cover; border-radius:8px; margin-bottom:18px;">
            <div class="robot-name" style="font-size:1.3em; font-weight:bold; color:#1a2533; margin-bottom:10px;">AlphaBot</div>
            <div class="robot-desc" style="color:#4a5a6a; font-size:1em;">AlphaBot은 스마트 공장 자동화를 위한 지능형 운반 로봇입니다. 고성능 센서와 AI 기반 경로 최적화로 효율적인 물류 이동을 지원합니다.</div>
        </div>
        <div class="product-card card" style="flex:1 1 300px; max-width:340px; min-width:260px; background:#fff; border-radius:12px; box-shadow:0 2px 12px rgba(0,0,0,0.08); padding:32px 18px; text-align:center;">
            <img src="https://placehold.co/180x180?text=CareBot" alt="CareBot 이미지" style="width:180px; height:180px; object-fit:cover; border-radius:8px; margin-bottom:18px;">
            <div class="robot-name" style="font-size:1.3em; font-weight:bold; color:#1a2533; margin-bottom:10px;">CareBot</div>
            <div class="robot-desc" style="color:#4a5a6a; font-size:1em;">CareBot은 병원 및 요양 시설에서 환자 케어를 돕는 서비스 로봇입니다. 음성 인식과 자율 주행 기능으로 안전하고 친근한 서비스를 제공합니다.</div>
        </div>
        <div class="product-card card" style="flex:1 1 300px; max-width:340px; min-width:260px; background:#fff; border-radius:12px; box-shadow:0 2px 12px rgba(0,0,0,0.08); padding:32px 18px; text-align:center;">
            <img src="https://placehold.co/180x180?text=EduBot" alt="EduBot 이미지" style="width:180px; height:180px; object-fit:cover; border-radius:8px; margin-bottom:18px;">
            <div class="robot-name" style="font-size:1.3em; font-weight:bold; color:#1a2533; margin-bottom:10px;">EduBot</div>
            <div class="robot-desc" style="color:#4a5a6a; font-size:1em;">EduBot은 교육 현장에서 활용되는 인터랙티브 학습 로봇입니다. 다양한 콘텐츠와 상호작용으로 창의적이고 즐거운 학습 환경을 만듭니다.</div>
        </div>
    </div>
</div>
</body>
</html>
