<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="recipeId" value="${param.recipeId}" />

<div class="container card my-5 p-4">
  <h3 class="card-header">➕ 재료 등록</h3>

  <form action="${pageContext.request.contextPath}/ingredientInsert.ingre" method="post">
    <input type="hidden" name="recipeId" value="${recipeId}">

    <div class="mb-3 mt-3">
      <label class="form-label">재료 이름:</label>
      <input type="text" class="form-control" name="ingredientName" required>
    </div>

    <div class="mb-3">
      <label class="form-label">수량:</label>
      <input type="number" class="form-control" name="quantity">
    </div>

    <div class="mb-3">
      <label class="form-label">단위:</label>
      <input type="text" class="form-control" name="unit">
    </div>

    <div class="mb-3">
      <label class="form-label">칼로리(kcal):</label>
      <input type="number" class="form-control" name="kcal">
    </div>

    <div class="mb-3">
      <label class="form-label">탄수화물(g):</label>
      <input type="number" class="form-control" name="carbs">
    </div>

    <div class="mb-3">
      <label class="form-label">단백질(g):</label>
      <input type="number" class="form-control" name="protein">
    </div>

    <div class="mb-3">
      <label class="form-label">지방(g):</label>
      <input type="number" class="form-control" name="fat">
    </div>

    <div class="mb-3">
      <label class="form-label">알레르기 유발 성분:</label>
      <input type="text" class="form-control" name="allergens">
    </div>

    <div class="mb-3 text-end">
      <button type="submit" class="btn btn-primary">등록</button>
      <a href="${pageContext.request.contextPath}/recipeDetail.reci?recipeId=${recipeId}" class="btn btn-danger">취소</a>
    </div>
  </form>
</div>

<%@include file="../inc/footer.jsp" %>
