<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container mt-5">
    <h3>레시피 삭제</h3>
    <p>정말로 이 레시피를 삭제하시겠습니까?</p>

    <form action="${pageContext.request.contextPath}/recipe/delete" method="post">
        <sec:csrfInput/>
        <input type="hidden" name="recipeId" value="${recipe.recipeId}">
        <div class="d-flex justify-content-end mt-4">
            <button type="submit" class="btn btn-danger">삭제</button>
            <a href="${pageContext.request.contextPath}/recipe/detail?recipeId=${recipe.recipeId}" 
               class="btn btn-secondary ms-2">취소</a>
        </div>
    </form>
</div>

<%@ include file="../inc/footer.jsp"%>