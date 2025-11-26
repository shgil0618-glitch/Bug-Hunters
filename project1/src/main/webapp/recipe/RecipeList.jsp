<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/header.jsp" %>

<div class="container card my-5 p-4">
  <h3 class="card-header">📋 레시피 목록</h3>

  <a href="${pageContext.request.contextPath}/recipeInsertForm.reci" class="btn btn-success my-2">➕ 새 레시피 등록</a>

  <table class="table table-bordered">
    <thead>
      <tr>
        <th>ID</th>
        <th>사진</th>
        <th>제목</th>
        <th>카테고리</th>
        <th>난이도</th>
        <th>보기</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="r" items="${list}">
        <tr>
          <td>${r.recipeId}</td>
          <td><img src="${pageContext.request.contextPath}/upload/${r.rfile}" width="80"></td>
          <td>${r.title}</td>
          <td>${r.category}</td>
          <td>${r.difficulty}</td>
          <td>
            <a href="${pageContext.request.contextPath}/recipeDetail.reci?recipeId=${r.recipeId}" class="btn btn-primary btn-sm">보기</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<%@include file="../inc/footer.jsp" %>
