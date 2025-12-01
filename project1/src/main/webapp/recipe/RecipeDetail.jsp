<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<div class="container card my-5 p-4">
    <h3 class="card-header">🍳 레시피 상세보기</h3>

    <c:if test="${not empty recipe}">
        <div class="mb-3">
           <img src="${pageContext.request.contextPath}/upload/${dto.rfile}" class="img-fluid mb-3" style="max-width:200px;">
        </div>

        <p><b>제목:</b> ${recipe.title}</p>
        <p><b>카테고리:</b> ${recipe.category}</p>
        <p><b>난이도:</b> ${recipe.difficulty}</p>
        <p><b>조리 시간:</b> ${recipe.cookTime} 분</p>
        <p><b>설명:</b> ${recipe.description}</p>

        <hr>

        <h5>📦 재료 목록</h5>
        <a href="${pageContext.request.contextPath}/ingredientInsertForm.ingre?recipeId=${recipe.recipeId}" class="btn btn-success my-2">재료 추가</a>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>이름</th>
                    <th>수량</th>
                    <th>단위</th>
                    <th>칼로리</th>
                    <th>수정</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="i" items="${ilist}">
                    <tr>
                        <td>${i.ingredientName}</td>
                        <td>${i.quantity}</td>
                        <td>${i.unit}</td>
                        <td>${i.kcal}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ingredientUpdateForm.ingre?ingredientId=${i.ingredientId}" class="btn btn-warning btn-sm">수정</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ingredientDelete.ingre?ingredientId=${i.ingredientId}&recipeId=${recipe.recipeId}" class="btn btn-danger btn-sm">삭제</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="text-end mt-3">
            <a href="${pageContext.request.contextPath}/recipeUpdateForm.reci?recipeId=${recipe.recipeId}" class="btn btn-primary">레시피 수정</a>
            <a href="${pageContext.request.contextPath}/recipeDelete.reci?recipeId=${recipe.recipeId}" class="btn btn-danger">레시피 삭제</a>
            <a href="${pageContext.request.contextPath}/recipeList.reci" class="btn btn-secondary">목록</a>
        </div>
    </c:if>

    <c:if test="${not empty errorMsg}">
        <div class="alert alert-danger">${errorMsg}</div>
        <a href="${pageContext.request.contextPath}/recipeList.reci" class="btn btn-secondary">목록으로 돌아가기</a>
    </c:if>
</div>

<%@include file="../inc/footer.jsp" %>
