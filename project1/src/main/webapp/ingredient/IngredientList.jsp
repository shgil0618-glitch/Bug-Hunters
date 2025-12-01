<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<c:set var="recipeId" value="${param.recipeId}" />

<div class="container card my-5 p-4">
  <h3 class="card-header">📋 재료 목록</h3>

  <a href="${pageContext.request.contextPath}/ingredientInsertForm.ingre?recipeId=${recipeId}" class="btn btn-success my-2">➕ 재료 추가</a>

  <table class="table table-bordered">
    <thead>
      <tr>
        <th>ID</th>
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
          <td>${i.ingredientId}</td>
          <td>${i.ingredientName}</td>
          <td>${i.quantity}</td>
          <td>${i.unit}</td>
          <td>${i.kcal}</td>
          <td>
            <a href="${pageContext.request.contextPath}/ingredientUpdateForm.ingre?ingredientId=${i.ingredientId}" 
               class="btn btn-warning btn-sm">수정</a>
          </td>
          <td>
            <a href="${pageContext.request.contextPath}/ingredientDelete.ingre?ingredientId=${i.ingredientId}&recipeId=${recipeId}"
               class="btn btn-danger btn-sm">삭제</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <div class="text-end mt-3">
    <a href="${pageContext.request.contextPath}/recipeDetail.reci?recipeId=${recipeId}" class="btn btn-secondary">레시피로 돌아가기</a>
  </div>
</div>

<%@include file="../inc/footer.jsp" %>
