<%@page import="com.thejoa703.dto.IngredientDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<div class="container card my-5 p-4">
  <h3 class="card-header">📦 재료 상세보기</h3>

  <p><b>재료 이름:</b> ${ingredient.ingredientName}</p>
  <p><b>수량:</b> ${ingredient.quantity}</p>
  <p><b>단위:</b> ${ingredient.unit}</p>
  <p><b>칼로리:</b> ${ingredient.kcal} kcal</p>
  <p><b>탄수화물:</b> ${ingredient.carbs} g</p>
  <p><b>단백질:</b> ${ingredient.protein} g</p>
  <p><b>지방:</b> ${ingredient.fat} g</p>
  <p><b>알레르기 유발 성분:</b> ${ingredient.allergens}</p>

  <div class="text-end mt-3">
    <a href="${pageContext.request.contextPath}/ingredientUpdateForm.ingre?ingredientId=${ingredient.ingredientId}" class="btn btn-primary">수정</a>
    <a href="${pageContext.request.contextPath}/ingredientDelete.ingre?ingredientId=${ingredient.ingredientId}&recipeId=${ingredient.recipeId}" class="btn btn-danger">삭제</a>
    <a href="${pageContext.request.contextPath}/recipeDetail.ingre?recipeId=${ingredient.recipeId}" class="btn btn-secondary">레시피로 돌아가기</a>
  </div>
</div>

<%@include file="../inc/footer.jsp" %>
