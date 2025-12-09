<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>

<style>
    .material-detail-container {
        max-width: 900px;
        margin: 40px auto;
        background: #fff;
        border-radius: 12px;
        padding: 30px 40px;
        box-shadow: 0 4px 20px rgba(0,0,0,0.08);
        text-align: center;
    }

    .material-title {
        font-size: 2rem;
        font-weight: bold;
        margin-bottom: 20px;
        color: #333;
    }

    .material-image {
        width: 260px;
        border-radius: 10px;
        margin-bottom: 25px;
        box-shadow: 0 3px 12px rgba(0,0,0,0.1);
    }

    .material-info {
        display: flex;
        justify-content: center;
        gap: 40px;
        margin-bottom: 25px;
        color: #555;
        font-size: 1rem;
    }

    .material-section {
        text-align: left;
        margin-top: 25px;
    }

    .material-section h3 {
        margin-top: 30px;
        margin-bottom: 10px;
        font-weight: 600;
        color: #176f3c;
    }

    .material-section p {
        background: #f8faf7;
        padding: 15px;
        border-radius: 8px;
        line-height: 1.6;
        color: #444;
        border-left: 4px solid #a2d9b5;
    }

    /* 뒤로가기 버튼 */
    .back-btn {
        margin-top: 30px;
        float: right;
    }
</style>

<div class="material-detail-container">
    <h2 class="material-title">${dto.title}</h2>

    <div>
        <img src="${pageContext.request.contextPath}/upload/${dto.imageurl}" 
             alt="${dto.title}" class="material-image" />
    </div>

    <div class="material-info">
        <div>🌤 계절: <b>${dto.season}</b></div>
        <div>🌡 온도: <b>${dto.temperature}</b></div>
        <div>🔥 100g 열량: <b>${dto.calories100g} kcal</b></div>
    </div>

    <hr>

    <div class="material-section">
        <h3>🍀 효능</h3>
        <p>${dto.efficacy}</p>

        <h3>🛒 구입 가이드</h3>
        <p>${dto.buyguide}</p>

        <h3>🔪 손질 방법</h3>
        <p>${dto.trimguide}</p>

        <h3>📦 보관 방법</h3>
        <p>${dto.storeguide}</p>
    </div>

    <a href="javascript:history.go(-1)" class="btn btn-secondary back-btn">← 뒤로가기</a>
</div>

<%@ include file="../inc/footer.jsp"%>