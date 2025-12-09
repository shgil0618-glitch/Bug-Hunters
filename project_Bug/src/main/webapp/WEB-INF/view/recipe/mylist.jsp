<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container mt-5">
    
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3>나의 레시피 목록 ✨</h3>
        <button onclick="location.href='${pageContext.request.contextPath}/recipe/register'" class="btn btn-primary">레시피 등록</button>
    </div>

    <c:if test="${not empty result}">
        <div class="alert alert-success mt-4">${result}</div>
    </c:if>

    <table class="table table-hover">
        <thead class="table-light">
            <tr>
                <th>No.</th>
                <th>레시피 제목</th>
                <th>카테고리</th>
                <th>조리 시간</th>
                <th>인분</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="recipe" items="${list}" varStatus="status">
                <tr>
                    <td>${status.count}</td>
                    <td><a href="${pageContext.request.contextPath}/recipe/detail?recipeId=${recipe.recipeId}">${recipe.title}</a></td>
                    <td>${recipe.categoryName}</td>
                    <td>${recipe.cookTime}분</td>
                    <td>${recipe.servings}인분</td>
                    <td>${recipe.views}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty list}">
                <tr>
                    <td colspan="7" class="text-center text-muted py-4">아직 작성된 레시피가 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <div class="d-flex justify-content-center">
        <%-- (현재는 페이징 로직이 없으므로 비워둡니다.) --%>
    </div>
</div>

<%@ include file="../inc/footer.jsp"%>