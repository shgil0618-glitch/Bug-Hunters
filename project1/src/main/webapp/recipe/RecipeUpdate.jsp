<%@page import="com.thejoa703.dto.RecipeDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<div class="container card my-5 p-4">
  <h3 class="card-header">✏ 레시피 수정</h3>

  <form action="${pageContext.request.contextPath}/recipeUpdate.reci" method="post">

    <input type="hidden" name="recipeId" value="${recipe.recipeId}">

    <div class="mb-3 mt-3">
      <label class="form-label">제목:</label>
      <input type="text" class="form-control" name="title" value="${recipe.title}" required>
    </div>

    <div class="mb-3">
      <label class="form-label">카테고리:</label>
      <select class="form-select" name="category">
        <option value="한식" ${recipe.category == '한식' ? 'selected' : ''}>한식</option>
        <option value="양식" ${recipe.category == '양식' ? 'selected' : ''}>양식</option>
        <option value="중식" ${recipe.category == '중식' ? 'selected' : ''}>중식</option>
        <option value="일식" ${recipe.category == '일식' ? 'selected' : ''}>일식</option>
        <option value="기타" ${recipe.category == '기타' ? 'selected' : ''}>기타</option>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">간략 설명:</label>
      <textarea class="form-control" name="description" rows="4">${recipe.description}</textarea>
    </div>

    <div class="mb-3">
      <label class="form-label">조리 시간(분):</label>
      <input type="number" class="form-control" name="cookTime" value="${recipe.cookTime}">
    </div>

    <div class="mb-3">
      <label class="form-label">난이도:</label>
      <select class="form-select" name="difficulty">
        <option value="쉬움" ${recipe.difficulty == '쉬움' ? 'selected' : ''}>쉬움</option>
        <option value="중간" ${recipe.difficulty == '중간' ? 'selected' : ''}>중간</option>
        <option value="어려움" ${recipe.difficulty == '어려움' ? 'selected' : ''}>어려움</option>
      </select>
    </div>

    <div class="mb-3">
      <label class="form-label">인원수:</label>
      <input type="number" class="form-control" name="servings" value="${recipe.servings}">
    </div>

    <div class="mb-3">
      <label class="form-label">이미지 파일명:</label>
      <input type="text" class="form-control" name="rfile" value="${recipe.rfile}">
    </div>

    <div class="mb-3 text-end">
      <button type="submit" class="btn btn-primary">수정 완료</button>
      <a href="${pageContext.request.contextPath}/recipeDetail.reci?recipeId=${recipe.recipeId}" class="btn btn-danger">취소</a>
    </div>

  </form>
</div>

<%@include file="../inc/footer.jsp" %>
