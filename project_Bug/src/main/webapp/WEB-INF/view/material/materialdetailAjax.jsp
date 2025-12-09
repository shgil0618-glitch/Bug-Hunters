<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../inc/header.jsp" %>
<div class="container my-5"> 
<h2>${dto.title}</h2>

<img src="${dto.imageurl}" style="width:200px; margin-bottom:15px;" />

<p><strong>계절:</strong> ${dto.season}</p>
<p><strong>온도:</strong> ${dto.temperature}</p>
<p><strong>칼로리(100g):</strong> ${dto.calories100g}</p>

<h3>📌 효능</h3>
<p>${dto.efficacy}</p>

<h3>📌 구입 가이드</h3>
<p>${dto.buyguide}</p>

<h3>📌 손질 방법</h3>
<p>${dto.trimguide}</p>

<h3>📌 보관 방법</h3>
<p>${dto.storeguide}</p>

</div>
<%@ include file="../inc/footer.jsp" %>