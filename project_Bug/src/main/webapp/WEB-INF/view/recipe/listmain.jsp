<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container mt-5">
    <h3>레시피 목록</h3>

    <!-- 성공 메시지 -->
    <c:if test="${not empty result}">
        <div class="alert alert-success mt-4">${result}</div>
    </c:if>

    <!-- 카드 레이아웃 -->
    <div class="row">
        <c:forEach var="recipe" items="${list}" varStatus="status">
            <!-- 8개까지만 출력 -->
            <c:if test="${status.index < 8}">
                <div class="col-md-3 mb-4">
                    <div class="card h-100 shadow-sm" style="cursor:pointer;" 
                         data-bs-toggle="modal" data-bs-target="#recipeModal${recipe.recipeId}">
                        <!-- 이미지 분기 처리 -->
                        <c:choose>
                            <c:when test="${fn:startsWith(recipe.image, 'http')}">
                                <img src="${recipe.image}" alt="${recipe.title}" class="card-img-top">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/upload/${recipe.image}" alt="${recipe.title}" class="card-img-top">
                            </c:otherwise>
                        </c:choose>
                        <div class="card-body">
                            <!-- 요리 이름 -->
                            <h5 class="card-title text-center">${recipe.title}</h5>
                            <!-- 설명 한 줄 추가 -->
                            <p class="card-text text-muted text-truncate">
                                <c:choose>
                                    <c:when test="${fn:length(recipe.description) > 50}">
                                        ${fn:substring(recipe.description, 0, 50)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${recipe.description}
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                </div>

                <!-- 모달 -->
                <div class="modal fade" id="recipeModal${recipe.recipeId}" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-lg modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">${recipe.title}</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                            </div>
                            <div class="modal-body">
                                <p><strong>카테고리:</strong> ${recipe.categoryName}</p>
                                <p><strong>작성자:</strong> ${recipe.nickname}</p>
                                <p><strong>조리 시간:</strong> ${recipe.cookTime} 분</p>
                                <p><strong>인분:</strong> ${recipe.servings} 인분</p>
                                <hr>
                                <p><strong>레시피 설명:</strong></p>
                                <p>${recipe.description}</p>
                                <!-- 모달 내부 이미지도 분기 처리 -->
                                <c:choose>
                                    <c:when test="${fn:startsWith(recipe.image, 'http')}">
                                        <img src="${recipe.image}" alt="${recipe.title}" class="img-fluid mt-3"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/upload/${recipe.image}" alt="${recipe.title}" class="img-fluid mt-3"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>

    <!-- 페이지 네비게이션 -->
    <div class="d-flex justify-content-center mt-4">
        <ul class="pagination">
            <li class="page-item"><a class="page-link" href="?page=1">1</a></li>
            <li class="page-item"><a class="page-link" href="?page=2">2</a></li>
            <li class="page-item"><a class="page-link" href="?page=3">3</a></li>
        </ul>
    </div>
</div>