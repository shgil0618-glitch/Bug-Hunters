<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%-- fn 라이브러리 추가 --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container mt-5">
    <h2 class="mb-4">${recipe.title}</h2>

    <c:if test="${not empty recipe.image}">
        <div class="mb-3 text-center">
            <%-- http로 시작하면(외부 URL) 그대로 사용, 아니면 내부 업로드 경로 사용 --%>
            <c:choose>
                <c:when test="${fn:startsWith(recipe.image, 'http')}">
                    <img src="${recipe.image}" alt="대표 이미지" class="img-fluid rounded" style="max-width:400px;">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/upload/${recipe.image}" 
                         alt="대표 이미지" class="img-fluid rounded" style="max-width:400px;">
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>

    <div class="row mb-3">
        <div class="col-md-6">
            <label class="form-label">카테고리</label>
            <input type="text" class="form-control" value="${recipe.categoryName}" readonly>
        </div>
        <div class="col-md-6">
            <label class="form-label">난이도</label>
            <input type="text" class="form-control" value="${recipe.difficulty}" readonly>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-md-6">
            <label class="form-label">조리 시간</label>
            <input type="text" class="form-control" value="${recipe.cookTime}분" readonly>
        </div>
        <div class="col-md-6">
            <label class="form-label">인분</label>
            <input type="text" class="form-control" value="${recipe.servings}인분" readonly>
        </div>
    </div>

    <div class="mb-3">
        <label class="form-label">레시피 설명</label>
        <textarea class="form-control" rows="3" readonly>${recipe.description}</textarea>
    </div>

    <c:if test="${not empty recipe.ingredients}">
        <div class="mb-3">
            <label class="form-label">재료</label>
            <ul class="list-group">
                <c:forEach var="ingre" items="${recipe.ingredients}">
                    <li class="list-group-item">${ingre.ingreName} - ${ingre.ingreNum}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <div class="mb-3">
        <label class="form-label">조리 방법</label>
        <textarea class="form-control" rows="6" readonly>${recipe.instructions}</textarea>
    </div>
    
    <c:if test="${not empty recipe.images}">
        <div class="mb-3">
            <label class="form-label">조리 단계 이미지</label>
            <div class="row">
                <c:forEach var="img" items="${recipe.images}">
                    <div class="col-md-4 mb-2">
                         <%-- 단계 이미지도 http 체크 (혹시 모르니) --%>
                        <c:choose>
                            <c:when test="${fn:startsWith(img.rurl, 'http')}">
                                <img src="${img.rurl}" class="img-fluid rounded" style="max-width:100%;">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/upload/${img.rurl}" class="img-fluid rounded" style="max-width:100%;">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>
    
    <c:if test="${loginUser.appUserId == recipe.appUserId}">
        <div class="d-flex justify-content-end mt-4">
            <a href="${pageContext.request.contextPath}/recipe/modify?recipeId=${recipe.recipeId}" 
               class="btn btn-primary me-2">수정</a>
            <form action="${pageContext.request.contextPath}/recipe/delete" method="post">
                <sec:csrfInput/>
                <input type="hidden" name="recipeId" value="${recipe.recipeId}">
                <button type="submit" class="btn btn-danger"
                        onclick="return confirm('정말 삭제하시겠습니까?');">삭제</button>
            </form>
        </div>
    </c:if>
</div>

<%@ include file="../inc/footer.jsp"%>